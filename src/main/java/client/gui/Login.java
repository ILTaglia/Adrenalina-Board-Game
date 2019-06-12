package client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login {
    public Button inizia;


    public void mostra()
    {
        int offset=120;
        JFrame schermata = new JFrame("ADRENALINA");
        schermata.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        schermata.setSize(700,350);
        schermata.setLayout(null);


        JButton inizia = new JButton("Inizia!");
        inizia.setBounds(210, 90+offset, 200, 25);
        JTextField user = new JTextField("Username");
        user.setBounds(370, 10+offset, 80, 25);
        JTextField pass = new JTextField("Password");
        pass.setBounds(370, 50+offset, 80, 25);
        JLabel usertext = new JLabel("Inserire l'Username:");
        usertext.setBounds(210, 10+offset, 150, 25);
        JLabel passtext = new JLabel("Inserire la password:");
        passtext.setBounds(210, 50+offset, 150, 25);


        JLabel conntext = new JLabel("Se lo desideri, seleziona la tipologia di connessione");
        conntext.setForeground(Color.RED);
        conntext.setBounds(210,150+offset,300,25);


        String percorso = System.getProperty("user.dir");
        percorso=percorso+"\\src\\main\\java\\client\\gui\\media\\adrenalina2.png";
        System.out.println(percorso);
        ImageIcon sfondo = new ImageIcon(percorso);
        JLabel background = new JLabel("",sfondo,JLabel.CENTER);
        background.setBounds(0,0,700,350);


        String [] scelta = {"RMI","SOCKET"};
        JComboBox sceltaconnessione = new JComboBox(scelta);
        sceltaconnessione.setBounds(500,150+offset,80,25);


        schermata.add(user);
        schermata.add(pass);
        schermata.add(usertext);
        schermata.add(passtext);
        schermata.add(inizia);
        schermata.add(conntext);
        schermata.add(sceltaconnessione);
        schermata.add(background);
        schermata.setVisible(true);
        schermata.setResizable(false);
        inizia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inizia.setBackground(Color.RED);
                inizia.repaint();
                System.out.println(user.getText());
                System.out.println(pass.getText());
                System.out.println(sceltaconnessione.getSelectedIndex());
            }
        });


    }


    public static void main(String args[])
    {

        Login schermata = new Login();
        schermata.mostra();


    }
}
