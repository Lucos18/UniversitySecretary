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
                    if(String.valueOf(passwordField1.getPassword()).equals(String.valueOf(passwordField2.getPassword())))
                    {
                        if(Users.newPassword(email, String.valueOf(passwordField2.getPassword())))
                            JOptionPane.showMessageDialog(null, "Password changed!");
                        else
                            JOptionPane.showMessageDialog(null, "Password not changed!");
                    }
                    else
                        JOptionPane.showMessageDialog(null, "Password not changed!");

                } catch (IllegalBlockSizeException | NoSuchPaddingException | BadPaddingException |
                         NoSuchAlgorithmException | InvalidKeyException ex) {throw new RuntimeException(ex);}
                Login.init();
                frame.dispose();
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
