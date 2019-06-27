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



        //############################################
        //##        COVERED CARDS                #####
        //############################################



        ImageIcon icon2 = new ImageIcon(System.getProperty("user.dir")+"\\src\\main\\java\\client\\gui\\media\\cards\\AD_powerups_IT_02.png");
        Image scaledImage2 = icon2.getImage().getScaledInstance(70, 110, Image.SCALE_DEFAULT);
        icon2.setImage(scaledImage2);
        JLabel arma2 = new JLabel(icon2);
        arma2.setBounds(1050,35,60,120);


        ImageIcon icon1 = new ImageIcon(System.getProperty("user.dir")+"\\src\\main\\java\\client\\gui\\media\\cards\\AD_weapons_IT_02.png");
        Image scaledImage1 = icon1.getImage().getScaledInstance(105, 175, Image.SCALE_DEFAULT);
        icon1.setImage(scaledImage1);
        JLabel arma1 = new JLabel(icon1);
        arma1.setBounds(1000,215,140,190);



        //############################################
        //##        WEAPON CARDS                #####
        //############################################

        int gunl=105, gunh=155;
        int gunstart=653;

        ImageIcon icon = new ImageIcon(loadweaponimage(1,0));
        Image scaledImage = icon.getImage().getScaledInstance(gunl, gunh, Image.SCALE_DEFAULT);
        icon.setImage(scaledImage);
        JLabel arma = new JLabel(icon);
        arma.setBounds(gunstart,1,gunl,gunh);

        ImageIcon icon0 = new ImageIcon(loadweaponimage(2,0));
        Image scaledImage0 = icon0.getImage().getScaledInstance(gunl, gunh, Image.SCALE_DEFAULT);
        icon0.setImage(scaledImage0);
        JLabel arma0 = new JLabel(icon0);
        arma0.setBounds(gunstart+gunl+5,1,gunl,gunh);

        ImageIcon icon3 = new ImageIcon(loadweaponimage(3,0));
        Image scaledImage3 = icon3.getImage().getScaledInstance(gunl, gunh, Image.SCALE_DEFAULT);
        icon3.setImage(scaledImage3);
        JLabel arma3 = new JLabel(icon3);
        arma3.setBounds(gunstart+2*(gunl+5),1,gunl,gunh);



        gunstart=300;

        ImageIcon icon4 = new ImageIcon(loadweaponimage(4,1));
        Image scaledImage4 = icon4.getImage().getScaledInstance(gunh, gunl, Image.SCALE_DEFAULT);
        icon4.setImage(scaledImage4);
        JLabel arma4 = new JLabel(icon4);
        arma4.setBounds(100,gunstart,gunh,gunl);


        ImageIcon icon5 = new ImageIcon(loadweaponimage(5,1));
        Image scaledImage5 = icon5.getImage().getScaledInstance(gunh, gunl, Image.SCALE_DEFAULT);
        icon5.setImage(scaledImage5);
        JLabel arma5 = new JLabel(icon5);
        arma5.setBounds(100,gunstart+gunl+5,gunh,gunl);

        ImageIcon icon6 = new ImageIcon(loadweaponimage(6,1));
        Image scaledImage6 = icon6.getImage().getScaledInstance(gunh, gunl, Image.SCALE_DEFAULT);
        icon6.setImage(scaledImage6);
        JLabel arma6 = new JLabel(icon6);
        arma6.setBounds(100,gunstart+2*(gunl+5),gunh,gunl);


        gunstart=460;

        ImageIcon icon7 = new ImageIcon(loadweaponimage(7,1));
        Image scaledImage7 = icon7.getImage().getScaledInstance(gunh, gunl, Image.SCALE_DEFAULT);
        icon7.setImage(scaledImage7);
        JLabel arma7 = new JLabel(icon7);
        arma7.setBounds(1000,gunstart,gunh,gunl);


        ImageIcon icon8 = new ImageIcon(loadweaponimage(8,1));
        Image scaledImage8 = icon8.getImage().getScaledInstance(gunh, gunl, Image.SCALE_DEFAULT);
        icon8.setImage(scaledImage8);
        JLabel arma8 = new JLabel(icon8);
        arma8.setBounds(1000,gunstart+gunl+5,gunh,gunl);

        ImageIcon icon9 = new ImageIcon(loadweaponimage(9,1));
        Image scaledImage9 = icon9.getImage().getScaledInstance(gunh, gunl, Image.SCALE_DEFAULT);
        icon9.setImage(scaledImage9);
        JLabel arma9 = new JLabel(icon9);
        arma9.setBounds(1000,gunstart+2*(gunl+5),gunh,gunl);



        //############################################
        //##        PLAYERS IMAGE                #####
        //############################################

        int sep= 40;

        //TODO LEGGERE LA POSIZIONE DEI PLAYER E SOMMARE ALLE ATTUALE LA VARIABILE movex MOLTIPLICATA PER L'INDICE DELLE COORDINATE

        String userimage = loaduserimage(0);
        ImageIcon user = new ImageIcon(userimage);
        JLabel playerimage = new JLabel(user);
        playerimage.setBounds(Cx,Cy,50,49);

        String userimage2 = loaduserimage(1);
        ImageIcon user2 = new ImageIcon(userimage2);
        JLabel playerimage2 = new JLabel(user2);
        playerimage2.setBounds(Cx+sep,Cy,50,49);

        String userimage3 = loaduserimage(2);
        ImageIcon user3 = new ImageIcon(userimage3);
        JLabel playerimage3 = new JLabel(user3);
        playerimage3.setBounds(Cx,Cy+sep,50,49);

        String userimage4 = loaduserimage(3);
        ImageIcon user4 = new ImageIcon(userimage4);
        JLabel playerimage4 = new JLabel(user4);
        playerimage4.setBounds(Cx+sep,Cy+sep,50,49);

        String userimage5 = loaduserimage(4);
        ImageIcon user5 = new ImageIcon(userimage5);
        JLabel playerimage5 = new JLabel(user5);
        playerimage5.setBounds(Cx+2*sep,Cy,50,49);



        //############################################
        //##        OBJECTS BUTTON                #####
        //############################################

        int y=Cy-30,x=Cx-20, hb=25, lb=150;
        JButton mmo00 = new JButton("show objects");
        mmo00.setBounds(x,y,120,20);
        schermata.add(mmo00);

        JButton mmo01 = new JButton("show objects");
        mmo01.setBounds(x+movex,y,120,20);
        schermata.add(mmo01);

        JButton mmo02 = new JButton("show objects");
        mmo02.setBounds(x+2*movex,y,120,20);
        schermata.add(mmo02);

        if(mapindex==2||mapindex==3)
        {
            JButton mmo03 = new JButton("show objects");
            mmo03.setBounds(x+3*movex,y,120,20);
            schermata.add(mmo03);
        }


        JButton mmo10= new JButton("show objects");
        mmo10.setBounds(x,y+movey,120,20);
        schermata.add(mmo10);

        JButton mmo11 = new JButton("show objects");
        mmo11.setBounds(x+movex,y+movey,120,20);
        schermata.add(mmo11);

        JButton mmo12 = new JButton("show objects");
        mmo12.setBounds(x+2*movex,y+movey,120,20);
        schermata.add(mmo12);

        JButton mmo13 = new JButton("show objects");
        mmo13.setBounds(x+3*movex,y+movey,120,20);
        schermata.add(mmo13);

        if(mapindex==3||mapindex==4)
        {
            JButton mmo20 = new JButton("show objects");
            mmo20.setBounds(x,y+2*movey,120,20);
            schermata.add(mmo20);
        }


        JButton mmo21 = new JButton("show objects");
        mmo21.setBounds(x+movex,y+2*movey,120,20);
        schermata.add(mmo21);

        JButton mmo22 = new JButton("show objects");
        mmo22.setBounds(x+2*movex,y+2*movey,120,20);
        schermata.add(mmo22);

        JButton mmo23 = new JButton("show objects");
        mmo23.setBounds(x+3*movex,y+2*movey,120,20);
        schermata.add(mmo23);






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

        schermata.add(arma2);
        schermata.add(arma1);

        schermata.add(arma);
        schermata.add(arma0);
        schermata.add(arma3);
        schermata.add(arma4);
        schermata.add(arma5);
        schermata.add(arma6);
        schermata.add(arma7);
        schermata.add(arma8);
        schermata.add(arma9);


        schermata.add(arma);
        schermata.add(playerimage);
        schermata.add(playerimage2);
        schermata.add(playerimage3);
        schermata.add(playerimage4);
        schermata.add(playerimage5);

        schermata.add(up);
        schermata.add(right);
        schermata.add(down);
        schermata.add(left);
        schermata.add(background);

        schermata.setVisible(true);
        schermata.setResizable(false);







    }




    public String loadweaponimage(int index,int orientation)
    {
        String percorso = System.getProperty("user.dir");
        if(orientation==0)
        {
            percorso=percorso+"\\src\\main\\java\\client\\gui\\media\\cards";
        }
        if(orientation==1)
        {
            percorso=percorso+"\\src\\main\\java\\client\\gui\\media\\cardsleft";
        }
        if(orientation==2)
        {
            percorso=percorso+"\\src\\main\\java\\client\\gui\\media\\cardsright";
        }
        if(index==0)
        {
            percorso=percorso+"\\AD_weapons_IT_022.png";
        }
        if(index==1)
        {
            percorso=percorso+"\\AD_weapons_IT_023.png";
        }
        if(index==2)
        {
            percorso=percorso+"\\AD_weapons_IT_024.png";
        }
        if(index==3)
        {
            percorso=percorso+"\\AD_weapons_IT_025.png";
        }
        if(index==4)
        {
            percorso=percorso+"\\AD_weapons_IT_026.png";
        }
        if(index==5)
        {
            percorso=percorso+"\\AD_weapons_IT_027.png";
        }
        if(index==6)
        {
            percorso=percorso+"\\AD_weapons_IT_028.png";
        }
        if(index==7)
        {
            percorso=percorso+"\\AD_weapons_IT_029.png";
        }
        if(index==8)
        {
            percorso=percorso+"\\AD_weapons_IT_0212.png";
        }
        if(index==9)
        {
            percorso=percorso+"\\AD_weapons_IT_0213.png";
        }
        if(index==10)
        {
            percorso=percorso+"\\AD_weapons_IT_0214.png";
        }
        if(index==11)
        {
            percorso=percorso+"\\AD_weapons_IT_0215.png";
        }
        if(index==12)
        {
            percorso=percorso+"\\AD_weapons_IT_0216.png";
        }
        if(index==13)
        {
            percorso=percorso+"\\AD_weapons_IT_0217.png";
        }
        if(index==14)
        {
            percorso=percorso+"\\AD_weapons_IT_0218.png";
        }
        if(index==15)
        {
            percorso=percorso+"\\AD_weapons_IT_0219.png";
        }
        if(index==16)
        {
            percorso=percorso+"\\AD_weapons_IT_0220.png";
        }
        if(index==17)
        {
            percorso=percorso+"\\AD_weapons_IT_0221.png";
        }
        if(index==18)
        {
            percorso=percorso+"\\AD_weapons_IT_0222.png";
        }
        return percorso;
    }






    public String loaduserimage(int index)
    {
        String percorso = System.getProperty("user.dir");
        percorso=percorso+"\\src\\main\\java\\client\\gui\\media\\cards";
        if(index==0)
        {
            percorso=percorso+"\\Blu.png";
        }
        if(index==1)
        {
            percorso=percorso+"\\Green.png";
        }
        if(index==2)
        {
            percorso=percorso+"\\Yellow.png";
        }
        if(index==3)
        {
            percorso=percorso+"\\Pink.png";
        }
        if(index==4)
        {
            percorso=percorso+"\\Grey.png";
        }
        return percorso;
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
