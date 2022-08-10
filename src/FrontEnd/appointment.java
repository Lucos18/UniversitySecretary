package FrontEnd;

import javax.swing.*;

public class appointment {
    static JFrame frame;
    private JPanel appointmentPanel;
    private JPanel mainPanel;
    private JTable table1;
    private JButton appointmentButton;
    private JButton scheduleAppointmentButton;

    public static void init() {
        frame = new JFrame();
        frame.setContentPane(new appointment().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 800);
        frame.setVisible(true);
    }
}
