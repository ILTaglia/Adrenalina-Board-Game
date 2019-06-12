package client.gui;

import javax.swing.*;
import java.awt.*;

public class Circle extends JPanel {

    int x=50,y=50;
    @Override
    public void paint(Graphics g)
    {
        setSize(500,500);
        g.drawOval(x,y,50,50);
        g.setColor(Color.red);
        g.fillOval(x,y,50,50);

    }

    public void translate(int x, int y)
    {
        this.x=x;
        this.y=y;
        repaint();
    }

    public static void main(String [] args)
    {
        JFrame schermata = new JFrame();
        schermata.setSize(600,600);

        Circle cerchio = new Circle();

        schermata.add(cerchio);

        schermata.setVisible(true);

        cerchio.translate(100,100);

        cerchio.translate(200,200);




    }
}
