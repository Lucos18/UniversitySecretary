import SSN.SSN;
import Users.Users;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        SSN test = new SSN("LUIGI", "IORIO", "01/06/2002", "FRATTAMAGGIORE", "M", "LUCABUONPANE3@GMAIL.COM", "PASSWORD");
        // CHECK DATA BITH FORMAT, USEFUL FOR CALCULATE SSN: dd/mm/aaaa
        System.out.println(test.getCodiceFiscale());
    }
}
