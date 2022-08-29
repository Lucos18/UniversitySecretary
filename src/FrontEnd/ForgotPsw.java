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
            //Check if the email has the right syntax
            if(Users.checkEmailValidation(u.getEmail())) {
                //Check if the Email exists inside the "users.json" file
                if (SendMail.checkUserEmailExists(u.getEmail()))
                {
                    SendMail.sendMail(u.getEmail(), "noreply", "To change the password on the University Secretary, please insert this OTP: "+SendMail.createOTP(emailTxtFld.getText()));
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
