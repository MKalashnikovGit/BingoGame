/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mkalashnikov.bingogame;

import java.awt.Dimension;
import java.awt.Graphics;
//import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

    private ImageIcon imageIcon;

    public ImagePanel() {
        //var path = System.getProperty("user.dir") + "\\src\\main\\java\\images\\bingoBackground.png";
        //imageIcon = //new ImageIcon(ImageIO.read(ImagePanel.class.getResource("/images/bingoBackground.png")));
        //new javax.swing.ImageIcon(getClass().getResource("/images/bingoBackground.png"));
//                new javax.swing.ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\images\\bingoBackground.png");

        //ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        var url = ImagePanel.class.getResource("bingoBackground.png");
        var url1 = ImagePanel.class.getResource("images/bingoBackground.png");
        var url2 = ImagePanel.class.getResource("images\\bingoBackground.png");
        var url3 = ImagePanel.class.getResource("/mkalashnikov/bingogame/images/bingoBackground.png");
        var url4 = ImagePanel.class.getResource("/mkalashnikov/bingogame/images/bingoBackground.png");
        //imageIcon = new ImageIcon(url);
    }

    @Override
    public Dimension getPreferredSize() {
        return (imageIcon == null ? new Dimension(100, 100) : new Dimension(
                imageIcon.getIconWidth(), imageIcon.getIconHeight()));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imageIcon.getImage(), 0, 0, this);
    }
}
