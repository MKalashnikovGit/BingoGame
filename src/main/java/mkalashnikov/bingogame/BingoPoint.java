/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mkalashnikov.bingogame;

import java.awt.Point;

/**
 *
 * @author Макс
 */
public class BingoPoint extends Point {

    public CellState cellState;

    public BingoPoint(Point p, CellState state) {
        this.x = p.x;
        this.y = p.y;
        cellState = state;
    }
}
