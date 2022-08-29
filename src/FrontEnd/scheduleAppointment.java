package FrontEnd;

import Appointment.Appointment;
import Users.Users;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;

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
    private JComboBox TimeBox;
    static public Users st ;
    public scheduleAppointment(){
        SubmitButton.addActionListener(e -> {

            boolean valid = true;
            if(dateTxtFld.isValid() && descriptionTxtFld.isValid() && TimeBox.isValid())
            {
                if (descriptionTxtFld.getText().length() > 50)
                {
                    JOptionPane.showMessageDialog(null, "Description can't have more than 50 characters.");
                    valid = false;
                }
                if(!(dateTxtFld.getText().contains("-"))){
                    JOptionPane.showMessageDialog(null, "Please, separate the date with '-'");
                    valid = false;
                }
                if (valid){
                    System.out.println(dateTxtFld.getText());
                    Appointment app = new Appointment(dateTxtFld.getText(),TimeBox.getSelectedItem().toString(),st.getCF(),descriptionTxtFld.getText());
                    if(Appointment.reqApp(app)) JOptionPane.showMessageDialog(null, "Appointment created.");
                    else JOptionPane.showMessageDialog(null, "Appointment not created, time already taken. Please choose another one from the list.");
                }
            }
        });

        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    appointment.init(st);
                    frame.dispose();
            }
        });
    }
    public static void init (Users u)
    {
        st = u;
        frame = new JFrame();
        frame.setContentPane(new scheduleAppointment().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 800);
        frame.setVisible(true);
    }
}
