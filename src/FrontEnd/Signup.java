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

public class Signup {
    static Users u;
    static String date;
    private JPanel signupPanel;
    private JLabel signupLabel;
    private JTextField emailTxtFld;
    private JPasswordField passwordTxtFld;
    private JButton signupButton;
    private JTextField nameTxtFld;
    private JTextField birthcTxtFld;
    private JTextField birthdTxtFld;
    private JTextField surnameTxtFld;
    private JTextField SSNTxtFld;
    private JComboBox sexCmbox;
    private JButton loginButton;
    private JPanel mainPanel;
    static JFrame frame;

    private static boolean checkDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        format.setLenient(false);
        try {
            format.parse(date);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public Signup() {

        SSNTxtFld.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(nameTxtFld.isValid() && surnameTxtFld.isValid() && birthcTxtFld.isValid() && birthdTxtFld.isValid() && sexCmbox.isValid()&&checkDate(birthdTxtFld.getText()))
                {
                    u = new Users (nameTxtFld.getText(),surnameTxtFld.getText(),birthdTxtFld.getText(),birthcTxtFld.getText(),sexCmbox.toString(),null,null); //creation and inazialization of the object user
                    try {
                        SSNTxtFld.setText(SSN.SSNC(u));
                    } catch (IOException | InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }

                }
            }
        });


        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                u.setCF(SSNTxtFld.getText());
                if(emailTxtFld.isValid()&& Users.checkEmailValidation(emailTxtFld.getText()))
                    u.setEmail(emailTxtFld.getText());
                if(passwordTxtFld.isValid())
                    u.setPassword(String.valueOf(passwordTxtFld.getPassword()));
                try {
                    OTP.main(emailTxtFld.getText());
                    while(!(OTP.otp)){}
                    if(Users.RegisterUser(u));
                    {
                        Login.main(null);
                        frame.dispose();
                    }
                } catch (IOException | InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login.main(null);
                frame.dispose();
            }
        });

    }

    public static void main(String[] args) {
        frame = new JFrame();
        frame.setContentPane(new Signup().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 800);
        frame.setVisible(true);
    }
}
