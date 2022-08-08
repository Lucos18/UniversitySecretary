package SendMail;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class SendMail {
    public static void sendMail(String email) {
        //Setting up username and password for login inside the outlook email
        final String username = "maronnasanta99@outlook.it";
        final String password = "santamaronna99";
        //Creating properties for mail auth
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "outlook.office365.com");
        props.put("mail.smtp.port", "587");
        //Creating a session with the outlook servers
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            //Creating a message to be sent via email
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("maronnasanta99@outlook.it"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("bboh204@gmail.com"));
            message.setSubject("This will be the Subject of the email");
            message.setText("This will be the text");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
