import SSN.SSN;
import Users.Users;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        SSN test = new SSN("DAVIDE", "CARUSO", "11/8/2002", "NAPOLI", "M", "LUCABUONPANE3@GMAIL.COM", "PASSWORD");

        System.out.println(test.getCodiceFiscale());
    }
}
