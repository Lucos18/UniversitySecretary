package FrontEnd;
import Users.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Login {
    static JFrame frame;
    private JPanel signupPanel;
    private JTextField emailTxtFld;
    private JPasswordField passwordTxtFld;
    private JButton signupButton;
    private JButton loginButton;
    private JPanel mainPanel;
    private JButton forgotPasswordButton;

    public Login() {
        loginButton.addActionListener(e -> {
            if(Users.checkEmailValidation(emailTxtFld.getText()) && passwordTxtFld.isValid())
            {
                Users u =new Users(emailTxtFld.getText(),String.valueOf(passwordTxtFld.getPassword()));
                try {
                    if (Users.Login(u))
                    {
                        if(!u.getRole()) {
                            studentHome.init(u);
                        }
                        else {
                            //student home
                        }
                        frame.dispose();
                    }

                } catch (IllegalBlockSizeException | NoSuchPaddingException | BadPaddingException |
                         NoSuchAlgorithmException | InvalidKeyException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        signupButton.addActionListener(e -> {
            Signup.init();
            frame.dispose();
        });
        forgotPasswordButton.addActionListener(e -> ForgotPsw.init());
    }

    public static void init() {
        frame = new JFrame();
        frame.setContentPane(new Login().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 800);
        frame.setVisible(true);
    }
}
