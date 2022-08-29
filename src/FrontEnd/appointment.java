package FrontEnd;

import Appointment.Appointment;
import Users.Users;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
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

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
                boolean appointmentFound = false;
                org.json.JSONArray appointmentListJSON = Appointment.printAppName(st.getCF());
                DefaultListModel model = new DefaultListModel();
                String userCF = st.getCF();
                for (int i = 0; i < appointmentListJSON.length(); i++)
                {
                    //Boolean to see if an email has been found inside the JSON array
                    boolean CFFoundUser = ((((org.json.JSONObject) appointmentListJSON.get(i)).get("CF").equals(userCF)));
                    if (CFFoundUser)
                    {
                        appointmentFound = true;
                        model.addElement("Date: " + ((org.json.JSONObject) appointmentListJSON.get(i)).get("date") + "\n");
                        model.addElement("Hour appointment: " + ((org.json.JSONObject) appointmentListJSON.get(i)).get("hour") + "\n");
                        String Description = ("Description: " + ((org.json.JSONObject) appointmentListJSON.get(i)).get("desc") + "\n");
                        Description = Description.replace("*", "");
                        model.addElement(Description);
                        model.addElement("\n");
                    }
                }
                if (!appointmentFound) model.addElement("You didn't create any appointment yet!");
                    appointmentList.setModel(model);

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
