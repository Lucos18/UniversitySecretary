
import SSN.SSN;
import SendMail.SendMail;
import Users.Users;
import FrontEnd.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) {
        Users user = new Users("LUCA", "BUONPANE", "23/01/2004", "CASAPULLA", "M", "LUCABUONPANE3@GMAIL.COM", "CIAO");
        Login.init();
    }
}
