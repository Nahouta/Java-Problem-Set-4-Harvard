
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class FifteenPuzzle {
    public static void main (String [] args) {
        Puzzle fifteenPuzzle = new Puzzle();
    }
    
}


class Puzzle extends JFrame {
    Font font = new Font ("Arial", Font.BOLD, 40);
    Font font2 = new Font("Heveltica", Font.BOLD, 20);
    Dimension buttonDim = new Dimension(100, 100);
    Dimension buttonDim2 = new Dimension(100, 50);

    JButton [][] buttons = new JButton[4][4];
    Clicker [][] clicks = new Clicker [4][4];

    JButton shuffle = new JButton("Shuffle");
    JButton exit = new JButton("Exit");

    public Puzzle () {
        setTitle("Fifteen Puzzle");
        this.setLayout(new GridLayout(5, 4, 4,7));

        shuffle.setPreferredSize(buttonDim2);
        shuffle.setFont(font2);
        shuffle.addActionListener(new Shuffler());

        exit.setPreferredSize(buttonDim2);
        exit.setFont(font2);
        exit.addActionListener(new Exiter());


        int counter = 1;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                buttons[i][j] = new JButton(Integer.toString(counter));
                buttons[i][j].setPreferredSize(buttonDim);
                buttons[i][j].setFont(font);

                clicks[i][j] = new Clicker();
                buttons[i][j].addActionListener(clicks[i][j]);

                this.add(buttons[i][j]);
                counter++;
            }    
        }

        this.add(shuffle);
        this.add(exit);

        buttons[3][3].setText("");

        this.setSize(new Dimension(500, 400));
        this.setVisible(true);
        this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
    }

    class Clicker implements ActionListener {

        public void actionPerformed (ActionEvent e) {
            /*Trying to locate the button that has been clicked */
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (e.getSource() == buttons[i][j]) {
                        /*We check if the found button is the empty space */
                        if (buttons[i][j].getText().equals("") ) {
                            //return; //We do nothing
                        }
                        else {
                            /*We are pretty sure that the found button is NOT empty */
                            if ( isValid (i-1, j) && buttons[i-1][j].getText().equals("")) {
                                swap(buttons[i-1][j], buttons[i][j]);
                                return;

                            }
                            else if ( isValid (i+1, j) && buttons[i+1][j].getText().equals("")) {
                                swap(buttons[i+1][j], buttons[i][j]);
                                return;
                            }
                            else if ( isValid (i, j-1) && buttons[i][j-1].getText().equals("")) {
                                swap(buttons[i][j-1], buttons[i][j]);
                                return;

                            }
                            else if ( isValid (i, j+1) && buttons[i][j+1].getText().equals("")) {
                                swap(buttons[i][j+1], buttons[i][j]);
                                return;

                            }
                        }
                    }
                }
            }
        }
    } 

    class Exiter implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            if (e.getSource() == exit) {
                System.exit(0);
            }
        }
    }

    class Shuffler implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            int [] emptyBox = new int [2];

            /*We create an array of 2 ints to store the location of the empty button */
            emptyBox[0]=0; //The i coordinate of the emptybox (emptybutton)
            emptyBox[1]=0; //The j coordinate of teh emptybox (emptybutton)



            if (e.getSource() == shuffle) {
                /*Everytime the user clicks the Shuffle button, the game operates 50 different moves.
                 * Since a lot of those are just going back and forth, every click doesn't make the puzzle particularly hard to solve
                 */
                for (int index = 0; index <= 50; index++) {
                /*We start by finding the empty button */
                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                            if (buttons[i][j].getText().equals("") ) {
                                emptyBox[0] = i;
                                emptyBox[1] = j;
                            }
                        }
                    }

                    /*We create an ArrayList of couples, to store the location of all the valid moves for the given empty box */
                    ArrayList<int []> validMoves = new ArrayList<>();

                    if ( isValid(emptyBox[0] - 1,  emptyBox[1] ) ) {
                        /*The North move is valid; We add it to the arrayList of valid moves */
                        int [] northMove = new int[2];

                        northMove[0] = emptyBox[0] - 1;
                        northMove[1] = emptyBox[1];
                        validMoves.add(northMove);
                    }
                    if ( isValid(emptyBox[0] + 1,  emptyBox[1] ) ) {
                        /*The South move is valid; We add it to the arrayList of valid moves*/
                        int [] southMove = new int[2];

                        southMove[0] = emptyBox[0] + 1;
                        southMove[1] = emptyBox[1];
                        validMoves.add(southMove);
                    }
                    if ( isValid(emptyBox[0],  emptyBox[1] - 1) ) {
                        /*The East move is valid; We add it to the arrayList of valid moves*/
                        int [] eastMove = new int[2];

                        eastMove[0] = emptyBox[0];
                        eastMove[1] = emptyBox[1] - 1;
                        validMoves.add(eastMove);
                    }
                    if ( isValid(emptyBox[0],  emptyBox[1] + 1) ) {
                        /*The West move is valid; We add it to the arrayList of valid moves*/
                        int [] westMove = new int[2];

                        westMove[0] = emptyBox[0];
                        westMove[1] = emptyBox[1] + 1;
                        validMoves.add(westMove);
                    }

                    /*From one of the validMoves, we pick one at random and execute it */
                    Random random = new Random();
                    int randomInt = random.nextInt(validMoves.size());
                    /*Retrieve the coordinates corresponding to the random moves */
                    swap ( buttons[emptyBox[0]][emptyBox[1]] , buttons[ validMoves.get(randomInt)[0] ] [ validMoves.get(randomInt)[1] ]);
                        

                }
                return;

            }
        }
    }

    boolean isValid (int i, int j) {
        if (i >= 0 && i <= 3 && j >= 0 && j <= 3) {
            return true;
        }
        else {
            return false;
        }
    }

    void swap (JButton b1, JButton b2) {
        String temp = new String (b1.getText());
        b1.setText(b2.getText());
        b2.setText(temp);
    }


}
