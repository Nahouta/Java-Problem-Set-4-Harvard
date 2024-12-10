import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator {
    public static void main (String [] args) {
        CalcBackend backend = new CalcBackend();
        CalcGUI gui = new CalcGUI(backend);
    }
}


class CalcGUI extends JFrame {
    Font font = new Font ("Arial", Font.BOLD, 40);
    Dimension buttonDim = new Dimension(100, 100);
    JLabel screen = new JLabel("0");

    char CLEARCHAR =          'C';
    char SQRTCHAR  =          '\u221A';
    char MULTIPLICATIONCHAR = '*';
    char DIVISIONCHAR =       '/';
    char ADDITIONCHAR =       '+';
    char SUBTRACTIONCHAR =    '-';
    char EQUALS = '=';


    JButton clearButton = new JButton(Character.toString(CLEARCHAR));
    JButton sqrtButton = new JButton(Character.toString(SQRTCHAR));
    JButton multiplyButton = new JButton(Character.toString(MULTIPLICATIONCHAR));
    JButton divideButton = new JButton(Character.toString(DIVISIONCHAR));
    JButton plusButton = new JButton(Character.toString(ADDITIONCHAR));
    JButton minusButton = new JButton(Character.toString(SUBTRACTIONCHAR));
    JButton equalsButton = new JButton(Character.toString(EQUALS));

    JButton one = new JButton("1");
    JButton two = new JButton("2");
    JButton three = new JButton("3");
    JButton four = new JButton("4");
    JButton five = new JButton("5");
    JButton six = new JButton("6");
    JButton seven = new JButton("7");
    JButton eight = new JButton("8");
    JButton nine = new JButton("9");
    JButton zero = new JButton("0");
    JButton point = new JButton(".");

    JButton dummy1 = new JButton("");
    JButton dummy2 = new JButton("");

    JButton [][] allbuttons = new JButton[5][4];

    //Fields related to transmission with BackEnd

    CalcBackend backend;
    char toSend = '0';


    public CalcGUI (CalcBackend bEnd) {//Should take a backEnd object as parameter, to be able to call non-static methods on it
        setTitle("Calculator");
        screen.setPreferredSize(new Dimension(200, 50));
        screen.setFont(font);
        screen.setBackground(Color.RED);
        screen.setHorizontalAlignment(SwingConstants.LEFT);

        //Disable the dummy buttons used to fill the empty cells of the grid
        dummy1.setEnabled(false);
        dummy2.setEnabled(false);

        //this.setLayout(new BorderLayout());

        //Line 0
        allbuttons[0][0] = clearButton;
        allbuttons[0][1] = sqrtButton;
        allbuttons[0][2] = divideButton;
        allbuttons[0][3] = multiplyButton;
        //Line 1
        allbuttons[1][0] = seven;
        allbuttons[1][1] = eight;
        allbuttons[1][2] = nine;
        allbuttons[1][3] = minusButton;
        //Line 2
        allbuttons[2][0] = four;
        allbuttons[2][1] = five;
        allbuttons[2][2] = six;
        allbuttons[2][3] = plusButton;
        //Line 3
        allbuttons[3][0] = one;
        allbuttons[3][1] = two;
        allbuttons[3][2] = three;
        allbuttons[3][3] = equalsButton;
        //Line 4
        allbuttons[4][0] = dummy1;
        allbuttons[4][1] = zero;
        allbuttons[4][2] = point;
        allbuttons[4][3] = dummy2;
        

        //Adding the Screen
        this.add(screen, BorderLayout.NORTH);

        //Setting up the fonts of all buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 4,7));
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                allbuttons[i][j].setPreferredSize(buttonDim);
                allbuttons[i][j].setFont(font);
                allbuttons[i][j].addActionListener(new Clicker());
                buttonPanel.add(allbuttons[i][j]);
            }    
        }

        //Getting ready to display the GUI
        this.setSize(new Dimension(400, 600));
        this.add(buttonPanel);
        this.setVisible(true);
        this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);

        /*Code related to communication with BackEnd object: */
        backend = bEnd;

    }

    class Clicker implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            /*Trying to locate the button that has been clicked */
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 4; j++) {
                    if (e.getSource() == allbuttons[i][j]) {
                        //Code to feed char to the CaclBackend
                        backend.feedChar( allbuttons[i][j].getText().charAt(0) );
                        screen.setText(backend.getDisplayVal());
                        //screen.setText(display);
                    }
                }
            }
        }
    }  
    
    






}