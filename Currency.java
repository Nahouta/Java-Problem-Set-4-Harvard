//Currency.java
/**
 * This Program will convert between the Dollar and Euro Currencies, using the 
 * Conversion rate of 1 EURO = 1.13 USD
 * @author Tresor Habib Nahouta
 * @version Last modified April 10th 2024
 */



import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

public class Currency {
    public static void main (String [] args) {
        Converter convert = new Converter();
    }
}


class Converter extends JFrame {
    Font font = new Font ("Helvetica", Font.BOLD, 10);
    Dimension fieldSize = new Dimension (100, 30);

    JLabel usd = new JLabel("USD");
    JLabel euro = new JLabel("EURO");

    JTextField usdField = new JTextField("");
    JTextField euroField = new JTextField("");

    JButton u2e = new JButton(">");
    JButton e2u = new JButton("<");

    public Converter () {
        setTitle("USD - EURO CONVERTER");
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.X_AXIS));
        this.setSize(new Dimension(400, 200));

        usdField.setPreferredSize(fieldSize);
        usdField.setMaximumSize( new Dimension(1920, fieldSize.height) );

        euroField.setPreferredSize(fieldSize);
        euroField.setMaximumSize( new Dimension(1920, fieldSize.height) );

        ConvertU2E cU2e = new ConvertU2E();
        u2e.addActionListener(cU2e);
        usdField.addActionListener(cU2e);

        ConvertE2U cE2u = new ConvertE2U();
        e2u.addActionListener(cE2u);
        euroField.addActionListener(cE2u);

        usd.setFont(font);
        euro.setFont(font);
        usdField.setFont(font);
        euroField.setFont(font);
        u2e.setFont(font);
        e2u.setFont(font);


        this.add(usd);

        this.add(usdField);

        this.add(u2e);

        this.add(e2u);
        this.add(euroField);
        this.add(euro);

        this.setVisible(true);
        this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
    }

    /**
     * This class is an action listener, for both clicking on the button to convert from USD to EURO,
     * Or hitting RETURN in the USD field (Which assusmes that the user wants to convert into EURO)
     * The program will prompt the user about an error  if they enter a non numerical value
     */
    class ConvertU2E implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            if (e.getSource() == u2e || e.getSource() == usdField) {
                try {
                    double usdAmount = Double.parseDouble(usdField.getText());
                    double euroAmount = usdAmount/1.13;
                    DecimalFormat df = new DecimalFormat("#.##");
                    euroField.setText( df.format(euroAmount) );
                }
                catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(null,"The value entered is not numerical. Please enter a numerical value.");
                }
            }
        }
    }

    /**
     * This class is an action listener, for both clicking on the button to convert from EURO to USD,
     * Or hitting RETURN in the EURO field (Which assusmes that the user wants to convert into USD)
     * The program will prompt the user about an error if they enter a non numerical value
     */
    class ConvertE2U implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            if (e.getSource() == e2u || e.getSource() == euroField) {
                try {
                    double euroAmount = Double.parseDouble(euroField.getText());
                    double usdAmount = euroAmount*1.13;
                    DecimalFormat df = new DecimalFormat("#.##");
                    usdField.setText( df.format(usdAmount) );
                }
                catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(null,"The value entered is not numerical. Please enter a numerical value.");
                }
                
            }
        }
    }


}