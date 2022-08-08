import Users.Users;

public class Main {
    public static void main(String[] args) {

        //Users.Login();
        Users user = new Users("LUCA", "BUONPANE", "23/01/2004", "CASAPULLA", "M", "LUCABUONPANE3@GMAIL.COM", "CIAO");
        Users.RegisterUser(user);
    }
}
