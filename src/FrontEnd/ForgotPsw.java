package FrontEnd;
import Users.*;
import SendMail.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ForgotPsw {
    static JFrame frame;
    private JPanel signupPanel;
    private JTextField emailTxtFld;
    private JButton SendMButton;
    private JPanel mainPanel;

    public ForgotPsw() {
        SendMButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Users.checkEmailValidation(emailTxtFld.getText())) {
                    SendMail.sendMail(emailTxtFld.getText(), "ciao", "ciao");
                    OTP.main(emailTxtFld.getText());
                    while(!(OTP.otp)){}
                    ChangePsw.main(null);
                    frame.dispose();
                }
            }
        });
    }

    public static void main(String[] args) {
        frame = new JFrame();
        frame.setContentPane(new ForgotPsw().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 800);
        frame.setVisible(true);
    }
}
