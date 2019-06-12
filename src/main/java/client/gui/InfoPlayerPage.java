package client.gui;

import javax.swing.*;

public class InfoPlayerPage {
    public void mostra(int player)
    {
        String linker=new String();
        if(player==1)
        {
            linker="\\src\\main\\java\\client\\gui\\media\\ColoreBlu.png";
        }
        if(player==2)
        {
            linker="\\src\\main\\java\\client\\gui\\media\\ColoreGiallo.png";
        }
        if(player==3)
        {
            linker="\\src\\main\\java\\client\\gui\\media\\ColoreGrigio.png";
        }
        if(player==4)
        {
            linker="\\src\\main\\java\\client\\gui\\media\\ColoreViola.png";
        }
        if(player==5)
        {
            linker="\\src\\main\\java\\client\\gui\\media\\ColoreViola.png";
        }
        JFrame schermata = new JFrame("INFORMAZIONI GIOCATORE");
        schermata.setResizable(false);
        schermata.setBounds(0,0,1300,400);

        String percorso = System.getProperty("user.dir");
        percorso=percorso+linker;
        ImageIcon sfondo = new ImageIcon(percorso);
        JLabel background = new JLabel("",sfondo,JLabel.CENTER);
        background.setBounds(0,0,1116,270);
        schermata.add(background);

        schermata.setVisible(true);
        schermata.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



    }

    public static void main(String []args)
    {
        InfoPlayerPage info = new InfoPlayerPage();
        info.mostra(1);
    }
}

