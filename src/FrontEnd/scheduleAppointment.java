package FrontEnd;

import Users.Users;

import javax.swing.*;

public class scheduleAppointment {
    static JFrame frame;
    private JPanel scheduleAppointmentPanel;
    private JLabel signupLabel;
    private JButton SubmitButton;
    private JTextField dateTxtFld;
    private JTextField descriptionTxtFld;
    private JTextField timeTxtFld;
    private JButton profileButton;
    private JPanel mainPanel;

    public static void init (Users u)
    {
        frame = new JFrame();
        frame.setContentPane(new scheduleAppointment().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 800);
        frame.setVisible(true);
    }
}
