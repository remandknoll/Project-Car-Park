import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GUI
{
    public JFrame frame3;
    public JTextArea textArea2;
    public JTextArea textArea3;
    public JTextArea textArea4;
    public JTextArea textArea5;
    public JTextArea textArea6;
    

    /**
     * Constructor for objects of class GUI
     */
    public GUI()
    {
        frame3 = new JFrame("AmountofregularCars");
        frame3.setLayout(new BoxLayout(frame3, BoxLayout.PAGE_AXIS));
        frame3.getContentPane().setLayout(new BoxLayout(frame3.getContentPane(), BoxLayout.PAGE_AXIS));
        Container contentPane3 = frame3.getContentPane();
        textArea2 = new JTextArea("Aantal auto's weggegaan met Parking Pass: " + "0");
        textArea3 = new JTextArea("\n");
        textArea4 = new JTextArea("Aantal auto's weggegaan zonder Parking Pass: 0");
        textArea5 = new JTextArea("\n");
        textArea6 = new JTextArea("Totale omzet: €0");
        contentPane3.add(textArea2);
        contentPane3.add(textArea3);
        contentPane3.add(textArea4);
        contentPane3.add(textArea5);
        contentPane3.add(textArea6);
        frame3.pack();
    }
    // With this method it will show the GUI.
    public void openGUI()
    {
        frame3.setVisible(true);
    }
}
