//MailLayout.java

/**
 * This program will create a Graphical User Interface for simulating the sending of an email.
 * It will print the main content of the email into a text file "outbox.txt"
 * Available in a dropdown list, are a set of receivers
 * 
 * @author Tresor Habib Nahouta
 * @version Last Modified: April 10th, 2024
 * 
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.concurrent.Flow;

public class MailLayout {
    /**
     * The main method only creates an instance of the MailSender frame class, which will handle all the processing
     */
    public static void main (String [] args) {
        MailSender mSender = new MailSender();
    }
}

/**
 * The custom class of the MailLayout, extending the JFrame class
 */
class MailSender extends JFrame {
    /*Creating all the necessary components */
    Font font = new Font ("Helvetica", Font.BOLD, 10);
    Dimension fieldSize = new Dimension (400, 25);

    JButton send = new JButton("Send");

    JLabel to = new JLabel("To: ");
    JTextField toField = new JTextField("foobar@foo.com");

    JLabel cc = new JLabel("Cc: ");
    JTextField ccField = new JTextField("Mark_Zuckerberg@facebook.com");

    JLabel bcc = new JLabel("Bcc: ");
    JTextField bccField = new JTextField("cscie10@fas.harvard.edu");

    JLabel subject = new JLabel("Subject: ");
    JTextField subjectField = new JTextField("New message");

    JLabel from = new JLabel("From: ");
    JComboBox<String> fromList = new JComboBox<String>();

    JTextArea email = new JTextArea();


    /*Constructor of the class */
    public MailSender () {
        setTitle("New Message");
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        send.setPreferredSize(new Dimension(40, 30));

        MessageSender m1 = new MessageSender();
        send.addActionListener(m1);
    
        /*Formatting the "to" Label and TextField*/
        to.setFont(font);
        to.setPreferredSize(new Dimension(50, 25));


        toField.setFont(font);
        toField.setPreferredSize(new Dimension(200, 15));
        JPanel toPanel = new JPanel();
        toPanel.setLayout(new BoxLayout(toPanel, BoxLayout.X_AXIS));
        toPanel.setPreferredSize(new Dimension(100, 25));
        toPanel.setMaximumSize(new Dimension(1920, fieldSize.height));
        toPanel.add(to);
        toPanel.add(toField);
    
    
        /*Formatting the "cc" Label and TextField*/
        cc.setFont(font);
        cc.setPreferredSize(new Dimension(50, 25));
        ccField.setFont(font);
        JPanel ccPanel = new JPanel();
        ccPanel.setLayout(new BoxLayout(ccPanel, BoxLayout.X_AXIS));
        ccPanel.setPreferredSize(new Dimension(100, 25));
        ccPanel.setMaximumSize(new Dimension(1920, fieldSize.height));
        ccPanel.add(cc);
        ccPanel.add(ccField);
    
    
        /*Formatting the "bcc" Label and TextField*/
        bcc.setFont(font);
        bcc.setPreferredSize(new Dimension(50, 25));
        bccField.setFont(font);
        JPanel bccPanel = new JPanel();
        bccPanel.setLayout(new BoxLayout(bccPanel, BoxLayout.X_AXIS));
        bccPanel.setPreferredSize(new Dimension(100, 25));
        bccPanel.setMaximumSize(new Dimension(1920, fieldSize.height));
        bccPanel.add(bcc);
        bccPanel.add(bccField);
    
    
        /*Formatting the "Subject" Label and TextField and setting up the listeners*/
        subject.setFont(font);
        subject.setPreferredSize(new Dimension(50, 25));
        subjectField.setFont(font);
        JPanel subjectPanel = new JPanel();
        subjectPanel.setLayout(new BoxLayout(subjectPanel, BoxLayout.X_AXIS));
        subjectPanel.setPreferredSize(new Dimension(100, 25));
        subjectPanel.setMaximumSize(new Dimension(1920, fieldSize.height));
        subjectPanel.add(subject);
        subjectPanel.add(subjectField);
        SubjectListener s1 = new SubjectListener();
        subjectField.addActionListener(s1);
    
        /*Formatting the "from" Label and TextField*/
        from.setFont(font);
        from.setPreferredSize(new Dimension(50, 25));

        fromList.setFont(font);
        fromList.addItem("Tyler_Winklevoss@facebook.com");
        fromList.addItem("Eduardo_Severin@facebook.com");
        fromList.addItem("Peter_Griffin@facebook.com");
        JPanel fromPanel = new JPanel();
        fromPanel.setPreferredSize(new Dimension(100, 25));
        fromPanel.setMaximumSize(new Dimension(1920, fieldSize.height));
        fromPanel.setLayout(new BoxLayout(fromPanel, BoxLayout.X_AXIS));
        fromPanel.add(from);
        fromPanel.add(fromList);
    

        email.setFont(font);
        email.setText("blah, blah, blah...\nAnd yes, BLAH!\nBest wishes,\nHHL");

    
        this.add(send);
        this.add(toPanel);
        this.add(ccPanel);
        this.add(bccPanel);
        this.add(subjectPanel);
        this.add(fromPanel);
        this.add(email);
    
        this.setSize(400,400);
        this.setVisible(true);
        this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
    }

    /**
     * This class is an ActionListener that updates the Frame title with the subject of the email
     */
    class SubjectListener implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            if (e.getSource() == subjectField) {
                setSubjectTitle(subjectField.getText());
            }
        }
    }


    /**
     * This class is an ActionLister that will send the message after a click on the send button will be registered
     * Il will print the content into the file and reset the fields
     */
    class MessageSender implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            if (e.getSource() == send) {
                //Code for creating and exporting as a file

                try {
                    printOutbox(email.getText());
                }
                catch (IOException ex) {
                    System.out.println("Couldn't print to the filee :( ");
                }
                
                email.setText("");
                toField.setText("");
                ccField.setText("");
                bccField.setText("");
                subjectField.setText("New Message");
                setSubjectTitle(getTitle());

            }
        }
    }


    /**
     * This method will update the title of the frame
     * @param title The new title of the frame (derived from the text in the Subject field)
     */
    void setSubjectTitle (String title) {
        this.setTitle(title);
    }

    /**
     * This method will print the context of the email into a file
     * @param s
     * @throws IOException
     */
    void printOutbox (String s) throws IOException{
        try {
            PrintWriter outFile = new PrintWriter("outbox.txt");
            outFile.print(s);
            outFile.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    } 

     
}

 


