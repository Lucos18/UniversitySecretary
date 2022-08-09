import SendMail.SendMail;
import Users.Users;

public class Main {
    public static void main(String[] args) {

        //Users.Login("GIANCOMARCIO@GMAIL.COM","CIAO");
        //SendMail.sendMail("lucabuonpane3@gmail.com");
        Users user = new Users("LUCA", "BUONPANE", "23/01/2004", "CASAPULLA", "M", "LUIGIIORIO30@GMAIL.COM", "CIAO");
        Users.Login(user.getEmail(),user.getPassword());
        Users.RegisterUser(user);
    }
}
