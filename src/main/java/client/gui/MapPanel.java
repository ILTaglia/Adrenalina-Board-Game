package client.gui;

import model.Player;
import model.Match;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MapPanel extends JLayeredPane {
    private static final long serialVersionUID = -4687295452861842127L;
    private MouseInputAdapter mapManager;
    private static final String TEXT_FONT="Comic Sans MS";

    //TODO Tiene traccia degli elementi aggiunti in questo turno, così da poterli eliminare ad ogni turno ed evitare che si sprechi troppa memoria
    //private List<JComponent> guiItemsAddedInBuyPhase=new ArrayList<JComponent>();
    //private List<JComponent> guiItemsAddedInSellPhase=new ArrayList<JComponent>();
    private JButton endButton;


    /*mapManager variabile che serve alla classe per dire quando viene fatto click su qualche elemento*/
    MapPanel(MouseInputAdapter mapManager){
        this.setBounds(60, 50, 780, 580);

        JLabel backGroundImage=new JLabel();
        backGroundImage.setBounds(0, 0, 780, 580);
        //backGroundImage.setIcon(getImage(""));
        this.setLayer(backGroundImage, 1);
        this.add(backGroundImage);

        this.mapManager=mapManager;

        //Setto il titolo
        JLabel title=new JLabel("Map");
        title.setBounds(140, 50, 500, 100);
        Font font=new Font(TEXT_FONT, Font.BOLD, 50);
        title.setForeground(new Color(255,146,0));
        title.setFont(font);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        this.setLayer(title, 2);
        this.add(title);

        //Setto il bottone che termina la fase
        endButton=new JButton();
        endButton.setBounds(700, 500, 50, 50);
        //endButton.setIcon(getImage(""));
        endButton.setOpaque(false);
        endButton.setBorder(null);
        endButton.setBorderPainted(false);
        endButton.setContentAreaFilled(false);
        endButton.addMouseListener(this.mapManager);
        this.setLayer(endButton, 2);
        this.add(endButton);
    }

    /*Method to show the weapon card the player is able to buy
     */
    public void showCardToBuy(Match match, int idClient){
        //TODO clean the previous dashboard config

        endButton.setEnabled(true);

        //Creo l'etichetta che dice cosa fare
        JLabel tip=new JLabel("Choose the card you want to buy");
        tip.setBounds(50, 155, 270, 50);
        Font font=new Font(TEXT_FONT, Font.BOLD, 16);
        tip.setForeground(new Color(255,170,30));
        tip.setFont(font);
        this.setLayer(tip, 2);
        this.add(tip);
        //TODO addition



        int i=0;
        Player player = match.getActivePlayer();

        //spazio tra le varie colonne di carte
        int interspace=40;
        int offset = 20;
        //TODO offset?

        for(Player p:match.getPlayers()){
            if(!p.getid().equals(idClient)){
                //Stampo una label col nome del giocatore
                JLabel label=new JLabel();
                font=new Font(TEXT_FONT, Font.BOLD, 20);
                label.setForeground(new Color(255,170,30));
                label.setFont(font);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setText(p.getname());
                label.setBounds(30-30+offset+110*i, 200, 150, 50);
                label.setBackground(new Color(0,0,255,180));
                label.setOpaque(true);
                this.setLayer(label, 2);
                this.add(label);
                //TODO addition
                //incremento l'indice per posizionare le carte in un numero adeguato di colonne
                i++;
            }
        }
    }

}
