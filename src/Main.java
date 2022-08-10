import Appointment.Appointment;
import Users.Users;


public class Main {
    public static void main(String[] args) {
        Users user = new Users("LUCA", "BUONPANE", "23/01/2004", "CASAPULLA", "M", "LUCABUONPANE3@GMAIL.COM", "CIAO","SSSNNNYYMDDCCCK");

        String id=Appointment.genID();


        Appointment app=new Appointment("10/08/2022","09:30","SSSNNNYYMDDCCCCK","0h ciao",id);

        Appointment.reqApp(app);

        Appointment.cancApp(app);

    }
}
