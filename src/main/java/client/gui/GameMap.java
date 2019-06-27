package client.gui;

import model.Dashboard;
import model.Player;
import model.PlayerVisibleData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameMap {
    int mapindex;
    int Cx=300, Cy=210;
    PlayerVisibleData data;
    public GameMap(PlayerVisibleData data)
    {
        this.mapindex=data.getDashboard().getMapType();
        this.data=data;
    }
    public void mostra()
    {
        int movex=190,movey=190;



        JFrame schermata = new JFrame("Map");
        schermata.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        schermata.setSize(1500,1000);
        schermata.setLayout(null);


            int offset=35;
            InfoPlayerPage view = new InfoPlayerPage();
            MyCardPage myCardPage=new MyCardPage();
            MyPowCards myPowCards=new MyPowCards();
            //CARICO immagine mappa Sinistra 1
            String percorso = loadmapimage(mapindex);
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





            String userimage = loaduserimage(0);
            ImageIcon user = new ImageIcon(userimage);
            JLabel playerimage = new JLabel(user);
            playerimage.setBounds(Cx,Cy,50,49);




            ImageIcon icon = new ImageIcon(loadweaponimage(0));
            Image scaledImage = icon.getImage().getScaledInstance(120, 200, Image.SCALE_DEFAULT);
            icon.setImage(scaledImage);
            JLabel arma = new JLabel(icon);
            arma.setBounds(1,1,200,400);

            //############################################
            //##        MORTAL TRACK                #####
            //############################################

            int space=46;
            int base=177;
            int killtrack [][]=data.getDashboard().getKillShotTrack();


            ImageIcon damage1 = new ImageIcon(loaddamageimage(killtrack[0][0]));
            JLabel dam1 = new JLabel(damage1);
            dam1.setBounds(base,50,30,30);

            ImageIcon damage2 = new ImageIcon(loaddamageimage(killtrack[0][1]));
            JLabel dam2 = new JLabel(damage2);
            dam2.setBounds(base+space,50,30,30);

            ImageIcon damage3 = new ImageIcon(loaddamageimage(killtrack[0][2]));
            JLabel dam3 = new JLabel(damage3);
            dam3.setBounds(base+2*space,50,30,30);

            ImageIcon damage4 = new ImageIcon(loaddamageimage(killtrack[0][3]));
            JLabel dam4 = new JLabel(damage4);
            dam4.setBounds(base+3*space,50,30,30);

            ImageIcon damage5 = new ImageIcon(loaddamageimage(killtrack[0][4]));
            JLabel dam5 = new JLabel(damage5);
            dam5.setBounds(base+4*space,50,30,30);

            ImageIcon damage6 = new ImageIcon(loaddamageimage(killtrack[0][5]));
            JLabel dam6 = new JLabel(damage6);
            dam6.setBounds(base+5*space,50,30,30);

            ImageIcon damage7 = new ImageIcon(loaddamageimage(killtrack[0][6]));
            JLabel dam7 = new JLabel(damage7);
            dam7.setBounds(base+6*space,50,30,30);

            ImageIcon damage8 = new ImageIcon(loaddamageimage(killtrack[0][7]));
            JLabel dam8 = new JLabel(damage8);
            dam8.setBounds(base+7*space,50,30,30);






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

            right.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Cx=Cx+movex;
                    playerimage.setLocation(Cx,Cy);
                }
            });

            left.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Cx=Cx-movex;
                    playerimage.setLocation(Cx,Cy);
                }
            });

            up.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Cy=Cy-movey;
                    playerimage.setLocation(Cx,Cy);
                }
            });

            down.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Cy=Cy+movey;
                    playerimage.setLocation(Cx,Cy);
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

            schermata.add(dam1);
            schermata.add(dam2);
            schermata.add(dam3);
            schermata.add(dam4);
            schermata.add(dam5);
            schermata.add(dam6);
            schermata.add(dam7);
            schermata.add(dam8);


            schermata.add(arma);
            schermata.add(playerimage);

            schermata.add(up);
            schermata.add(right);
            schermata.add(down);
            schermata.add(left);
            schermata.add(background);

            schermata.setVisible(true);
            schermata.setResizable(false);







    }




    public String loadweaponimage(int index)
    {
        String percorso = System.getProperty("user.dir");
        if(index==0)
        {
            percorso=percorso+"\\src\\main\\java\\client\\gui\\media\\cards\\AD_weapons_IT_022.png";
        }
        if(index==1)
        {
            percorso=percorso+"\\src\\main\\java\\client\\gui\\media\\cards\\AD_weapons_IT_023.png";
        }
        if(index==2)
        {
            percorso=percorso+"\\src\\main\\java\\client\\gui\\media\\cards\\AD_weapons_IT_024.png";
        }
        if(index==3)
        {
            percorso=percorso+"\\src\\main\\java\\client\\gui\\media\\cards\\AD_weapons_IT_025.png";
        }
        if(index==4)
        {
            percorso=percorso+"\\src\\main\\java\\client\\gui\\media\\cards\\AD_weapons_IT_026.png";
        }
        if(index==5)
        {
            percorso=percorso+"\\src\\main\\java\\client\\gui\\media\\cards\\AD_weapons_IT_027.png";
        }
        if(index==6)
        {
            percorso=percorso+"\\src\\main\\java\\client\\gui\\media\\cards\\AD_weapons_IT_028.png";
        }
        if(index==7)
        {
            percorso=percorso+"\\src\\main\\java\\client\\gui\\media\\cards\\AD_weapons_IT_029.png";
        }
        if(index==8)
        {
            percorso=percorso+"\\src\\main\\java\\client\\gui\\media\\cards\\AD_weapons_IT_0212.png";
        }
        if(index==9)
        {
            percorso=percorso+"\\src\\main\\java\\client\\gui\\media\\cards\\AD_weapons_IT_0213.png";
        }
        if(index==10)
        {
            percorso=percorso+"\\src\\main\\java\\client\\gui\\media\\cards\\AD_weapons_IT_0214.png";
        }
        if(index==11)
        {
            percorso=percorso+"\\src\\main\\java\\client\\gui\\media\\cards\\AD_weapons_IT_0215.png";
        }
        if(index==12)
        {
            percorso=percorso+"\\src\\main\\java\\client\\gui\\media\\cards\\AD_weapons_IT_0216.png";
        }
        if(index==13)
        {
            percorso=percorso+"\\src\\main\\java\\client\\gui\\media\\cards\\AD_weapons_IT_0217.png";
        }
        if(index==14)
        {
            percorso=percorso+"\\src\\main\\java\\client\\gui\\media\\cards\\AD_weapons_IT_0218.png";
        }
        if(index==15)
        {
            percorso=percorso+"\\src\\main\\java\\client\\gui\\media\\cards\\AD_weapons_IT_0219.png";
        }
        if(index==16)
        {
            percorso=percorso+"\\src\\main\\java\\client\\gui\\media\\cards\\AD_weapons_IT_0220.png";
        }
        if(index==17)
        {
            percorso=percorso+"\\src\\main\\java\\client\\gui\\media\\cards\\AD_weapons_IT_0221.png";
        }
        if(index==18)
        {
            percorso=percorso+"\\src\\main\\java\\client\\gui\\media\\cards\\AD_weapons_IT_0222.png";
        }
        return percorso;
    }






    public String loaduserimage(int index)
    {
        String percorso = System.getProperty("user.dir");
        //TODO qui si farà un controllo sul nome della carta, a seconda del quale restituisco il link corretto
        return percorso+"\\src\\main\\java\\client\\gui\\media\\cards\\Personaggio.png";
    }

    public String loaddamageimage(int colorindex)
    {
        String percorso=System.getProperty("user.dir");
        percorso=percorso+"\\src\\main\\java\\client\\gui\\media\\cards";
        if(colorindex==0)
        {
            percorso=percorso+"\\Blu.png";
        }
        if(colorindex==1)
        {
            percorso=percorso+"\\Green.png";
        }
        if(colorindex==2)
        {
            percorso=percorso+"\\Yellow.png";
        }
        if(colorindex==3)
        {
            percorso=percorso+"\\Pink.png";
        }
        if(colorindex==4)
        {
            percorso=percorso+"\\Grey.png";
        }
        if(colorindex==5)
        {
            percorso=percorso+"\\Red.png";
        }
        return percorso;
    }





    public static void main(String []args)
    {
        Dashboard mappa = new Dashboard(1);
        Player player1 = new Player("UtenteDiProva","Green","abcde");
        Player player2 = new Player("UtenteDiProva","Blue","abcde");
        Player player3 = new Player("UtenteDiProva","Yellow","abcde");
        Player player4 = new Player("UtenteDiProva","Pink","abcde");
        Player player5 = new Player("UtenteDiProva","Grey","abcde");

        PlayerVisibleData datas = new PlayerVisibleData(player1);
        mappa.setKillShotTrack(player1,0);
        mappa.setKillShotTrack(player2,1);
        mappa.setKillShotTrack(player2,2);
        mappa.setKillShotTrack(player3,3);
        mappa.setKillShotTrack(player2,4);
        mappa.setKillShotTrack(player4,5);
        mappa.setKillShotTrack(player5,6);
        mappa.setKillShotTrack(player1,7);
        datas.setDashboard(mappa);
        GameMap map = new GameMap(datas);

        map.mostra();
    }

    public String loadmapimage(int index)
    {
        System.out.println(index);
        String percorso = System.getProperty("user.dir");
        if(index==2)
        {
            percorso=percorso+"\\src\\main\\java\\client\\gui\\media\\MR2.png";
        }
        if(index==3)
        {
            percorso=percorso+"\\src\\main\\java\\client\\gui\\media\\MR4.png";
        }
        if(index==1)
        {
            percorso=percorso+"\\src\\main\\java\\client\\gui\\media\\MR3.png";
        }
        if(index==4)
        {
            percorso=percorso+"\\src\\main\\java\\client\\gui\\media\\M1R.png";
        }
        return percorso;

    }


}
