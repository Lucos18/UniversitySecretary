package FrontEnd;

import Users.*;
import SSN.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Signup {
    static Users u;
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


        signupButton.addActionListener(e -> {
            u.setCF(SSNTxtFld.getText());
            if(emailTxtFld.isValid()&& Users.checkEmailValidation(emailTxtFld.getText()))
                u.setEmail(emailTxtFld.getText());
            if(passwordTxtFld.isValid())
                u.setPassword(String.valueOf(passwordTxtFld.getPassword()));
            try {

                if(Users.RegisterUser(u))
                {
                    OTP.init(emailTxtFld.getText());
                    while(!(OTP.otpI)){}
                    if(OTP.otpC) {
                        //new register
                        Login.init();
                        frame.dispose();
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Entered OTP is not valid!");
                    }
                }
            } catch (IOException | InterruptedException | IllegalBlockSizeException | NoSuchPaddingException |
                     BadPaddingException | NoSuchAlgorithmException | InvalidKeyException ex) {
                throw new RuntimeException(ex);
            }
        });
        loginButton.addActionListener(e -> {
            Login.init();
            frame.dispose();
        });

    }

    public static void init() {
        frame = new JFrame();
        frame.setContentPane(new Signup().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 800);
        frame.setVisible(true);
    }
}
