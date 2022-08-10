package FrontEnd;
import Users.*;

import javax.swing.*;

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
                Users u =new Users(emailTxtFld.getText(),passwordTxtFld.getPassword());
                if (Users.Login(u))
                {
                    if(u.getRole())
                        //segretary home
                    else
                        //student home
                    frame.dispose();
                }
            }
        });
        signupButton.addActionListener(e -> {
            Signup.main(null);
            frame.dispose();
        });
        forgotPasswordButton.addActionListener(e -> ForgotPsw.main(null));
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
