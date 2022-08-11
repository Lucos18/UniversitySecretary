package FrontEnd;

import Users.Users;

import javax.swing.*;
import java.awt.event.*;

public class studentHome {
    static JFrame frame;
    static public Users st ;
    private JPanel homePanel;
    private JLabel signupLabel;
    private JButton appointmentButton;
    private JButton logoutButton;
    private JPanel mainPanel;
    private JLabel nameLabel;
    private JLabel surnameLabel;
    private JLabel birthCLabel;
    private JLabel birthDLabel;
    private JLabel sexLabel;
    private JLabel ssnLabel;
    private JLabel emaiLabel;

    public studentHome() {



        logoutButton.addActionListener(e -> {
            Login.init();
            frame.dispose();
        });
        appointmentButton.addActionListener(e -> {
            appointment.init(st);//passa la mail
            frame.dispose();
        });

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
                //funzione che restituisce oggetto utente con tutte le info
                nameLabel.setText(st.getName());
                surnameLabel.setText(st.getSurname());
                birthCLabel.setText(st.getCityB());
                birthDLabel.setText(st.getdateB());
                sexLabel.setText(st.getSex());
                ssnLabel.setText(st.getCF());
                emaiLabel.setText(st.getEmail());
            }
        });
    }

    public static void init(Users u) {
        st=u;
        frame = new JFrame();
        frame.setContentPane(new studentHome().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 800);
        frame.setVisible(true);


    }
}
