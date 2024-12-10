// Implement your calculator math logic in this class.

// You MUST initialize the calculator's state in the zero-arg constructor. You MUST NOT
// change feedChar's and getDisplayVal's signature or functionality. You MUST NOT have
// ANY other non-private members of this class.

// I will test your CalcBackend class via an expanded version of CalcBackendTest.java,
// which will create an instance of CalcBackend with the zero-arg constructor, pass
// simulated button clicks via feedChar, and retrieve resulting display Strings with
// getDisplayVal.

// Note that when I test your CalcBackend class, I am ONLY using your feedChar and
// getDisplayVal methods. That means that my tests COMPLETELY bypass your Calculator
// class. That's because  your Calculator class is not supposed to have ANY involvement
// with your calculator's computations. Your Calculator class should ONLY layout the
// calculator's JFrame, attach listeners, feed button clicks to CalcBackend via feedChar
// and then update the calculator's display via getDisplayVal.

// I would appreciate it if you would include comments like these (but with your actual char
// values) in CalcBackend.java, because it will let me test your code without investigating
// to see what character is passed to feedChar for the various operators:

//@@ CLEARCHAR =          'C',
//@@ SQRTCHAR  =          '\u221A',
//@@ MULTIPLICATIONCHAR = '*',
//@@ DIVISIONCHAR =       '/',
//@@ ADDITIONCHAR =       '+',
//@@ SUBTRACTIONCHAR =    '-';

import javax.swing.JOptionPane;

public class CalcBackend {

    // Variables defining calculator's internal state
    enum State {
        RFO, //Ready for 1st operand : The very first iteration of the program
        RSO, //Ready for 2nd operand : There is a result from the previous operation
        CLO, //Constructing operand, left of decimal point
        CRO //Constructing operant, right of decimal point   
    }

    enum ButtonCategory {
        Digit,
        DecimalPoint,
        EqualsSign,
        BinaryOp,
        UnaryOp,
        clear
    }




    double displayVal; // Always contains double value matching GUI's display
    //String displayVal = "";   //TESTER LINE
    State state;
    double previousVal;
    double integerPart;
    double decimalPart;

    char operator;
    char decimalTracker;

    // Zero-arg constructor initializes calculator's state
    public CalcBackend() {
        this.state = State.RFO;
        displayVal = 0;
        previousVal = 0;//The result from the precedent calculation
        integerPart = 0;
        decimalPart = 0;
        operator = '0'; 
        decimalTracker = '0';

        /*Will help us keep track of what is happening at every stage;
         '0' => concatenation of the decimal part without the decimal point 
         '.' => concatenation with decimal point before adding the decimal part
         '+-/*' => Normal operation between previousVal et displayVal
         */
        
    }

