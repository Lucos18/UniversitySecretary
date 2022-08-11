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

        /*Attributes of Appointment */
        String date;    // dd/mm/yyyy
        String hour;    // hh:mm
        String CF;
        String descr;   //short description max 50 ch
        int id;
        boolean status=true; //if true appointment was approved
        public Appointment(String date, String hour, String CF, String descr,int ramID)
        {
            this.date=date;
            this.hour=hour;
            this.CF=CF;
            this.descr=descr;
            this.id=ramID;

        }

        public boolean getStatus(){return status;}

        public void setStatus(boolean newStatus){status=newStatus;}

        /*Methods*/

        public static int genID()
        {
            int ID;
            ID=1;
            return ID;
        }

        static short hourToN(String hour)
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

        public static String fixDescLen(String description)
                /*Method for inserting "void" character in descrpition field
                    */
        {
            String desc= description;
            char[] cdesc=new char[50];
            char ch='*';

            for (int i = 0; i < desc.length(); i++) {
                cdesc[i] = desc.charAt(i);
            }

            desc = new String(cdesc);
            desc = desc.replace('\0', ch);
            return desc;
        }

        static long srcAppID(String ID)
                                /*Method for appointment search by cf
                                    return the pos of the block of appointment of specified date
                                    */
        {
            long pos = -1;

            try {
                String line;
                boolean flag = true;
                RandomAccessFile file = new RandomAccessFile("src\\Appointment\\IndexApp.json", "rw");

                while (((line = file.readLine()) != null) && flag)        /*Search in index file position of requested cf*/ {
                    JSONObject temp = new JSONObject(line);
                    String id = temp.getString("id");
                    if (id.equalsIgnoreCase(ID))                /*if found, set pos value and exit cicle*/ {
                        pos = Long.parseLong(temp.getString("pos"));
                        flag = false;
                    }
                }
                file.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return pos;
        }

        static JSONArray printAppName(String ID)
                            /*Method print all appointment whith specific name
                                return a jason array with all appointment with that name
                                */
        {
            String line;
            long pos;
            JSONArray appDate= new JSONArray();
            try {
                RandomAccessFile file= new RandomAccessFile("src\\Appointment\\app.json","rw");
                while ((pos=srcAppID(ID))!=-1)
                {
                    line = file.readLine();
                    file.seek(pos);
                    JSONObject temp = new JSONObject(line);
                    appDate.put(temp);
                }
                file.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return appDate;
        }

        static JSONArray printAppDate(String date)
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

        private static Long srcAppDate(String dateS)
                /*Method for appointment search by date
                    return the pos of the block of appointment of specified date
                    */
        {
            long pos = -1;

            try {
                String line;
                boolean flag=true;
                RandomAccessFile file= new RandomAccessFile("src\\Appointment\\IndexApp.json","r");
                System.out.println(file.readLine());
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

        public static boolean reqApp(Appointment reqToApp)
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
            req.put("id",reqToApp.id);
            try {
                RandomAccessFile app= new RandomAccessFile("src\\Appointment\\app.json","rw");
                if(pos==-1) /*if not found, add to index, the position of new date*/
                {
                    RandomAccessFile index= null;
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
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                app.seek(pos+((long) req.length() *hourToN(reqToApp.hour)));
                String line=app.readLine();
                if(line!=null) {
                    JSONObject temp = new JSONObject(line);
                    if (temp.isEmpty() || (!temp.getBoolean("status"))) {
                        response = true;
                        app.writeBytes(req.toString());
                    }
                    else System.out.println("data già inserita");
                }
                else{
                    response = true;
                    app.writeBytes(req.toString());
                }
                app.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return response;
        }

        public static boolean cancApp(Appointment AppToCanc)
                /*Method to request an appointment
                    place the request with all data in a Json file,where secretary can approve
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
            req.put("id",AppToCanc.id);
            req.put("pos",pos);
            if(pos!=-1) {
                try {
                    RandomAccessFile file = new RandomAccessFile("src\\Appointment\\app.json", "rw");
                    file.seek(pos + ((long) req.length() * hourToN(AppToCanc.hour)));
                    String line = file.readLine();
                    JSONObject temp = new JSONObject(line);
                    if (!(temp.isEmpty() || (!temp.getBoolean("status")))) {
                        response = true;
                        file.writeBytes(req.toString());
                    } else System.out.println("errore nella cancellazione");
                    file.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else System.out.println("errore nella cancellazione");
            return response;
        }

        public static void delOldApp()
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
                    boolean flag = true;
                    RandomAccessFile fileToCanc = new RandomAccessFile(filename[i], "rw");
                    RandomAccessFile Newfile = new RandomAccessFile(filename[i+2], "rw");

                    while (((line = fileToCanc.readLine()) != null) && flag)        /*search file */ {
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
