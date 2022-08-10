package FrontEnd;

import SendMail.SendMail;

import javax.swing.*;


public class OTP {
    public static boolean otpC=false;
    public static boolean otpI=false;
    static JFrame frame;
    private JPanel signupPanel;
    private JTextField otpTxtFld;
    private JButton sendOTPButton;
    private JPanel mainPanel;
    static String mail;

    public OTP() {
        sendOTPButton.addActionListener(e -> {
            if(otpTxtFld.isValid()) {
                otpI=true;
                if(SendMail.readOTP(mail,otpTxtFld.getText())) {
                    otpC = true;
                }
                frame.dispose();
            }
        });
    }

    public static void init(String args) {
        mail=args;
        frame = new JFrame();
        frame.setContentPane(new OTP().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 800);
        frame.setVisible(true);
    }
}


