
import SSN.SSN;
import SendMail.SendMail;
import Users.Users;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {

        //Users.Login("GIANCOMARCIO@GMAIL.COM","CIAO");
        //SendMail.sendMail("lucabuonpane3@gmail.com");
        Users user = new Users("PAOLO", "HOLO", "23/01/2004", "SANTA MARIA CAPUA VETERE", "M", "bboh204@gmail.com", "CIAO");
        //if (Users.checkEmailValidation(user.getEmail())) System.out.println("Valid mail");
        //else System.out.println("Invalid mail");
        Users.RegisterUser(user);
        //Users.encryptPassword(user.getPassword());
        System.out.println(Users.Login(user.getEmail(),user.getPassword()));
        //String CF = SSN.SSNC(user);
        //user.setCF(CF);
        //boolean ciao = Users.RegisterUser(user);
        //user.setCF() = SSN.SSNC(user);
        //SendMail.createOTP(user.getEmail());
    }
}
