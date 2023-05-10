/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mkalashnikov.bingogame.UI;
import javax.swing.JButton;

/**
 *
 * @author Макс
 */
public class BingoButton extends JButton{
    public int rowIndex;
    public int colIndex;
    
    public BingoButton() {
        rowIndex = 0;
        colIndex = 0;
    }
    
    public BingoButton(int rowIndex, int colIndex) {
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
    }
}
