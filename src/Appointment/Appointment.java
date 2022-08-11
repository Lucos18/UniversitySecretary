package Appointment;

import org.json.JSONObject;

import java.io.IOException;
import java.io.RandomAccessFile;



public class Appointment {

        /*Const*/
        static final int maxApp =12;        //max appointment per day

        /*Attributes of Appointment */
        String date;    // dd/mm/yyyy
        String hour;    // hh:mm
        String CF;
        String descr;   //short description max 50 ch
        boolean status=false; //if true appointment was approved
        Appointment(String date, String hour, String CF, String descr)
        {
            this.date=date;
            this.hour=hour;
            this.CF=CF;
            this.descr=descr;
        }

        public boolean getStatus(){return status;}

        public void setStatus(boolean newStatus){status=newStatus;}

        /*Methods*/

        static short dateToN(String date)
        {
            String[] dateCheck={"09:00","09:30","10:00","10:30","11:00","11:30","12:00","12:30","14:00","14:30","15:00","15:30",};
            short n=0;
            for(;n<11;n++)
                if(date.equalsIgnoreCase(dateCheck[n]))
                    break;
            return n;
        }

        static boolean reqApp(Appointment reqToApp)
                    /*Method to request an appointment
                    place the request with all data in a Json file,where secretary can approve
                    */
        {
            boolean response=false;
            long pos = 0;
            JSONObject req = new JSONObject();      /*Definition of request with data passed by form*/
            req.put("CF", reqToApp.CF);
            req.put("date", reqToApp.date);
            req.put("hour", reqToApp.hour);
            req.put("desc", reqToApp.descr);
            req.put("flag",reqToApp.status);
            req.put("pos",pos);

            try {
                int i=0;
                String line;
                boolean flag=true;
                RandomAccessFile file= new RandomAccessFile("IndexApp.json","rw");

                while(((line=file.readLine())!=null) && flag)        /*Search in index file position of requested data*/
                {
                    JSONObject temp = new JSONObject(line);
                    String date = temp.getString("date");
                    if(date.equalsIgnoreCase(reqToApp.date))                /*if found, set pos value and exit cicle*/
                    {
                        pos = Long.parseLong(temp.getString("pos"));
                        flag=false;
                    }
                    else i++;
                }
                if(flag)                                             /*if not, add to index, the position of new date*/
                {
                    JSONObject reqIndex = new JSONObject();    //object for request index
                    pos= (long) maxApp * req.length() * i;    //calculate position using n of appointment * bytes * number of date
                    reqIndex.put("pos", pos);
                    reqIndex.put("date", reqToApp.date);
                    file.seek(file.length());
                    file.writeBytes(reqIndex.toString());
                }
                file.close();
            } catch (IOException e) {
                throw new RuntimeException(e);}

            try {
                RandomAccessFile file= new RandomAccessFile("app.json","rw");
                file.seek(pos+((long) req.length() *dateToN(reqToApp.hour)));
                String line=file.readLine();
                JSONObject temp = new JSONObject(line);
                if(temp.isEmpty())
                {
                    response=true;
                    file.writeBytes(req.toString());
                }
                else System.out.println("data già inserita");
                file.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return response;
        }





        static void confApp()
        {

        }

        static void cancApp()
        {

        }
}
