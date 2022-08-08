package Users;
import java.io.*;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Users {
    String name;
    String surname;
    String dateB;
    String cityB;
    String sex;
    String email;
    String password;
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
    public Users(String name, String surname, String email, String password, Boolean role)
    {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.role = role;
    }
    //Function to register a user in the University JSON, with different user information.
    public static void RegisterUser(Users user) {
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
        }
        //Insert user email information inside the json object
        jobject.put("Email", user.getEmail());
        //The boolean will check if the user exist inside the json file
        boolean userNotExist = true;
        //looping inside the jsonArray, checking if the user exist inside the json file by checking the email
        for (int i = 0; i < jsonArray.size(); i++) {
            if ((((JSONObject) jsonArray.get(i)).get("Email").equals(jobject.get("Email")))) {
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
            }
            System.out.println("Registration completed!");
        } else {
            System.out.println("User already exist!");
        }

    }
    public static void Login(String email, String password) {
        //Define the JSON Array and Parser
        JSONParser jsonParser = new JSONParser();
        JSONArray userList = new JSONArray();
        try (FileReader reader = new FileReader("users.json")) {
            //Read JSON file "users.json" and paste all the content inside the JSON array
            userList = (JSONArray) jsonParser.parse(reader);
            for (int i = 0; i < userList.size(); i++)
            {
                //Boolean to see if an email and a password has been found inside the json array
                boolean emailFound = ((((JSONObject) userList.get(i)).get("Email").equals(email)));
                boolean PasswordFound = ((((JSONObject) userList.get(i)).get("password").equals(password)));
                if (emailFound && PasswordFound)
                {
                    //add there something to do when login is passed
                    System.out.println("Login effettuato zio pera");
                    break;
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
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
}


