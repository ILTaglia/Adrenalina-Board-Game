package client.gui.media.cards;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login {
    public Button inizia;


    public void mostra()
    {
        JFrame schermata = new JFrame("ADRENALINA");
        schermata.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        schermata.setSize(400,400);
        schermata.setLayout(null);


        JButton inizia = new JButton("Inizia!");
        inizia.setBounds(10, 90, 200, 25);
        JTextField user = new JTextField("Username");
        user.setBounds(170, 10, 80, 25);
        JTextField pass = new JTextField("Password");
        pass.setBounds(170, 50, 80, 25);
        JLabel usertext = new JLabel("Inserire l'Username:");
        usertext.setBounds(10, 10, 150, 25);
        JLabel passtext = new JLabel("Inserire la password:");
        passtext.setBounds(10, 50, 150, 25);

        schermata.add(user);
        schermata.add(pass);
        schermata.add(usertext);
        schermata.add(passtext);
        schermata.add(inizia);
        schermata.setVisible(true);
        inizia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inizia.setBackground(Color.RED);
                inizia.repaint();
                System.out.println(user.getText());
                System.out.println(pass.getText());
            }
        });


    }


    public static void main(String args[])
    {

        Login schermata = new Login();
        schermata.mostra();


    }
}
