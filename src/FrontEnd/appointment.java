package FrontEnd;

import Users.Users;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class appointment {
    static Users st;
    static JFrame frame;
    private JPanel appointmentPanel;
    private JPanel mainPanel;
    private JButton appointmentButton;
    private JButton scheduleAppointmentButton;
    private JList<String> appointmentList;

    public appointment() {
        scheduleAppointmentButton.addActionListener(e -> {
            scheduleAppointment.init(st);
            frame.dispose();
        });
        appointmentButton.addActionListener(e -> {
            studentHome.init(st);
            frame.dispose();
        });


        mainPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
                //appointmentList.setModel();
            }
        });
    }

    public static void init(Users u) {
        frame = new JFrame();
        frame.setContentPane(new appointment().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 800);
        frame.setVisible(true);
        st=u;




    }
}
