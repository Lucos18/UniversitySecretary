package FrontEnd;

import javax.swing.*;

public class ChangePsw {
    static JFrame frame;
    private JPanel ChgPswPanel;
    private JTextField emailTxtFld;
    private JPasswordField passwordTxtFld;
    private JButton changePswButton;
    private JPanel mainPanel;

    public ChangePsw() {
        changePswButton.addActionListener(e -> {
            if(passwordTxtFld.isValid()&&emailTxtFld.isValid())
            {
                //funzione cambia password
                frame.dispose();
            }
        });
    }

    public static void init() {
        frame = new JFrame();
        frame.setContentPane(new ChangePsw().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 800);
        frame.setVisible(true);
    }
}
