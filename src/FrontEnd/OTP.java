package FrontEnd;

import Users.*;
import SSN.*;
import FrontEnd.*;
import com.sun.jdi.PathSearchingVirtualMachine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class OTP {
    public static boolean otp;
    static JFrame frame;
    private JPanel signupPanel;
    private JTextField otpTxtFld;
    private JButton sendOTPButton;
    private JPanel mainPanel;
    static String mail;

    public OTP() {
        sendOTPButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(otpTxtFld.isValid()) {
                    //chiamata funzione otp
                    frame.dispose();
                }
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


