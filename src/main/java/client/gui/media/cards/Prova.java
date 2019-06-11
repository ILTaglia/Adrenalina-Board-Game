package client.gui.media.cards;

//Usually you will require both swing and awt packages
// even if you are working with just swings.
import javax.swing.*;
import java.awt.*;
class prova {
    public static void main(String args[]) {

        //Creating the Frame
        JFrame frame = new JFrame("Chat Frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 900);

        //Creating the MenuBar and adding components
        JMenuBar barramenu = new JMenuBar();
        JMenu cel1 = new JMenu("Impostazioni");
        barramenu.add(cel1);
        JMenu cel2 = new JMenu("Aiuto");
        barramenu.add(cel2);
        JMenu cel3 = new JMenu("Extra");
        barramenu.add(cel3);
        JMenuItem scritta1= new JMenuItem("Ciao");
        cel3.add(scritta1);
        JMenuItem m11 = new JMenuItem("Open");
        cel1.add(m11);
        JMenuItem m22 = new JMenuItem("Save as");
        cel1.add(m22);

        //Creating the panel at bottom and adding components
        JPanel pannello = new JPanel(); // the panel is not visible in output
        JLabel scritta = new JLabel("Enter Text");
        pannello.add(scritta);
        JTextField campoditesto = new JTextField(20); // accepts upto 10 characters
        pannello.add(campoditesto);
        JButton pulsante1 = new JButton("Send");
        pannello.add(pulsante1);
        JButton pulsante2 = new JButton("Reset");
        pannello.add(pulsante2);
        JButton pulsante3 = new JButton("Indietro");
        pannello.add(pulsante3);

        // Text Area at the Center
        JTextArea foglio = new JTextArea();

        //Adding Components to the frame.
        frame.getContentPane().add(BorderLayout.SOUTH, pannello);
        frame.getContentPane().add(BorderLayout.NORTH, barramenu);
        frame.getContentPane().add(BorderLayout.CENTER, foglio);
        frame.setVisible(true);
    }

    public void mostraframe()
    {
        JFrame frame = new JFrame("Flow");
        frame.setLayout(new FlowLayout());
        frame.add(new JButton("Ciao"));
        frame.add(new JButton("Premi"));
        frame.add(new JButton("Questo"));
        frame.add(new JButton("Bottone"));
        frame.add(new JButton("!!!!!"));
        frame.pack();
        frame.setVisible(true);
    }


}