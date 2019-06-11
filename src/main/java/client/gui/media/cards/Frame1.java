package client.gui.media.cards;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame1 {
    public Button bottone1;


    public void mostra()
    {
        JFrame schermata = new JFrame("Schermata!");
        schermata.setSize(400,400);
        schermata.setLayout(new BorderLayout());
        JPanel spazio1 = new JPanel();
        JPanel spazio2 = new JPanel();
        JPanel spazio3 = new JPanel();
        JPanel spazio4 = new JPanel();
        spazio1.setLayout(new BorderLayout());
        spazio2.setLayout(new BorderLayout());
        spazio3.setLayout(new BorderLayout());
        spazio4.setLayout(new BorderLayout());
        JButton bottone1 = new JButton("Premi");
        JButton bottone2 = new JButton("Non premere");
        spazio1.add(bottone1,BorderLayout.EAST);
        spazio1.add(bottone2,BorderLayout.WEST);
        JLabel scritta1 = new JLabel("Premi il bottone a destra!");
        JLabel scritta2 = new JLabel("Hai già selezionato ciò che volevi?");
        spazio2.add(scritta1);
        spazio3.add(scritta2);
        schermata.add(BorderLayout.CENTER,spazio1);
        schermata.add(BorderLayout.NORTH,spazio2);
        schermata.add(BorderLayout.SOUTH,spazio3);
        schermata.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //schermata.pack();
        schermata.setVisible(true);
        bottone1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bottone2.setBackground(Color.RED);
                bottone2.repaint();
            }
        });
    }





    public static void main(String args[])
    {

        Frame1 schermata = new Frame1();
        schermata.mostra();


    }
}

abstract class Controllore extends JFrame implements ActionListener
{

}
