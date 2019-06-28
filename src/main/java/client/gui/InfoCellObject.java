package client.gui;

import model.Ammo;

import javax.swing.*;

public class InfoCellObject {
    Ammo ammo;
    public InfoCellObject(Ammo ammo)
    {
        this.ammo=ammo;
    }

    public void mostra()
    {
        JFrame schermata = new JFrame("Ammo");
        schermata.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        schermata.setSize(300,300);


        String userimage2 = loadammoimage();
        ImageIcon user2 = new ImageIcon(userimage2);
        JLabel playerimage2 = new JLabel(user2);

    }

    public String loadammoimage()
    {

        String percorso = System.getProperty("user.dir");
        percorso=percorso+"\\src\\main\\java\\client\\gui\\media\\MR2.png";
        return percorso;
    }
}
