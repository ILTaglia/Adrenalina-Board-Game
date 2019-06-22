package client.gui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyCardPage {
    public void mostra()
    {
        JFrame schermata = new JFrame("MY CARDS");


        schermata.setResizable(false);
        schermata.setLayout(null);
        //schermata.setLayout(new BorderLayout());
        schermata.setBounds(0,0,1400,650);




        String exactweapon = loadweapon(0);
        String percorso = System.getProperty("user.dir");
        percorso=percorso+exactweapon;
        ImageIcon sfondo = new ImageIcon(percorso);
        JLabel background = new JLabel(sfondo);
        JLabel background2 = new JLabel(sfondo);
        JLabel background3 = new JLabel(sfondo);


        int h=50, x=50;
        background.setBounds(x,h,240,406);
        background2.setBounds(10*x,h,240,406);
        background3.setBounds(20*x,h,240,406);


        JButton usegun1 = new JButton("Usa l'Arma!");
        usegun1.setBounds(x,h+450,240,30);
        JButton usegun2 = new JButton("Usa l'Arma!");
        usegun2.setBounds(10*x,h+450,240,30);
        JButton usegun3 = new JButton("Usa l'Arma!");
        usegun3.setBounds(20*x,h+450,240,30);


        usegun1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        usegun2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        usegun3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });



        schermata.add(usegun1);
        schermata.add(usegun2);
        schermata.add(usegun3);
        schermata.add(background);
        schermata.add(background2);
        schermata.add(background3);





        schermata.setVisible(true);
    }
    //240*406

    public String loadweapon(int index)
    {
        //TODO qui si farà un controllo sul nome della carta, a seconda del quale restituisco il link corretto
        return "\\src\\main\\java\\client\\gui\\media\\cards\\AD_weapons_IT_022.png";
    }

    public static void main(String []args)
    {
        MyCardPage info = new MyCardPage();
        info.mostra();
    }


}
