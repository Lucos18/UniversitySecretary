package Test;

import Users.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class UsersTest {
    Users user1 = new Users("LUCA","BUONPANE","23/01/2004","SANTA MARIA CAPUA VETERE", "M", "LUCABUONPANE3@GMAIL.COM","SIUM");
    Users user2 = new Users("DAVIDE PASQUALE","GALLO","19/12/2002","NAPOLI", "M", "LUCABUONPANE3@GMAIL.COM","SIUM");
    @BeforeEach

    void setUp(){
        Users user1 = new Users("LUCA","BUONPANE","23/01/2004","SANTA MARIA CAPUA VETERE", "M", "LUCABUONPANE3@GMAIL.COM","SIUM");
        Users user2 = new Users("DAVIDE PASQUALE","GALLO","19/12/2002","NAPOLI", "M", "LUCABUONPANE3@GMAIL.COM","SIUM");
    }
    @Test
    @DisplayName("Registrazione user1")
    void registerUser() throws IOException, InterruptedException {
        assertEquals(true, Users.RegisterUser(user1));
    }

    @Test
    void login() {
    }
}