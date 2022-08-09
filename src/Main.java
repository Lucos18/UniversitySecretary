
import SSN.SSN;
import SendMail.SendMail;
import Users.Users;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        //Users.Login("GIANCOMARCIO@GMAIL.COM","CIAO");
        //SendMail.sendMail("lucabuonpane3@gmail.com");
        Users user = new Users("LADSUDASDCA", "GIGGIWNO", "23/01/2004", "SANTA MARIA CAPUA VETERE", "M", "WAKANDA@GMAIL.COM", "CIAO");

        //Users.Login(user.getEmail(),user.getPassword());

        String CF = SSN.SSNC(user);
        user.setCF(CF);
        Users.RegisterUser(user);

        //user.setCF() = SSN.SSNC(user);
    }
}
