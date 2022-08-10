package FrontEnd;

import javax.swing.*;


public class OTP {
    public static boolean otp;
    static JFrame frame;
    private JPanel signupPanel;
    private JTextField otpTxtFld;
    private JButton sendOTPButton;
    private JPanel mainPanel;
    static String mail;

    public OTP() {
        sendOTPButton.addActionListener(e -> {
            if(otpTxtFld.isValid()) {
                //chiamata funzione otp
                frame.dispose();
            }
        });
    }

    public static void main(String args) {
        mail=args;
        frame = new JFrame();
        frame.setContentPane(new OTP().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 800);
        frame.setVisible(true);
    }
}


