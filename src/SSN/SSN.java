package SSN;
import Users.Users;

import java.io.IOException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SSN {
    //Function that returns true if a char is a vocal
    private static Boolean isVocale(char lettera){
        return lettera == 'a' || lettera == 'e' || lettera == 'i' || lettera == 'o' || lettera == 'u';
    }

//Generator of italian Tax Code
    public static String SSNC(Users user) throws IOException, InterruptedException {
        String codiceFiscale = "";
        int i , cntConsonanti = 0;
        String lowerSurname = user.getSurname().toLowerCase();

//First loop of the Surname Tax Code, where it will get only the first 3 consonants

        for(i = 0; codiceFiscale.length() != 3 && i < lowerSurname.length(); ++i){
            if(!isVocale(lowerSurname.charAt(i))){
                codiceFiscale += lowerSurname.charAt(i);
                ++cntConsonanti;
            }
        }
//Second loop, it will get also voxels if consonant are lower than 3

        if(cntConsonanti < 3){
            for(i = 0; codiceFiscale.length() != 3 && i < lowerSurname.length(); ++i){
                if(isVocale(lowerSurname.charAt(i))) codiceFiscale += lowerSurname.charAt(i);
            }
        }
//Third loop, if surname has only two letters or less, then it will add an X at the end

        if(user.getSurname().length() == 2) codiceFiscale += 'X';


//It removes all white spaces from TaxCode when name or surname has more than one word

        String lowerName = user.getName().toLowerCase();
        lowerName = (lowerName.replaceAll(" ", ""));

//It counts how many consonants are inside the name

        cntConsonanti = 0;
        for(i = 0; i < lowerName.length(); ++i) {
            if (!isVocale(lowerName.charAt(i))) ++cntConsonanti;
        }
//If name contains more of 4 cons

        int nConsonante = 0;
        for(i = 0; codiceFiscale.length() != 6 && i < lowerName.length(); ++i){
            if(!isVocale(lowerName.charAt(i))){
                ++nConsonante;
                if(cntConsonanti > 3 && nConsonante == 2);
                else codiceFiscale += lowerName.charAt(i);
            }
        }
//If name consonant counter is minus of 3

        if(cntConsonanti < 3){
            for(i = 0; codiceFiscale.length() != 6 && i < lowerName.length(); ++i){
                if(isVocale(lowerName.charAt(i))) codiceFiscale += lowerName.charAt(i);
            }
        }
//If the name contains less than 2 character, replace the third character with "X"

        if(user.getName().length() == 2) codiceFiscale += 'X';

//Standard date type: dd/mm/aaaa. In that case, we remove the "/" character and split the number

        String[] dataNascita = user.getdateB().split("/");

        codiceFiscale += dataNascita[2].charAt(2);
        codiceFiscale += dataNascita[2].charAt(3);

//Switch with month of the people who wants to use SSN calculator.
/*
        A = gennaio
        B = febbraio
        C = marzo
        D = aprile
        E = maggio
        H = giugno
        L = luglio
        M = agosto
        P = settembre
        R = ottobre
        S = novembre
        T = dicembre.
*/
//Solved the "0" behind month number

        if(dataNascita[1].charAt(0) == '0') dataNascita[1] = dataNascita[1].replace("0", "");

        switch(dataNascita[1]){
            case "1": codiceFiscale += "A"; break;
            case "2": codiceFiscale += "B"; break;
            case "3": codiceFiscale += "C"; break;
            case "4": codiceFiscale += "D"; break;
            case "5": codiceFiscale += "E"; break;
            case "6": codiceFiscale += "H"; break;
            case "7": codiceFiscale += "L"; break;
            case "8": codiceFiscale += "M"; break;
            case "9": codiceFiscale += "P"; break;
            case "10": codiceFiscale += "R"; break;
            case "11": codiceFiscale += "S"; break;
            case "12": codiceFiscale += "T"; break;
        }
//Use data and sex of the people who want to use SSN calculator.

        if(Integer.parseInt(dataNascita[0]) < 10) dataNascita[0] = "" + dataNascita[0];
        if(user.getSex() == "F") dataNascita[0] = String.valueOf(Integer.parseInt(dataNascita[0]) + 40);
        codiceFiscale += dataNascita[0];

//Used the API city birth for calculate the 11,12,13,14 character of SSN
//Creating Client to do an API request for "CodiceStatale" of CityB

        var client = HttpClient.newHttpClient();
        String cityB = (user.getCityB().replaceAll(" ", "%20"));

//Creating request for the API request
        var request = HttpRequest.newBuilder(
                URI.create("https://www.gerriquez.com/comuni/ws.php?dencomune="+ cityB))
                .header("accept", "application/json")
                .build();

//The response will be in JSON format, and it will contain all the information of the CityB
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String risposta= response.body();
        JSONArray jsonArray = new JSONArray();
        JSONParser jsonParser = new JSONParser();

        try{
            jsonArray = (JSONArray) jsonParser.parse(risposta);
        }
        catch(ParseException e){
            throw new RuntimeException(e);
        }

        codiceFiscale += (String) ((JSONObject) jsonArray.get(0)).get("CodiceCatastaleDelComune");

//Algorythm for validate the SSN code. This character is used for omocode case.
        int sommaPari=0;
        codiceFiscale = codiceFiscale.toUpperCase();
        for (int j=1;j<=13;j+=2) {
            switch (codiceFiscale.charAt(j)) {
                case '0': {sommaPari+=0;break;}
                case '1': {sommaPari+=1;break;}
                case '2': {sommaPari+=2;break;}
                case '3': {sommaPari+=3;break;}
                case '4': {sommaPari+=4;break;}
                case '5': {sommaPari+=5;break;}
                case '6': {sommaPari+=6;break;}
                case '7': {sommaPari+=7;break;}
                case '8': {sommaPari+=8;break;}
                case '9': {sommaPari+=9;break;}
                case 'A': {sommaPari+=0;break;}
                case 'B': {sommaPari+=1;break;}
                case 'C': {sommaPari+=2;break;}
                case 'D': {sommaPari+=3;break;}
                case 'E': {sommaPari+=4;break;}
                case 'F': {sommaPari+=5;break;}
                case 'G': {sommaPari+=6;break;}
                case 'H': {sommaPari+=7;break;}
                case 'I': {sommaPari+=8;break;}
                case 'J': {sommaPari+=9;break;}
                case 'K': {sommaPari+=10;break;}
                case 'L': {sommaPari+=11;break;}
                case 'M': {sommaPari+=12;break;}
                case 'N': {sommaPari+=13;break;}
                case 'O': {sommaPari+=14;break;}
                case 'P': {sommaPari+=15;break;}
                case 'Q': {sommaPari+=16;break;}
                case 'R': {sommaPari+=17;break;}
                case 'S': {sommaPari+=18;break;}
                case 'T': {sommaPari+=19;break;}
                case 'U': {sommaPari+=20;break;}
                case 'V': {sommaPari+=21;break;}
                case 'W': {sommaPari+=22;break;}
                case 'X': {sommaPari+=23;break;}
                case 'Y': {sommaPari+=24;break;}
                case 'Z': {sommaPari+=25;break;}
            }
        }
        int sommaDispari=0;
        for (int k=0;k<=14;k+=2) {
            switch (codiceFiscale.charAt(k)) {
                case '0': {sommaDispari+=1;break;}
                case '1': {sommaDispari+=0;break;}
                case '2': {sommaDispari+=5;break;}
                case '3': {sommaDispari+=7;break;}
                case '4': {sommaDispari+=9;break;}
                case '5': {sommaDispari+=13;break;}
                case '6': {sommaDispari+=15;break;}
                case '7': {sommaDispari+=17;break;}
                case '8': {sommaDispari+=19;break;}
                case '9': {sommaDispari+=21;break;}
                case 'A': {sommaDispari+=1;break;}
                case 'B': {sommaDispari+=0;break;}
                case 'C': {sommaDispari+=5;break;}
                case 'D': {sommaDispari+=7;break;}
                case 'E': {sommaDispari+=9;break;}
                case 'F': {sommaDispari+=13;break;}
                case 'G': {sommaDispari+=15;break;}
                case 'H': {sommaDispari+=17;break;}
                case 'I': {sommaDispari+=19;break;}
                case 'J': {sommaDispari+=21;break;}
                case 'K': {sommaDispari+=2;break;}
                case 'L': {sommaDispari+=4;break;}
                case 'M': {sommaDispari+=18;break;}
                case 'N': {sommaDispari+=20;break;}
                case 'O': {sommaDispari+=11;break;}
                case 'P': {sommaDispari+=3;break;}
                case 'Q': {sommaDispari+=6;break;}
                case 'R': {sommaDispari+=8;break;}
                case 'S': {sommaDispari+=12;break;}
                case 'T': {sommaDispari+=14;break;}
                case 'U': {sommaDispari+=16;break;}
                case 'V': {sommaDispari+=10;break;}
                case 'W': {sommaDispari+=22;break;}
                case 'X': {sommaDispari+=25;break;}
                case 'Y': {sommaDispari+=24;break;}
                case 'Z': {sommaDispari+=23;break;}
            }
        }
        int interoControllo = (sommaPari+sommaDispari)%26;
        String carattereControllo="";
        switch (interoControllo) {
            case 0:{carattereControllo="A";break;}
            case 1:{carattereControllo="B";break;}
            case 2:{carattereControllo="C";break;}
            case 3:{carattereControllo="D";break;}
            case 4:{carattereControllo="E";break;}
            case 5:{carattereControllo="F";break;}
            case 6:{carattereControllo="G";break;}
            case 7:{carattereControllo="H";break;}
            case 8:{carattereControllo="I";break;}
            case 9:{carattereControllo="J";break;}
            case 10:{carattereControllo="K";break;}
            case 11:{carattereControllo="L";break;}
            case 12:{carattereControllo="M";break;}
            case 13:{carattereControllo="N";break;}
            case 14:{carattereControllo="O";break;}
            case 15:{carattereControllo="P";break;}
            case 16:{carattereControllo="Q";break;}
            case 17:{carattereControllo="R";break;}
            case 18:{carattereControllo="S";break;}
            case 19:{carattereControllo="T";break;}
            case 20:{carattereControllo="U";break;}
            case 21:{carattereControllo="V";break;}
            case 22:{carattereControllo="W";break;}
            case 23:{carattereControllo="X";break;}
            case 24:{carattereControllo="Y";break;}
            case 25:{carattereControllo="Z";break;}
        }

//SSN calculated.
        codiceFiscale+=carattereControllo;
        return codiceFiscale;
    }
}

