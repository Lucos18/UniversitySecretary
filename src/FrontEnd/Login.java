package FrontEnd;
import Users.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Users.checkEmailValidation(emailTxtFld.getText()) && passwordTxtFld.isValid())
                    if(Users.Login(emailTxtFld.getText(), String.valueOf(passwordTxtFld.getPassword())))
                    {
                        //open home
                        frame.dispose();
                    }
            }
        });
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Signup.main(null);
                frame.dispose();
            }
        });
        forgotPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ForgotPsw.main(null);
            }
        });
    }

    public static void main(String[] args) {
        frame = new JFrame();
        frame.setContentPane(new Login().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 800);
        frame.setVisible(true);
    }
}
