package FrontEnd;
import Users.*;
import SendMail.*;

import javax.swing.*;

public class ForgotPsw {
    static JFrame frame;
    private JPanel signupPanel;
    private JTextField emailTxtFld;
    private JButton SendMButton;
    private JPanel mainPanel;
    static Users u;

    public ForgotPsw() {
        SendMButton.addActionListener(e -> {
            u.setEmail(emailTxtFld.getText());
            if(Users.checkEmailValidation(u.getEmail())) {
                if (SendMail.checkUserEmailExists(u.getEmail()))
                {
                    SendMail.sendMail(u.getEmail(), "noreply", "Change password, insert this OTP: "+SendMail.createOTP(emailTxtFld.getText()));
                    OTP.init(u);
                    frame.dispose();
                }
                else
                    JOptionPane.showMessageDialog(null, "User doesn't exists.");      
            }
        });
    }
    public static void init() {
        u= new Users(null,null);
        u.setName("");
        frame = new JFrame();
        frame.setContentPane(new ForgotPsw().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 800);
        frame.setVisible(true);
    }
}
