package Appointment;

import org.json.JSONObject;

import java.io.IOException;
import java.io.RandomAccessFile;



public class Appointment {

        /*Const*/
        static final int maxApp =14;        //max appointment per day

        /*Attributes of Appointment */
        String date;    // dd/mm/yyyy
        String hour;    // hh/mm
        String CF;
        String descr;   //short description max 50 ch
        boolean approved=false; //if true appointment was approved
        Appointment(String date, String hour, String CF, String descr)
        {
            this.date=date;
            this.hour=hour;
            this.CF=CF;
            this.descr=descr;
        }

        /*Methods*/
        static void reqApp(Appointment reqToApp)
                    /*Method to request an appointment
                    place the request with all data in a Json file,where secretary can approve
                    */
        {
            long pos = 0;
            JSONObject req = new JSONObject();      /*Definition of request with data passed by form*/
            req.put("CF", reqToApp.CF);
            req.put("date", reqToApp.date);
            req.put("hour", reqToApp.hour);
            req.put("desc", reqToApp.descr);
            req.put("flag",reqToApp.approved);

            try {
                int i=0;
                String line;
                boolean flag=true;
                RandomAccessFile file= new RandomAccessFile("IndexApp.json","rw");

                while(((line=file.readLine())!=null) && flag)        /*Search in index file position of requested data*/
                {
                    JSONObject temp = new JSONObject(line);
                    String data = temp.getString("date");
                    if(data.equalsIgnoreCase(reqToApp.date))                /*if found, set pos value and exit cicle*/
                    {
                        pos = Long.parseLong(temp.getString("pos"));
                        flag=false;
                    }
                    i++;
                }
                if(flag)                                             /*if not, add to index, the position of new date*/
                {
                    JSONObject reqIndex = new JSONObject();
                    pos= (long) maxApp *req.length()*i;    //calculate position using n of appointment * bytes * number of date
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
                file.seek(pos+(0/*req.length()*reqToApp.hour*/));
                String line=file.readLine();
                JSONObject temp = new JSONObject(line);
                if(temp.isEmpty()) {
                    file.writeBytes(req.toString());
                }
                else
                {
                    System.out.println("data già inserita");
                }
                file.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }




}
