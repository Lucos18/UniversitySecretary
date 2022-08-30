package FrontEnd;

import SendMail.SendMail;
import Users.*;

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
    static boolean k;
    static Users st;

    public OTP() {
        sendOTPButton.addActionListener(e -> {
            if(otpTxtFld.isValid()) {
                otpI = true;
                if (st.getName().isEmpty())
                {
                    if(SendMail.readOTP(mail,otpTxtFld.getText())) {
                        otpC = true;
                        ChangePsw.init(mail); 
                        JOptionPane.showMessageDialog(null, "Entered OTP is valid!");
                        frame.dispose();
                    }
                }
                else
                {
                    if(Users.confirmOtpRegister(st,mail,otpTxtFld.getText()))
                    {
                        otpC = true;
                        Login.init();
                        JOptionPane.showMessageDialog(null, "Entered OTP is valid!");
                        frame.dispose();
                    }
                }

            }
            if (!otpC) JOptionPane.showMessageDialog(null, "Entered OTP is not valid!");
        });
    }
    public static void init(Users u) {
        mail=u.getEmail();
        st=u;
        frame = new JFrame();
        frame.setContentPane(new OTP().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 800);
        frame.setVisible(true);
    }
}


