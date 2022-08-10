
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


        //SendMail.sendMail("lucabuonpane3@gmail.com");
        Users user = new Users("PAOLO", "HOLO", "23/01/2004", "SANTA MARIA CAPUA VETERE", "M", "bboh204@gmail.com", "CIAO");
        //Users.Login(user);
        //Users.RegisterUser(user);
        //if (Users.checkEmailValidation(user.getEmail())) System.out.println("Valid mail");
        //else System.out.println("Invalid mail");
        //Users.RegisterUser(user);
        //SendMail.createOTP(user.getEmail());
        //Users.encryptPassword(user.getPassword());
        //System.out.println(Users.Login(user));
        //String CF = SSN.SSNC(user);
        //user.setCF(CF);
        //boolean ciao = Users.RegisterUser(user);
        //user.setCF() = SSN.SSNC(user);
        //SendMail.createOTP(user.getEmail());
    }
}
