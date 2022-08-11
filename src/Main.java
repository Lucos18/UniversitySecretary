
import SSN.SSN;
import SendMail.SendMail;
import Users.Users;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {


        //SendMail.sendMail("lucabuonpane3@gmail.com");
        Users user = new Users("LOLLO", "GIGGINO", "23/01/2004", "SANTA MARIA CAPUA VETERE", "M", "GIANCOMARCIO@GMAIL.COM", "CIAO");
        //Users.Login(user);
        Users.RegisterUser(user);
        Scanner in = new Scanner(System.in);
        System.out.println("scrivi: ");
        String OTP = in.nextLine();
        Users.confirmOtpRegister(user,user.getEmail(),OTP);
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
