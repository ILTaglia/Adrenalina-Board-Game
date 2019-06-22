package client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameMap {
    int Cx=100, Cy=100;
    public void mostra(int type)
    {



        JFrame schermata = new JFrame("Map");
        schermata.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        schermata.setSize(1500,1000);
        schermata.setLayout(null);

        if(type==1)
        {
            int offset=35;
            InfoPlayerPage view = new InfoPlayerPage();
            MyCardPage myCardPage=new MyCardPage();
            MyPowCards myPowCards=new MyPowCards();
            //CARICO immagine mappa Sinistra 1
            String percorso = System.getProperty("user.dir");
            percorso=percorso+"\\src\\main\\java\\client\\gui\\media\\M1R.png";
            ImageIcon sfondo = new ImageIcon(percorso);
            JLabel background = new JLabel(sfondo);
            background.setBounds(100,0,1053,800);


            JButton up = new JButton("UP");
            up.setBounds(1250+offset,500,190,30);
            JButton right = new JButton("RIGHT");
            right.setBounds(1350+offset,535,90,50);
            JButton down = new JButton("DOWN");
            down.setBounds(1250+offset,590,190,30);
            JButton left = new JButton("LEFT");
            left.setBounds(1250+offset,535,90,50);


            JButton player1 = new JButton("Giocatore Blu");
            player1.setBounds(1250+offset,50,190,30);
            JButton player2 = new JButton("Giocatore Giallo");
            player2.setBounds(1250+offset,100,190,30);
            JButton player3 = new JButton("Giocatore Grigio");
            player3.setBounds(1250+offset,150,190,30);
            JButton player4 = new JButton("Giocatore Viola");
            player4.setBounds(1250+offset,200,190,30);

            JButton mytable = new JButton("La mia plancia");
            mytable.setBounds(200,850,190,50);
            JButton mycards = new JButton("Le mie carte");
            mycards.setBounds(500,850,190,50);
            JButton mypow = new JButton("I miei potenziamenti");
            mypow.setBounds(800,850,190,50);



            player1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    view.mostra(1);
                }
            });

            player2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    view.mostra(2);
                }
            });

            player3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    view.mostra(3);
                }
            });

            player4.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    view.mostra(4);
                }
            });

            Circle c = new Circle();


            up.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Cx=Cx+10;
                    c.translate(Cx,Cy);
                }
            });

            down.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Cx=Cx-10;
                    c.translate(Cx,Cy);
                }
            });

            mycards.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    myCardPage.mostra();
                }
            });


            mypow.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    myPowCards.mostra();
                }
            });

            mytable.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    view.mostra(1);
                }
            });

            schermata.add(c);



            schermata.add(mycards);
            schermata.add(mypow);
            schermata.add(mytable);


            schermata.add(player1);
            schermata.add(player2);
            schermata.add(player3);
            schermata.add(player4);

            schermata.add(up);
            schermata.add(right);
            schermata.add(down);
            schermata.add(left);
            schermata.add(background);
            schermata.setVisible(true);
            schermata.setResizable(false);

        }




    }


    public static void main(String []args)
    {
        GameMap map = new GameMap();
        map.mostra(1);
    }

}