    // feedChar is called by GUI to tell CalcBackend that a particular button was clicked
    public void feedChar(char c) {

        //FIRST ROUND OF STATE CASES: IS READY FOR FIRST OPERAND------------------------------------------------------------------------------------------
        if (state == State.RFO && category(c) == ButtonCategory.Digit) {
            /*The very first execution*/

            displayVal = Double.parseDouble(Character.toString(c));
            /*Then, we switch to build mode, and assume that we are building the left part of the first operand*/
            state = State.CLO;
        }
        else if (state == State.RFO && category(c) == ButtonCategory.DecimalPoint) {
            /*Stores the previous value in memory, resets the current value and rebuilds it.
             * But in this case, we switch directly to the second half of the current value,
             * and the integer part is set to zero.
            */
            //previousVal = displayVal;

            //integerPart = 0;

            displayVal = 0;
            decimalTracker = '.';
            state = State.CRO;
        }
        else if (state == State.RFO && category(c) == ButtonCategory.EqualsSign) {
            if ( operator == '0') {
                /*We don't do anything because it is the first operand */
            }
            else if ( operator == '+' ) {
                displayVal = previousVal + displayVal;  
            }
            else if ( operator == '-' ) {
                displayVal = previousVal - displayVal;
            }
            else if ( operator == '*' ) {
                displayVal = previousVal * displayVal;
            }
            else if ( operator == '/' ) {
                displayVal = previousVal / displayVal;
            }
            
            operator = '0';
            decimalTracker = '0';
            state = State.RFO;
        }
        else if (state == State.RFO && category(c) == ButtonCategory.BinaryOp) {
            /*We update the operator */
            displayVal = 0;
            operator = c;
            state = State.CLO;

        }
        else if (state == State.RFO && category(c) == ButtonCategory.UnaryOp) {
            /*We operate directly onto the value */
            displayVal = Math.sqrt(displayVal);
            operator = '0';
            decimalTracker = '0';
            state = State.RFO;
        }

        //SECOND ROUND OF STATE CASES: CONSTRUCTING OPERAND, LEFT OF DECIMAL POINT------------------------------------------------------------------
        else if (state == State.CLO && category(c) == ButtonCategory.Digit) {
            /*We just concatenate the new digit with the previous value */
            displayVal = Double.parseDouble(Double.toString((int)displayVal) + Character.toString(c));
            state = State.CLO;
        }
        else if (state == State.CLO && category(c) == ButtonCategory.DecimalPoint) {
            /*We store the value of displayVal as the first half (integer) of whatever operand it is */
            /*Since we are not sure yet if there will be a decimal part, we assume that the full number is the integer */
            //integerPart = displayVal;

            /*displayVal remains equal to whatever it is, but we get a warning that we need to add the next portion
             * of integers as decimal part
             */
            decimalTracker = '.';
            state = State.CRO;
        }
        else if (state == State.CLO && category(c) == ButtonCategory.EqualsSign) {
            if ( operator == '0') {
                /*We don't do anything because it is the first operand */
            }
            else if ( operator == '+' ) {
                displayVal = previousVal + displayVal;  
            }
            else if ( operator == '-' ) {
                displayVal = previousVal - displayVal;
            }
            else if ( operator == '*' ) {
                displayVal = previousVal * displayVal;
            }
            else if ( operator == '/' ) {
                displayVal = previousVal / displayVal;
            }

            previousVal = displayVal;
            operator = '0';
            decimalTracker = '0';
            state = State.RSO;
        }
        else if (state == State.CLO && category(c) == ButtonCategory.BinaryOp) {
            /*We store the current value as the 1st operand, and store the operator */
            if ( operator == '0') {
                /*We don't do anything because it is the first operand */
            }
            else if ( operator == '+' ) {
                displayVal = previousVal + displayVal;  
            }
            else if ( operator == '-' ) {
                displayVal = previousVal - displayVal;
            }
            else if ( operator == '*' ) {
                displayVal = previousVal * displayVal;
            }
            else if ( operator == '/' ) {
                displayVal = previousVal / displayVal;
            }

            previousVal = displayVal;
            operator = c;
            state = State.RSO;
        }
        else if (state == State.CLO && category(c) == ButtonCategory.UnaryOp) {
            /*We check whether or not there is an ongoing operation,
            perform the current operation, then proceed with the remaining op*/

            displayVal = Math.sqrt(displayVal);

            if ( operator == '0') {
                /*We don't do anything because it is the first operand */
            }
            else if ( operator == '+' ) {
                displayVal = previousVal + displayVal;  
            }
            else if ( operator == '-' ) {
                displayVal = previousVal - displayVal;
            }
            else if ( operator == '*' ) {
                displayVal = previousVal * displayVal;
            }
            else if ( operator == '/' ) {
                displayVal = previousVal / displayVal;
            }
            previousVal = displayVal;
            operator = '0';
            state = State.RSO;
        }

        //THIRD ROUND OF STATE CASES: CONSTRUCTING OPERAND, RIGHT OF DECIMAL POINT----------------------------------------------------------------------------
        else if (state == State.CRO && category(c) == ButtonCategory.Digit) {
            /*We concatenate both the integer and decimal parts and keep the state to CRO*/
            if (decimalTracker == '.') {
                displayVal = Double.parseDouble(Double.toString(displayVal) + Character.toString(c));
                state = State.CRO;
            }
            else {
                /*We just keep concatenating */
                displayVal = Double.parseDouble(Double.toString(displayVal) + Character.toString(c));
                state = State.CRO;
            }

        }
        else if (state == State.CRO && category(c) == ButtonCategory.DecimalPoint) {
            /*Since we can't have 2 decimal points on the same number, we cancel everything */
            state = State.RFO;
            displayVal = 0;
            decimalTracker = '0';
            operator = '0';

        }
        else if (state == State.CRO && category(c) == ButtonCategory.EqualsSign) {
            /*We perform the operation */
            if ( operator == '0') {
                /*We don't do anything because it is the first operand */
            }
            else if ( operator == '+' ) {
                displayVal = previousVal + displayVal;  
            }
            else if ( operator == '-' ) {
                displayVal = previousVal - displayVal;
            }
            else if ( operator == '*' ) {
                displayVal = previousVal * displayVal;
            }
            else if ( operator == '/' ) {
                displayVal = previousVal / displayVal;
            }
            previousVal = displayVal;
            operator = '0';
            decimalTracker = '0';
            state = State.RSO;

        }
        else if (state == State.CRO && category(c) == ButtonCategory.BinaryOp) {
            /*We start by performing the previous operation */
            if ( operator == '0') {
                /*We don't do anything because it is the first operand */
            }
            else if ( operator == '+' ) {
                displayVal = previousVal + displayVal;  
            }
            else if ( operator == '-' ) {
                displayVal = previousVal - displayVal;
            }
            else if ( operator == '*' ) {
                displayVal = previousVal * displayVal;
            }
            else if ( operator == '/' ) {
                displayVal = previousVal / displayVal;
            }

            /*Then we store the result in the previousVal and get ready for the next operation */
            previousVal = displayVal;
            operator = c;
            decimalTracker = '0';
            state = State.RSO;
        }
        else if (state == State.CRO && category(c) == ButtonCategory.UnaryOp) {
            /*We first perform the operation on the current number */
            displayVal = Math.sqrt(displayVal);

            /*Then we perform the previous operation that was left on hold */
            if ( operator == '0') {
                /*We don't do anything because it is the first operand */
            }
            else if ( operator == '+' ) {
                displayVal = previousVal + displayVal;  
            }
            else if ( operator == '-' ) {
                displayVal = previousVal - displayVal;
            }
            else if ( operator == '*' ) {
                displayVal = previousVal * displayVal;
            }
            else if ( operator == '/' ) {
                displayVal = previousVal / displayVal;
            }

            /*We then get ready for the next operation */
            previousVal = displayVal;
            operator = '0';
            decimalTracker = '0';
            state = State.RSO;
        }

        //FOURTH ROUND OF STATE CASES: IS READY FOR SECOND OPERAND------------------------------------------------------------------------------------

        else if (state == State.RSO && category(c) == ButtonCategory.Digit) {
            /*We concatenate with the current value */
            displayVal = Double.parseDouble(Character.toString(c));
            decimalTracker = '0';
            state = State.CLO;
        }
        else if (state == State.RSO && category(c) == ButtonCategory.DecimalPoint) {
            displayVal = 0;
            decimalTracker = '.';
            state = State.CRO;
        }
        else if (state == State.RSO && category(c) == ButtonCategory.EqualsSign) {
                        /*We perform the operation */
            if ( operator == '0') {
                /*We don't do anything because it is the first operand */
            }
            else if ( operator == '+' ) {
                displayVal = previousVal + displayVal;  
            }
            else if ( operator == '-' ) {
                displayVal = previousVal - displayVal;
            }
            else if ( operator == '*' ) {
                displayVal = previousVal * displayVal;
            }
            else if ( operator == '/' ) {
                displayVal = previousVal / displayVal;
            }
            previousVal = displayVal;
            operator = '0';
            decimalTracker = '0';
            state = State.RSO;
        }
        else if (state == State.RSO && category(c) == ButtonCategory.BinaryOp) {
            /*We just update the nature of the operation to be performed */
            operator = c;
            state = State.CLO;
        }
        else if (state == State.RSO && category(c) == ButtonCategory.UnaryOp) {
            /*We first perform the operation on the current value */
            displayVal = Math.sqrt(displayVal);

            /*Then we perform any remaining operation */
            if ( operator == '0') {
                /*We don't do anything because it is the first operand */
            }
            else if ( operator == '+' ) {
                displayVal = previousVal + displayVal;  
            }
            else if ( operator == '-' ) {
                displayVal = previousVal - displayVal;
            }
            else if ( operator == '*' ) {
                displayVal = previousVal * displayVal;
            }
            else if ( operator == '/' ) {
                displayVal = previousVal / displayVal;
            }

            previousVal = displayVal;
            operator = '0';
            decimalTracker = '0';
            state = State.RSO;
        }
        else {
            /*The only remaining case if the clear button. We reset everything */
            state = State.RFO;
            displayVal = 0;
            integerPart = 0;
            decimalPart = 0;
            operator = '0';

        }

        

    }

    // getDisplayVal is called by GUI right after GUI called feedChar,
    // to get the String that the GUI should display.
    public String getDisplayVal() {
       
        //String displayString = "Value Received: " + this.displayVal;  //TESTER LINE
        String displayString = "" + this.displayVal;

        // Adjust displayString as necessary, say to show multiple
        // trailing zeroes to the right of the decimal point, or to
        // limit the length of displayString.
        
        return displayString;
    }

    private ButtonCategory category(char c) {
        if ( c >= 48 && c <= 57 ) {
            return ButtonCategory.Digit;
        }
        else if ( c == '.') {
            return ButtonCategory.DecimalPoint;
        }
        else if ( c == '=') {
            return ButtonCategory.EqualsSign;
        }
        else if ( c == '+' || c == '-' || c == '*' || c == '/') {   
            return ButtonCategory.BinaryOp;
        }
        else if ( c == '\u221A') {
            return ButtonCategory.UnaryOp;
        }
        else {
            return ButtonCategory.clear;
        }
    }

   
}
