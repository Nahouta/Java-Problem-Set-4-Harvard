//Age.java

/**
 * This program pops up an option pane and asks for the user's age, and returns a message 
 * saying that the user is old is the age is 40 or higher, and young if it is less than 40
 * The program also handles improper input case, and will loop until a numerical value is entered.
 * @author Tresor Habib Nahouta
 * @version Last modified April 6th, 2024
 */
import javax.swing.*;


public class Age {
    public static void main (String [] args) {
        boolean isValidInput = true;
        do {
            try {
                String input = JOptionPane.showInputDialog("What's your age, cowboy?");
                Double age = Double.parseDouble(input);
        
                if (age >= 0 && age < 40) {
                    JOptionPane.showMessageDialog(null,"You are still young ! Congratulations!");
                    isValidInput = true;
                }
                else if (age >= 40){
                    JOptionPane.showMessageDialog(null, "You are old. Sorry");
                    isValidInput = true;
                }
                else {
                    JOptionPane.showMessageDialog(null, "Come on! You can't have a negative age. Try again please!");
                    isValidInput = false;
                }
            }
            /*We handle the case where the input is not a numerical value */
            catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,"The value entered is not numerical. Please enter a numerical value.");
                isValidInput = false;
            };
        }
        while (!isValidInput);
    }
}
