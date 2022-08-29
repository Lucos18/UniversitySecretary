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
    private JPasswordField passwordTxtFld;
    private JButton changePswButton;
    private JPanel mainPanel;

    public ChangePsw() {
        changePswButton.addActionListener(e -> {
            if(passwordTxtFld.isValid()&&emailTxtFld.isValid())
            {
                try {
                    if(Users.newPassword(emailTxtFld.getText(), String.valueOf(passwordTxtFld.getPassword()))) JOptionPane.showMessageDialog(null, "Password changed!");
                    else JOptionPane.showMessageDialog(null, "Password not changed!");
                } catch (IllegalBlockSizeException | NoSuchPaddingException | BadPaddingException |
                         NoSuchAlgorithmException | InvalidKeyException ex) {throw new RuntimeException(ex);}
                frame.dispose();
            }
        });
    }

    public static void init() {
        frame = new JFrame();
        frame.setContentPane(new ChangePsw().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 800);
        frame.setVisible(true);
    }
}
