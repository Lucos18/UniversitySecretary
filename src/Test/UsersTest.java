package Test;

import Users.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

class UsersTest {
    Users user1 = new Users("LUCA","BUONPANE","23/01/2004","SANTA MARIA CAPUA VETERE", "M", "LUCABUONPANE3@GMAIL.COM","SIUM");
    Users user2 = new Users("DAVIDE PASQUALE","GALLO","19/12/2002","NAPOLI", "M", "LUCABUONPANE3@GMAIL.COM","SIUM");
    @BeforeEach
    void setUp(){

    }
    @Test
    @DisplayName("Registrazione user1")
    void registerUser() throws IOException, InterruptedException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        assertEquals(true, Users.RegisterUser(user1));
    }

    @Test
    @DisplayName("Login user1-user2")
    void loginUser() throws IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        assertEquals(true, Users.Login(user1.getEmail(),user1.getPassword()));
        assertEquals(false, Users.Login(user2.getEmail(),user2.getPassword()));
    }
    @Test
    @DisplayName("Check different emails")
    void checkEmail(){
        assertEquals(true, Users.checkEmailValidation("LUCABUONPANE3@GMAIL.COM"));
        assertEquals(true, Users.checkEmailValidation("BBOH204@GMAIL.COM"));
        assertEquals(false, Users.checkEmailValidation("GIANCOMARCIO@GMILCOM"));
        assertEquals(false, Users.checkEmailValidation("LOCOGMAIL.COM"));
    }
}