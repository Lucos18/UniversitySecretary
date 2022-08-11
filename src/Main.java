
import SendMail.SendMail;
import Users.Users;
public class Main {
    public static void main(String[] args){
        Users user = new Users("NOTPEZZO", "gigiogigig", "23/01/2004", "SANTA MARIA CAPUA VETERE", "M", "gigigigig@GMAIL.COM", "CIAO");
        SendMail.writeOTP("123456","giggino@gmail.com");
    }
}
