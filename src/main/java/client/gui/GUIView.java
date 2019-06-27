package client.gui;

import model.Match;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GUIView {
    private JFrame f;
    private CardsPanel cardsPanel;
    private MapPanel mapPanel;
    private JButton musicButton;
    //private MP3 mp3;
    private Waiter waiter;
    private Match match;

    private int idClient;

    private static final Logger LOGGER= Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


    private boolean cardToBuy=false;
    private Popup popup;


    public GUIView() {
        this.waiter = new Waiter();

        BufferedImage img = null;
        LOGGER.setLevel(Level.INFO);

        try {
            img= ImageIO.read(GUIView.class.getResource("AD_B1_material_08.png"));
        } catch (IOException e1) {
            LOGGER.log(Level.FINEST,e1.getMessage(),e1);
        }

        f=new JFrame("Adrenalina");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(900,700);
        f.setContentPane(new JLayeredPane());

        mapPanel=new MapPanel(mapMouseManager);
        f.add(mapPanel);
        mapPanel.setBounds(0, 0, 453, 700);


        cardsPanel=new CardsPanel();
        f.add(cardsPanel);
        cardsPanel.setBounds(453, 0, 447, 270);
    }

    private MouseInputAdapter mapMouseManager = new MouseInputAdapter() {
        public void mouseClicked(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e)) {
                if (e.getSource() == GUIView.this.musicButton) {
                    //TODO cambiare stato musica
                }
            }
        }
    };

    public void welcomeMessage(int idClient) {
        this.idClient=idClient;
        this.popup.showMessage("Welcome to Adrenalina!");
    }

    /*To make the player select which card does it wants to buy*/
    public void getCardToBuy() {
        cardToBuy=true;
        mapPanel.showCardToBuy(match, idClient);
    }


}

