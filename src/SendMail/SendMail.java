package SendMail;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.mail.Authenticator;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import javax.mail.*;
import javax.mail.internet.*;

public class SendMail {
    public static void sendMail(String email, String Subject, String Text) {
        //Setting up username and password for login inside the Outlook email
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
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            //Creating a message to be sent via email
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("maronnasanta99@outlook.it"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            message.setSubject(Subject);
            message.setText(Text);
            Transport.send(message);
            System.out.println("Email sent!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    public static String createOTP(String email){
        Random random = new Random();
        //Declaring the possible numbers that can be in the OTP
        String numbers = "0123456789";
        //Declaring the OTP that will contain 6 numbers
        char[] OTP = new char[6];
        for (int i = 0; i < 6; i++)
        {
            // Use of charAt() method : to get character value
            // Use of nextInt() as it is scanning the value as int
            OTP[i] =
                    numbers.charAt(random.nextInt(numbers.length()));
        }
        writeOTP(String.valueOf(OTP), email);
        return String.valueOf(OTP);
    }
    public static void writeOTP(String OTP, String email){
        JSONObject jobject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONParser jparser = new JSONParser();
        //Reading the "users.json" file and parsing inside the json Array
        try {
            FileReader file = new FileReader("OTP.json");
            jsonArray = (JSONArray) jparser.parse(file);

        } catch (Exception ex) {
            System.out.println("Generic Error!");
        }
        //Insert user email information inside the json object
        jobject.put("Email", email);
        jobject.put("OTP", OTP);
        jsonArray.add(jobject);
        //The boolean will check if the user exist inside the json file
        boolean OTPExist = true;
        //looping inside the jsonArray, checking if the user exist inside the json file by checking the email
        for (int i = 0; i < jsonArray.size(); i++) {
            if ((((JSONObject) jsonArray.get(i)).get("Email").equals(jobject.get("Email")))) {
                //If the email of user has been found inside the json Array it will set the "userNotExist" variable to false
                jsonArray.remove(i);
                break;
            }
        }
        try {
            FileWriter file = new FileWriter("OTP.json");
            file.write(jsonArray.toJSONString());
            file.close();
        } catch (Exception ex) {
            System.out.println("Generic Error!");
        }

    }
    public static Boolean readOTP(String email, String OTP){
        JSONParser jsonParser = new JSONParser();
        JSONArray OTPList = new JSONArray();
        try (FileReader reader = new FileReader("OTP.json")) {
            //Read JSON file "users.json" and paste all the content inside the JSON array
            OTPList = (JSONArray) jsonParser.parse(reader);
            for (int i = 0; i < OTPList.size(); i++)
            {
                //Boolean to see if an email and a password has been found inside the JSON array
                boolean emailFound = ((((JSONObject) OTPList.get(i)).get("Email").equals(email)));
                boolean OTPFound = ((((JSONObject) OTPList.get(i)).get("OTP").equals(OTP)));
                if (emailFound && OTPFound)
                {
                    //It will send a mail to the user email with the machine info that did the login.
                    return true;
                }
            }
        } catch (IOException | org.json.simple.parser.ParseException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
