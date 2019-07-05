package client.gui;
import javax.swing.*;

public class AmmoC extends JLabel {
    private JLabel cost;
    private int id;

    /**
     *
     * @param ammoCard is the ammoCard on the match
     * @param x is the number of line of the normal cell in which it is
     * @param y is the number of column of the normal cell in which it is
     */
    AmmoC(model.AmmoCard ammoCard, int x, int y){
        //type convention: AmmoTile 0 AmmoPowTile 1
        ImageIcon image;
        if(ammoCard.getType()==0){
            if(ammoCard.toString().equals("00")){image=this.getImageAmmo("AD_ammo_0421.png");}
            if(ammoCard.toString().equals("11")){image=this.getImageAmmo("AD_ammo_0422.png");}
            if(ammoCard.toString().equals("22")){image=this.getImageAmmo("AD_ammo_0420.png");}
            if(ammoCard.toString().equals("12")){image=this.getImageAmmo("AD_ammo_0424.png");}
            if(ammoCard.toString().equals("02")){image=this.getImageAmmo("AD_ammo_0423.png");}
            if(ammoCard.toString().equals("10")){image=this.getImageAmmo("AD_ammo_0425.png");}
            else return;

        }
        else {
            if(ammoCard.toString().equals("011")){image=this.getImageAmmo("AD_ammo_044.png");}
            if(ammoCard.toString().equals("211")){image=this.getImageAmmo("AD_ammo_042.png");}
            if(ammoCard.toString().equals("200")){image=this.getImageAmmo("AD_ammo_043.png");}
            if(ammoCard.toString().equals("100")){image=this.getImageAmmo("AD_ammo_047.png");}
            if(ammoCard.toString().equals("022")){image=this.getImageAmmo("AD_ammo_045.png");}
            if(ammoCard.toString().equals("122")){image=this.getImageAmmo("AD_ammo_046.png");}
            else return;

        }
        this.setIcon(image);
        cost=new JLabel();
        //this.setCost(0);
        this.setLocation(x, y);
        cost.setSize(50, 50);
    }

    private ImageIcon getImageAmmo(String fileName){
        return new ImageIcon(CardsPanel.class.getResource("/client/gui/media/ammo/"+fileName));
    }
}
