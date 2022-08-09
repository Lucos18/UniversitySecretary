package Users;
import java.io.*;
import java.net.InetAddress;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Base64;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import SSN.SSN;
import SendMail.SendMail;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;


public class Users {
    String name;
    String surname;
    String dateB;
    String cityB;
    String sex;
    String email;
    String password;

    String CF = null;
    boolean role = false;
    public Users(String name, String surname, String dateB, String cityB, String sex, String email, String password)
    {
        this.name = name;
        this.surname = surname;
        this.dateB = dateB;
        this.cityB = cityB;
        this.sex = sex;
        this.email = email;
        this.password = password;
    }
    //Function to register a user in the University JSON, with different user information.
    public static boolean RegisterUser(Users user) throws IOException, InterruptedException {
        String CF = SSN.SSNC(user);
        user.setCF(CF);
        //Define the JSON Object, Array and Parser
        JSONObject jobject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONParser jparser = new JSONParser();
        //Reading the "users.json" file and parsing inside the json Array
        try {
            FileReader file = new FileReader("users.json");
            jsonArray = (JSONArray) jparser.parse(file);

        } catch (Exception ex) {
            System.out.println("Generic Error!");
            return false;
        }
        //Insert user email information inside the json object
        jobject.put("Email", user.getEmail());
        jobject.put("CF", user.getCF());
        //The boolean will check if the user exist inside the json file
        boolean userNotExist = true;
        //looping inside the jsonArray, checking if the user exist inside the json file by checking the email
        for (int i = 0; i < jsonArray.size(); i++) {
            if ((((JSONObject) jsonArray.get(i)).get("Email").equals(jobject.get("Email"))) || (((JSONObject) jsonArray.get(i)).get("CF").equals(jobject.get("CF")))) {
                //If the email of user has been found inside the json Array it will set the "userNotExist" variable to false
                userNotExist = false;
            }
        }
        if (userNotExist) {

            //Adding new information of the user inside the json object
            jobject.put("name", user.getName());
            jobject.put("surname", user.getSurname());
            jobject.put("password", user.getPassword());
            //Insert the json object inside the jsonArray
            jsonArray.add(jobject);

            //Write the jsonArray with all the user information inside the "users.json" file
            try {
                FileWriter file = new FileWriter("users.json");
                file.write(jsonArray.toJSONString());
                file.close();
            } catch (Exception ex) {
                System.out.println("Generic Error!");
                return false;
            }
            System.out.println("Registration completed!");
            SendMail.sendMail(user.getEmail(), "Benvenuto, " + user.getName(), "Ti sei registrato alla segreteria universitaria");
            return true;
        } else {
            System.out.println("User already exist!");
            return false;
        }

    }
    public static boolean Login(String email, String password) {
        //Define the JSON Array and Parser
        JSONParser jsonParser = new JSONParser();
        JSONArray userList = new JSONArray();
        try (FileReader reader = new FileReader("users.json")) {
            //Read JSON file "users.json" and paste all the content inside the JSON array
            userList = (JSONArray) jsonParser.parse(reader);
            for (int i = 0; i < userList.size(); i++)
            {
                //Boolean to see if an email and a password has been found inside the JSON array
                boolean emailFound = ((((JSONObject) userList.get(i)).get("Email").equals(email)));
                boolean PasswordFound = ((((JSONObject) userList.get(i)).get("password").equals(password)));
                if (emailFound && PasswordFound)
                {
                    SendMail.sendMail(email,"Nuovo accesso effettuato", "è stato effettuato un nuovo accesso all'account tramite il dispositivo: " + getMachineName());
                    System.out.println("Login effettuato zio pera");
                    return true;
                }
            }
        } catch (IOException | org.json.simple.parser.ParseException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
    public static String getMachineName(){
        String SystemName = "";
        try {
            // get system name
            SystemName = InetAddress.getLocalHost().getHostName();
            // SystemName stores the name of the system
        }
        catch (Exception E) {
            System.err.println(E.getMessage());
        }
        return SystemName;
    }
    public static boolean checkEmailValidation(String email){
        //All the possible combination of emails
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public static String encryptPassword(String password) throws IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        String key = "Bar12345Bar12345";
        Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, aesKey);
        byte[] encrypted = cipher.doFinal(password.getBytes());
        //byte[] decryptedPassword = cipher.doFinal();
        Base64.Encoder encoder = Base64.getEncoder();
        String encryptedPassword = encoder.encodeToString(encrypted);
        System.out.println(encryptedPassword);
        return encryptedPassword;
    }
    /*
    public static String decryptPassword(String encryptedPassword) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        String key = "Bar12345Bar12345";
        Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        Base64.Decoder decoder = Base64.getDecoder();
        cipher.init(Cipher.DECRYPT_MODE, aesKey);
        String decrypted = new String(cipher.doFinal(decoder.decode(encryptedPassword)));
        System.out.println(decrypted);
        return decrypted;
    }
     */
    //All getters and setters of Users
    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public String getdateB() {
        return dateB;
    }
    public String getCityB() {
        return cityB;
    }
    public String getSex() {
        return sex;
    }
    public String getPassword() {
        return password;
    }
    public String getEmail() {
        return email;
    }
    public String getCF(){
        return CF;
    }
    public void setCF(String CF){
        this.CF = CF;
    }
    public void setPassword(String Password) {
        this.password = Password;
    }
    public void setEmail(String Email) {
        this.email = Email;
    }
}


