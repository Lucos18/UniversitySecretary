package Users;
import SSN.SSN;
import SendMail.SendMail;

import java.io.*;

import java.net.InetAddress;

import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
public class Users {
    String name;
    String surname;
    String dateB;
    String cityB;
    String sex;
    String email;
    String password;
<<<<<<< HEAD
    String CF;
=======

    String CF = null;
>>>>>>> Test-Luca-Raffaele
    boolean role = false;
    public Users(String name, String surname, String dateB, String cityB, String sex, String email, String password, String CF)
    {
        this.name = name;
        this.surname = surname;
        this.dateB = dateB;
        this.cityB = cityB;
        this.sex = sex;
        this.email = email;
        this.password = password;
        this.CF=CF;
    }
<<<<<<< HEAD
    public Users(String name, String surname, String email, String password, String CF, Boolean role)
=======
    public Users(String email, String password)
>>>>>>> Test-Luca-Raffaele
    {
        this.email = email;
        this.password = password;
<<<<<<< HEAD
        this.CF=CF;
        this.role = role;
=======
    }
    //Function to register a user in the University JSON, with different user information.
    public static boolean RegisterUser(Users user) throws IOException, InterruptedException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        String CF = SSN.SSNC(user);
        user.setCF(CF);
        //Define the JSON Object, Array and Parser
        JSONObject jobject = new JSONObject();
        JSONArray jsonArray = Users.readFile("users.json");
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
            //Get encrypted password to add inside the database user
            String encryptedPassword = encryptPassword(user.getPassword());
            user.setPassword(encryptedPassword);
            //It will send the email to the registered user
            String OTPgenerated = SendMail.createOTP(user.getEmail());
            SendMail.sendMail(user.getEmail(), "Il tuo codice OTP ", "Per completare la registrazione inserisci il seguente OTP: " + OTPgenerated);
            return true;
        } else {
            System.out.println("User already exist!");
            return false;
        }

    }
    public static boolean Login(Users user) throws IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        //Get encrypted password to read inside the database user
        String encryptedPassword = encryptPassword(user.password);
        //Define the JSON Array by reading through the method
        JSONArray userList = Users.readFile("users.json");
        for (int i = 0; i < userList.size(); i++)
        {
            //Boolean to see if an email and a password has been found inside the JSON array
            boolean emailFoundUser = ((((JSONObject) userList.get(i)).get("Email").equals(user.email)));
            boolean passwordFoundUser = ((((JSONObject) userList.get(i)).get("password").equals(encryptedPassword)));
            if (emailFoundUser && passwordFoundUser)
            {
                //It will send a mail to the user email with the machine info that did the login.
                SendMail.sendMail(user.getEmail(), "Nuovo tentativo di accesso", "è stato effettuato un nuovo accesso all'account tramite il dispositivo: " + getMachineName() + "\nSe sei tu, inserisci il seguente OTP per completare l'accesso: " + SendMail.createOTP(user.email));
                return true;
            }
        }
        return false;
    }

    public static Users getUserInfo(Users user)
    {
        JSONArray userList = Users.readFile("users.json");
        for (int i = 0; i < userList.size(); i++)
        {
            //Boolean to see if an email and a password has been found inside the JSON array
            boolean emailFoundUser = ((((JSONObject) userList.get(i)).get("Email").equals(user.email)));
            if (emailFoundUser)
            {
                //Will set all the information that we need for the home student of already existing user object
                user.setCF((String) ((JSONObject) userList.get(i)).get("CF"));
                user.setSex((String) ((JSONObject) userList.get(i)).get("sex"));
                user.setCityB((String) ((JSONObject) userList.get(i)).get("cityBirth"));
                user.setDateB((String) ((JSONObject) userList.get(i)).get("dateBirth"));
                user.setSurname((String) ((JSONObject) userList.get(i)).get("surname"));
                user.setName((String) ((JSONObject) userList.get(i)).get("name"));
                return user;
            }
        }
        return user;
    }
    public static String getMachineName(){
        String SystemName = "";
        try {
            //It will get the system name and stores it inside the variable "System name"
            SystemName = InetAddress.getLocalHost().getHostName();
        }
        catch (Exception E) {
            System.err.println(E.getMessage());
        }
        return SystemName;
    }
    //Function that check if an Email is valid in term of syntax
    public static boolean checkEmailValidation(String email){
        //All the possible combination of emails
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    //Function that ecrypts user password
    public static String encryptPassword(String password) throws IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        //Declaration of the key that will be used for encryption (Must be a multiple of 16 char)
        String key = "Bar12345Bar12345";
        //Specify that will use AES encryption
        Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, aesKey);
        byte[] encrypted = cipher.doFinal(password.getBytes());
        Base64.Encoder encoder = Base64.getEncoder();
        //Will convert the encoded String to a normal String of the encrypted password
        return encoder.encodeToString(encrypted);
    }
    //The function will read the OTP by the user and if the method returns true then add all the information inside the user.json file
    public static Boolean confirmOtpRegister(Users user, String email, String OTP){
        //If exists email and OTP inside the "OTP.json" file then it will create the JSON Object with user information
        if (SendMail.readOTP(email,OTP))
        {
            //Declaring JSON Object, Array and Parser
            JSONObject jobject = new JSONObject();
            //Reading the file to add inside the json Array
            JSONArray jsonArray = Users.readFile("users.json");
            //Add different information of the user inside the object
            jobject.put("Email", user.getEmail());
            jobject.put("CF", user.getCF());
            jobject.put("name", user.getName());
            jobject.put("surname", user.getSurname());
            jobject.put("password", user.getPassword());
            jobject.put("cityBirth", user.getCityB());
            jobject.put("dateBirth", user.getdateB());
            jobject.put("sex", user.getSex());
            jobject.put("role", user.getRole());
            //Insert the object inside the jsonArray for writing
            jsonArray.add(jobject);
            //It will write the jsonArray inside the users.json file, so it can be considered as a registered user
            Users.writeFile(jsonArray, "users.json");
            System.out.println("Registration completed!");
            return true;
        }
        return false;
    }
    public static JSONArray readFile(String directory){
        JSONParser jparser = new JSONParser();
        JSONArray jsonArray = new JSONArray();
        try {
            FileReader file = new FileReader(directory);
            jsonArray = (JSONArray) jparser.parse(file);

        } catch (Exception ex) {
            System.out.println("Generic Error!");
        }
        return jsonArray;
    }
    public static void writeFile(JSONArray jsonArray, String directory){
        try {
            //Will create a file writer to write the new information inside the directory parameter
            FileWriter file = new FileWriter(directory);
            file.write(jsonArray.toJSONString());
            file.flush();
            file.close();
        } catch (Exception ex) {
            System.out.println("Generic Error!");
        }
    }
    public static boolean newPassword(String email, String newPassword) throws IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        JSONObject jobject;
        JSONArray userList = Users.readFile("users.json");
        for (int i = 0; i < userList.size(); i++)
        {
            //Boolean to see if an email has been found inside the JSON array
            boolean emailFoundUser = ((((JSONObject) userList.get(i)).get("Email").equals(email)));
            if (emailFoundUser)
            {
                //Will get the User that we need to change the password
                jobject = (JSONObject) userList.get(i);
                //Put the new password inside the parameter "password" of the user
                jobject.put("password", Users.encryptPassword(newPassword));
                //Will remove the User from the "users.json" file
                userList.remove(i);
                //Add back the User to the file with the new password
                userList.add(jobject);
                Users.writeFile(userList,"users.json");
                return true;
            }
        }
        return false;
    }
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
    public boolean getRole() {return this.role;}
    public void setCF(String CF){
        this.CF = CF;
    }
    public void setPassword(String Password) {
        this.password = Password;
    }
    public void setEmail(String Email) {
        this.email = Email;
    }
    public void setName(String Name) {
        this.name = Name;
    }
    public void setSurname(String Surname) {
        this.surname = Surname;
    }
    public void setCityB(String CityB) {
        this.cityB = CityB;
    }
    public void setDateB(String dateB) {
        this.dateB = dateB;
    }
    public void setSex(String sex) {
        this.sex = sex;
>>>>>>> Test-Luca-Raffaele
    }
}


