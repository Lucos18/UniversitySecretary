import Appointment.Appointment;
import Users.Users;


public class Main {
    public static void main(String[] args) {
        Users user = new Users("LUCA", "BUONPANE", "23/01/2004", "CASAPULLA", "M", "LUCABUONPANE3@GMAIL.COM", "CIAO","SSSNNNYYMDDCCCK");

        int id=Appointment.genID();


        Appointment app=new Appointment("10/08/2022","11:30","SSSNNNYYMDDCCCK",Appointment.fixDescLen("ciao"),id);
        id++;
        Appointment.reqApp(app);

    }
}
