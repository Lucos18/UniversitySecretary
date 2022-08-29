package Appointment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Appointment {

        /*Const*/
        static final int maxApp =12;        //max appointment per day
        static final int dimApp=136;        //dimension of each appointment     150 if id

        /*Attributes of Appointment */
        String date;    // dd/mm/yyyy
        String hour;    // hh:mm
        String CF;
        String descr;   //short description max 50 ch
        String id;      //max 6 char
        boolean status=true; //if true appointment was approved
        public Appointment(String date, String hour, String CF, String descr)
        {
            this.date=fixStrLen(date,10);
            this.hour=fixStrLen(hour,5);
            this.CF=fixStrLen(CF,16);
            this.descr=fixStrLen(descr,50);
            //this.id=fixStrLen(genID(),6);
        }

        public boolean getStatus(){return status;}

        public void setStatus(boolean newStatus){status=newStatus;}

        /*Methods*/

        /*public static String  genID()
        {
            String ID;
            ID="1";
            return ID;
        }*/

        static short hourToN(String hour)//done
                /*Method for N of appointment
                    return the cardinal number of the appointment based on hour
                    */
        {
            String[] hourCheck={"09:00","09:30","10:00","10:30","11:00","11:30","12:00","12:30","14:00","14:30","15:00","15:30",};
            short n=0;
            for(;n<11;n++)
                if(hour.equalsIgnoreCase(hourCheck[n]))
                    break;
            return n;
        }

        public static String fixStrLen(String Ostr, int len)//done
                /*Method for inserting "void" character in string appointment field
                    */
        {
            String str= Ostr;
            char[] cstr=new char[len];
            char ch='*';

            for (int i = 0; i < str.length(); i++) {
                cstr[i] = str.charAt(i);
            }

            str = new String(cstr);
            str = str.replace('\0', ch);
            return str;
        }

        static JSONObject voidingReq(String dateToAdd)
                                      /*Method for creation of void appointment */
        {
            JSONObject voidReq = new JSONObject();
            Appointment voidApp=new Appointment("*","*","*","*");
            voidApp.setStatus(false);
            voidReq.put("CF", voidApp.CF);
            voidReq.put("date", dateToAdd);
            voidReq.put("hour",voidApp.hour);
            voidReq.put("desc", voidApp.descr);
            voidReq.put("status",voidApp.status);
            return voidReq;
        }

        static long srcAppCf(String CF,long posk)//done
                                /*Method for appointment search by cf
                                    return the pos of the block of appointment of specified date
                                    */
        {
            long pos = -1;

            try {
                String line;
                boolean flag = true;
                RandomAccessFile file = new RandomAccessFile("src\\Appointment\\app.json", "rw");
                file.seek(posk);
                while (((line = file.readLine()) != null) && flag)        /*Search in index file position of requested cf*/
                {
                    JSONObject temp = new JSONObject(line);
                    String cf = temp.getString("CF");
                    if (cf.equalsIgnoreCase(CF))                /*if found, set pos value and exit cicle*/ {
                        pos =  file.getFilePointer()-dimApp;
                        flag = false;
                    }
                }
                file.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return pos;
        }

        public static JSONArray printAppName(String CF)//done
                            /*Method print all appointment whith specific name
                                return a jason array with all appointment with that name
                                */
        {
            String line;
            long pos=0;
            JSONArray appDate= new JSONArray();
            try {
                RandomAccessFile file= new RandomAccessFile("src\\Appointment\\app.json","rw");
                while ((srcAppCf(CF,pos))!=-1)
                {
                    line = file.readLine();
                    JSONObject temp = new JSONObject(line);
                    appDate.put(temp);
                    pos =  file.getFilePointer();
                }
                file.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return appDate;
        }

        public static JSONArray printAppDate(String date)//done
                 /*Method print all appointment in a specified date
                    return a jason array with all appointment for that date
                    */
        {
            JSONArray appDate= new JSONArray();
            long pos=srcAppDate(date);
            try {
                RandomAccessFile file= new RandomAccessFile("src\\Appointment\\app.json","rw");
                file.seek(pos);
                for(int i=0;i<maxApp;i++)
                {
                    String line=file.readLine();
                    JSONObject temp = new JSONObject(line);
                    appDate.put(temp);
                }
                file.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return appDate;
        }

        private static Long srcAppDate(String dateS)//done
                /*Method for appointment search by date
                    return the pos of the block of appointment of specified date
                    */
        {
            long pos = -1;

            try {
                String line;
                boolean flag=true;
                RandomAccessFile file= new RandomAccessFile("src\\Appointment\\IndexApp.json","r");
                file.seek(0);
                while(((line=file.readLine())!=null) && flag)        /*Search in index file position of requested data*/
                {
                    JSONObject temp = new JSONObject(line);
                    String date = temp.getString("date");
                    if(date.equalsIgnoreCase(dateS))                /*if found, set pos value and exit cicle*/
                    {
                        pos = temp.getLong("pos");
                        flag=false;
                    }
                }
                file.close();
            } catch (IOException e) {
                throw new RuntimeException(e);}
            return pos;
        }

        public static boolean reqApp(Appointment reqToApp)//done
                    /*Method to request an appointment
                    place the request with all data in a Json file,where secretary can approve
                    */
        {
            Long pos= srcAppDate(reqToApp.date);//search correct position

            boolean response=false;
            JSONObject req = new JSONObject();      /*Definition of request with data passed by form*/
            req.put("CF", reqToApp.CF);
            req.put("date", reqToApp.date);
            req.put("hour", reqToApp.hour);
            req.put("desc", reqToApp.descr);
            req.put("status",reqToApp.status);
            try {
                RandomAccessFile app= new RandomAccessFile("src\\Appointment\\app.json","rw");
                if(pos==-1) /*if not found, add to index, the position of new date*/
                {
                    RandomAccessFile index;
                    try {
                        index = new RandomAccessFile("src\\Appointment\\IndexApp.json","rw");
                        JSONObject reqIndex = new JSONObject();    //object for request index
                        pos=app.length();
                        reqIndex.put("pos", pos);
                        reqIndex.put("date", reqToApp.date);
                        index.seek(index.length());
                        index.writeBytes(reqIndex.toString());
                        index.writeBytes("\n");
                        index.close();
                        JSONObject voidReq = voidingReq(reqToApp.date);
                        app.seek(pos);
                        for(int i=0;i<maxApp;i++)
                        {
                            app.writeBytes(voidReq.toString());
                            app.writeBytes("\n");
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }                                                                   /*if found add the appointment at the correct place based on hour*/
                long posizione=pos+((long) dimApp *hourToN(reqToApp.hour));
                app.seek(posizione);
                String line=app.readLine();
                if(line!=null) {
                    JSONObject temp = new JSONObject(line);
                    if (temp.isEmpty() || (!temp.getBoolean("status"))) {
                        response = true;
                        app.seek(posizione);
                        app.writeBytes(req.toString());
                    }
                    else System.out.println("data già inserita");
                }
                else System.out.println("ERRORE");
                app.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return response;
        }

        public static boolean cancApp(Appointment AppToCanc)//done
                /*Method to delete a requested appointment
                    search appointment using date and overwrite it
                    */
        {

            long pos=srcAppDate(AppToCanc.date);
            boolean response=false;
            JSONObject req = new JSONObject();      /*Definition of request with data passed by form*/
            req.put("CF", AppToCanc.CF);
            req.put("date", AppToCanc.date);
            req.put("hour", AppToCanc.hour);
            req.put("desc", AppToCanc.descr);
            req.put("status",false);
            req.put("pos",pos);
            if(pos!=-1) {                       //if pos =-1 the appointment doesn't exist
                try {
                    RandomAccessFile file = new RandomAccessFile("src\\Appointment\\app.json", "rw");       /*search appointment and overwrite with a void one*/
                    long posizione=pos + ((long) dimApp * hourToN(AppToCanc.hour));
                    file.seek(posizione);
                    String line = file.readLine();
                    JSONObject temp = new JSONObject(line);
                    if (!(temp.isEmpty() || (!temp.getBoolean("status")))) {                    //check if it's real appointment to prevent error
                        response = true;
                        file.seek(posizione);
                        JSONObject voidReq = voidingReq(AppToCanc.date);                            //create void appointment
                        file.writeBytes(voidReq.toString());                                        /*overwriting*/
                        file.writeBytes("\n");
                    } else System.out.println("errore nella cancellazione");
                    file.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else System.out.println("errore nella cancellazione");
            return response;
        }

        public static void delOldApp()//not
                 /*Method to delete all old appointment
                   write in a new file all appointment by this date and above, then change the name and delete old files
                    */
        {
            String[] filename={"src\\Appointment\\IndexApp.json","src\\Appointment\\app.json","src\\Appointment\\NEWIndexApp.json","src\\Appointment\\NEWapp.json"};
            try {
                Calendar cal=Calendar.getInstance();
                String str_date= cal.get(Calendar.DATE) + "-" + cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.YEAR);
                DateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
                Date actDate = formatter.parse(str_date);

                for(int i=0;i<2;i++) {
                    String line;
                    RandomAccessFile fileToCanc = new RandomAccessFile(filename[i], "rw");
                    RandomAccessFile Newfile = new RandomAccessFile(filename[i+2], "rw");

                    while (((line = fileToCanc.readLine()) != null))        /*search file */ {
                        JSONObject temp = new JSONObject(line);
                        Date date = formatter.parse(temp.getString("date"));
                        if (actDate.compareTo(date)>=0)                /*if found rewrite in newfile*/ {
                            Newfile.writeBytes(temp.toString());
                        }
                    }
                    Files.delete(Path.of(filename[i]));
                    Files.move(Path.of(filename[i]), Path.of(filename[i+2]));
                }
            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
        }
}
