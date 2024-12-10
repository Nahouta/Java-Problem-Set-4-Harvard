//BullsEye.java

/**
 * This program will draw a set of concentric rings in alternating black and white colors.
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class BullsEye {

    public static void main (String [] args) {
        JFrame f = new JFrame ("BullEyes");
        f.setSize (515, 600);
        f.add (new targets());
        f.setVisible (true);
        f.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
    } 
}

/**
 * The class extending the JPanel for the graphics purposes.
 */
class targets extends JPanel {
    public void paintComponent (Graphics g)
    {
       g.setColor (Color.BLACK);
       g.fillOval (0, 0, 500, 500);

       g.setColor (Color.WHITE);
       g.fillOval (50, 50, 400, 400);

       g.setColor (Color.BLACK);
       g.fillOval (100, 100, 300, 300);

       g.setColor (Color.WHITE);
       g.fillOval (150, 150, 200, 200);

       g.setColor (Color.BLACK);
       g.fillOval (200, 200, 100, 100);
    }
}