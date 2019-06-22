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
        schermata.setBounds(0,0,1300,1000);
        schermata.setLayout(null);

        String percorso = System.getProperty("user.dir");
        percorso=percorso+linker;
        ImageIcon sfondo = new ImageIcon(percorso);
        JLabel background = new JLabel(sfondo);
        background.setBounds(50,50,1116,270);
        schermata.add(background);



        String exactweapon = loadweapon(0);
        String percorso2 = System.getProperty("user.dir");
        percorso2=percorso2+exactweapon;
        ImageIcon sfondo2 = new ImageIcon(percorso2);
        JLabel background1 = new JLabel(sfondo2);
        JLabel background2 = new JLabel(sfondo2);
        JLabel background3 = new JLabel(sfondo2);

        int h=400, x=50;
        background1.setBounds(x,h,240,406);
        background2.setBounds(10*x*4/5,h,240,406);
        background3.setBounds(20*x*4/5,h,240,406);


        schermata.add(background1);
        schermata.add(background2);
        schermata.add(background3);







        schermata.setVisible(true);
        //schermata.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



    }

    public static void main(String []args)
    {
        InfoPlayerPage info = new InfoPlayerPage();
        info.mostra(1);
    }

    public String loadweapon(int index)
    {
        //TODO qui si farà un controllo sul nome della carta, a seconda del quale restituisco il link corretto
        return "\\src\\main\\java\\client\\gui\\media\\cards\\AD_weapons_IT_022.png";
    }

}

