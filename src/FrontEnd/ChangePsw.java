package FrontEnd;

import Users.Users;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class ChangePsw {
    static JFrame frame;
    private JPanel ChgPswPanel;
    private JTextField emailTxtFld;
    private JPasswordField passwordField2;
    private JButton changePswButton;
    private JPanel mainPanel;
    private JPasswordField passwordField1;
    private static String email;

    public ChangePsw() {
        changePswButton.addActionListener(e -> {
            if(passwordField1.isValid()&&passwordField2.isValid())
            {
                try {
                    //Check if the first password in the text field equals to the second password in the text field
                    if(String.valueOf(passwordField1.getPassword()).equals(String.valueOf(passwordField2.getPassword())))
                    {
                        //Change the password inside the file "users.json"
                        Users.newPassword(email, String.valueOf(passwordField2.getPassword()));
                        JOptionPane.showMessageDialog(null, "Password changed!");
                        //Get the user to the Login page
                        Login.init();
                        frame.dispose();
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Password not changed! Insert twice the same password!");
                        ChangePsw.init(email);
                        frame.dispose();
                    }
                } catch (IllegalBlockSizeException | NoSuchPaddingException | BadPaddingException |
                         NoSuchAlgorithmException | InvalidKeyException ex) {throw new RuntimeException(ex);}
            }
        });
    }

    public static void init( String mail) {
        email=mail;
        frame = new JFrame();
        frame.setContentPane(new ChangePsw().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 800);
        frame.setVisible(true);
    }
}
