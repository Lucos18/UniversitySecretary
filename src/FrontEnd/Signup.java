package FrontEnd;

import Users.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
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
                    SSNTxtFld.setText("CHIAMATA FUNZIONE SSN");
                }
            }
        });


        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
