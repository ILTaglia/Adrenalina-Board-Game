package client.gui;

import javax.swing.*;

public class MyPowCards {

    public void mostra()
    {
/**
 * Class for powcards
 */
        JFrame schermata = new JFrame("MY POWERUP");
        schermata.setResizable(false);
        schermata.setBounds(0,0,1300,600);
        schermata.setLayout(null);

        String exactweapon = loadpow(0);
        ImageIcon sfondo = new ImageIcon(exactweapon);
        JLabel background = new JLabel(sfondo);
        JLabel background2 = new JLabel(sfondo);
        JLabel background3 = new JLabel(sfondo);

        int h=50, x=50;
        background.setBounds(x,h,240,406);
        background2.setBounds(10*x,h,240,406);
        background3.setBounds(20*x,h,240,406);


        JButton usegun1 = new JButton("Usa il potenziamento!");
        usegun1.setBounds(x,h+450,240,30);
        JButton usegun2 = new JButton("Usa il potenziamento!");
        usegun2.setBounds(10*x,h+450,240,30);
        JButton usegun3 = new JButton("Usa il potenziamento!");
        usegun3.setBounds(20*x,h+450,240,30);

        schermata.add(background);
        schermata.add(background2);
        schermata.add(background3);
        schermata.add(usegun1);
        schermata.add(usegun2);
        schermata.add(usegun3);

        schermata.setVisible(true);


    }





    public static void main (String []args)
    {
        MyPowCards myPowCards = new MyPowCards();
        myPowCards.mostra();
    }


















    public String loadpow(int index)
    {
        String percorso = System.getProperty("user.dir");
        //TODO qui si farà un controllo sul nome della carta, a seconda del quale restituisco il link corretto
        return percorso+"\\src\\main\\java\\client\\gui\\media\\cards\\AD_powerups_IT_022.png";
    }

}
