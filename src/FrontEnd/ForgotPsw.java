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
            if(Users.checkEmailValidation(emailTxtFld.getText())) {
                if (SendMail.checkUserEmailExists(emailTxtFld.getText())) {
                    SendMail.createOTP(emailTxtFld.getText());
                    OTP.init(emailTxtFld.getText(),false, u);
                    while(!(OTP.otpI)){}
                    if(OTP.otpC)
                    {

                        ChangePsw.init();
                        frame.dispose();
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Entered OTP is not valid!");
                    }
                } else JOptionPane.showMessageDialog(null, "User doesn't exists.");
            }
        });
    }
    public static void init() {
        u= new Users(null,null);
        frame = new JFrame();
        frame.setContentPane(new ForgotPsw().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 800);
        frame.setVisible(true);
    }
}
