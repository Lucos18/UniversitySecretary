package SendMail;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
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
            //Add Subject to the email content
            message.setSubject(Subject);
            //Add Text to email content
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
        //Reading the "OTP.json" file and parsing inside the json Array
        try {
            FileReader file = new FileReader("OTP.json");
            jsonArray = (JSONArray) jparser.parse(file);

        } catch (Exception ex) {
            System.out.println("Generic Error!");
        }
        //Will check if the file .json inside the Array is empty, if not will enter in the for
        if (jsonArray.size() != 0)
        {
            for (int i = 0; i < jsonArray.size(); i++) {
                //Will check if the email corresponds to another email inside the array
                if ((((JSONObject) jsonArray.get(i)).get("Email").equals(email))) {
                    //If Email is found inside the "OTP.json" file, it will remove the email object
                    jsonArray.remove(i);
                    break;
                }
            }
        }
        //It will put inside the object the Email and the OTP informations, and then add inside the jsonArray
        jobject.put("Email", email);
        jobject.put("OTP", OTP);
        jsonArray.add(jobject);
        try {
            //Will create a file writer to write the new information inside the OTP.json
            FileWriter file = new FileWriter("OTP.json");
            file.write(jsonArray.toJSONString());
            file.flush();
            file.close();
        } catch (Exception ex) {
            System.out.println("Generic Error!");
        }
    }
    public static Boolean readOTP(String email, String OTP){
        JSONParser jsonParser = new JSONParser();
        JSONArray OTPList = new JSONArray();
        //Read JSON file "OTP.json" and paste all the content inside the JSON array
        try (FileReader reader = new FileReader("OTP.json")) {

            OTPList = (JSONArray) jsonParser.parse(reader);
            for (int i = 0; i < OTPList.size(); i++)
            {
                //Boolean to see if an email and a password has been found inside the JSON array
                boolean emailFound = ((((JSONObject) OTPList.get(i)).get("Email").equals(email)));
                boolean OTPFound = ((((JSONObject) OTPList.get(i)).get("OTP").equals(OTP)));
                if (emailFound && OTPFound)
                {
                    //It will return true if email and OTP have been found
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
