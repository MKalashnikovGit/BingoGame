/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package mkalashnikov.bingogame;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Игровая логика Bingo!
 *
 * @author Макс
 */
public class BingoGame {

    // Игровое поле, состоящее из клеток с разным статусом.
    private BingoCell[][] field;

    // Причина проигрыша
    private String failReason;

    // Счет
    private int score;

    private Boolean isRunning = false;

    public Boolean getIsRunning() {
        return isRunning;
    }

    public void stop() {
        isRunning = false;
    }

    // Начать игру
    public List<BingoPoint> start(int level, Boolean clearScore) {
        if (clearScore) {
            score = 0;
        }

        isRunning = true;
        int fieldSize = level + 4;
        field = new BingoCell[fieldSize][fieldSize];
        failReason = "";
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                field[i][j] = new BingoCell();
            }
        }

        // Отметить 2 случайные клетки.
        markRandomCleanCell(fieldSize);
        markRandomCleanCell(fieldSize);

        return getModifiedCells();
    }

    // Получить измененные за шаг ячейки
    private ArrayList<BingoPoint> getModifiedCells() {
        var modifiedCells = new ArrayList<BingoPoint>();
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                if (field[i][j].isModified) {
                    var modified = new BingoPoint(new Point(i, j), field[i][j].state);
                    modifiedCells.add(modified);
                }
            }
        }

        return modifiedCells;
    }

    // Отметить случайную ячейку
    private void markRandomCleanCell(int fieldSize) {
        var rnd = new Random();
        Point p;

        do {
            p = new Point(rnd.nextInt(0, fieldSize), rnd.nextInt(0, fieldSize));
        } while (field[p.x][p.y].state != CellState.Clean);

        field[p.x][p.y].state = CellState.Marked;
        field[p.x][p.y].isModified = true;
    }

    // Отметить ячейку и выполнить логику по проверке бинго
    public List<BingoPoint> markCell(Point p, Boolean shouldBingo) {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                field[i][j].isModified = false;
            }
        }

        markCellsInternal(p, true);
        checkBingo(shouldBingo);
        return getModifiedCells();
    }

    // Отметить клетку и возможные соседние.
    private void markCellsInternal(Point p, Boolean markSiblings) {
        var currState = field[p.x][p.y].state;
        if (currState == CellState.Bingoed || currState == CellState.Failed) {
            return;
        }

        var state = currState == CellState.Clean ? CellState.Marked : CellState.Clean;
        field[p.x][p.y].state = state;
        field[p.x][p.y].isModified = true;

        if (markSiblings) {
            var siblings = new ArrayList<Point>();

            // top
            if (p.x > 0) {
                siblings.add(new Point(p.x - 1, p.y));
            }

            // bot
            if (p.x < field.length - 1) {
                siblings.add(new Point(p.x + 1, p.y));
            }

            // left
            if (p.y > 0) {
                siblings.add(new Point(p.x, p.y - 1));
            }

            // right
            if (p.y < field.length - 1) {
                siblings.add(new Point(p.x, p.y + 1));
            }

            for (Point sibling : siblings) {
                markCellsInternal(sibling, false);
            }
        }
    }

    // Вернуть количество собранных Бинго. Отмечает как фейл, если в этот ход не должно было быть бинго или собралось больше одного бинго.
    private int checkBingo(Boolean shouldBingo) {
        int bingos = 0;

        // строки   
        CellState stateToSet = CellState.Clean;
        for (int i = 0; i < field.length; i++) {
            stateToSet = (shouldBingo && bingos < 1) ? CellState.Bingoed : CellState.Failed;
            Boolean isBingo = true;
            Boolean someCellsModified = false;
            for (int j = 0; j < field.length; j++) {
                var cell = field[j][i];
                someCellsModified |= cell.isModified;
                isBingo &= (cell.state == CellState.Marked || cell.state == CellState.Bingoed || cell.state == CellState.Failed);
                if (!isBingo) {
                    break;
                }
            }

            if (isBingo && someCellsModified) {
                for (int j = 0; j < field.length; j++) {
                    if (field[j][i].state != CellState.Bingoed) {
                        field[j][i].isModified = true;
                    }

                    field[j][i].state = stateToSet;
                }

                bingos++;
            }
        }

        // столбцы        
        for (int i = 0; i < field.length; i++) {
            stateToSet = (shouldBingo && bingos < 1) ? CellState.Bingoed : CellState.Failed;
            Boolean isBingo = true;
            Boolean someCellsModified = false;
            for (int j = 0; j < field.length; j++) {
                var cell = field[i][j];
                someCellsModified |= cell.isModified;
                isBingo &= (cell.state == CellState.Marked || cell.state == CellState.Bingoed || cell.state == CellState.Failed);
                if (!isBingo) {
                    break;
                }
            }

            if (isBingo && someCellsModified) {
                for (int j = 0; j < field.length; j++) {
                    if (field[i][j].state != CellState.Bingoed) {
                        field[i][j].isModified = true;
                    }

                    field[i][j].state = stateToSet;
                }

                bingos++;
            }
        }

        // диагонали        
        Boolean isBingo = true;
        Boolean someCellsModified = false;
        for (int i = 0; i < field.length; i++) {
            stateToSet = (shouldBingo && bingos < 1) ? CellState.Bingoed : CellState.Failed;
            var cell = field[i][i];
            someCellsModified |= cell.isModified;
            isBingo &= (cell.state == CellState.Marked || cell.state == CellState.Bingoed || cell.state == CellState.Failed);
            if (!isBingo) {
                break;
            }
        }
        if (isBingo && someCellsModified) {
            for (int i = 0; i < field.length; i++) {
                if (field[i][i].state != CellState.Bingoed) {
                    field[i][i].isModified = true;
                }

                field[i][i].state = stateToSet;
            }

            bingos++;
        }

        isBingo = true;
        someCellsModified = false;
        for (int i = 0; i < field.length; i++) {
            stateToSet = (shouldBingo && bingos < 1) ? CellState.Bingoed : CellState.Failed;
            var cell = field[i][field.length - i - 1];
            someCellsModified |= cell.isModified;
            isBingo &= (cell.state == CellState.Marked || cell.state == CellState.Bingoed || cell.state == CellState.Failed);
            if (!isBingo) {
                break;
            }
        }
        if (isBingo && someCellsModified) {
            for (int i = 0; i < field.length; i++) {
                if (field[i][field.length - i - 1].state != CellState.Bingoed) {
                    field[i][field.length - i - 1].isModified = true;
                }

                field[i][field.length - i - 1].state = stateToSet;
            }

            bingos++;
        }

        if (!shouldBingo && bingos > 0) {
            failReason = "Бинго собрано не на нулевой шаг!";
        }

        if (shouldBingo && bingos == 0) {
            failReason = "Бинго не собрано!";
        }

        if (shouldBingo && bingos > 1) {
            failReason = "Собрано больше одного бинго за один шаг!";
        }

        if (shouldBingo && bingos == 1) {
            score += field.length - 4;
        }

        return bingos;
    }

    // Получить счет
    public int getScore() {
        return score;
    }

    // Узнать причину проигрыша
    public String GetFailReason() {
        return failReason;
    }

    // Вернуть все ячейки для корректного отображения причины проигрыша.
    public ArrayList<BingoPoint> GetFullField() {
        var cells = new ArrayList<BingoPoint>();
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                cells.add(new BingoPoint(new Point(i, j), field[i][j].state));
            }
        }

        return cells;
    }
}
