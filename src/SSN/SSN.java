package SSN;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.io.*;
import java.net.http.HttpResponse;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

// SI POTREBBE CONTROLLARE LA CORRETTEZZA DEL FORMATO DELLA DATA DI NASCITA

import Users.Users;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SSN extends Users {
    private Boolean isVocale(char lettera){
        return lettera == 'a' || lettera == 'e' || lettera == 'i' || lettera == 'o' || lettera == 'u';
    }
    private String codiceFiscale = "";

    public SSN(String name, String surname, String dateB, String cityB, String sex, String email, String password) throws IOException, InterruptedException {
        super(name, surname, dateB, cityB, sex, email, password);

        int i , cntConsonanti = 0;
        String lowerSurname = surname.toLowerCase();

        // COGNOME.1 = SERVONO LE PRIME 3 CONSONANTI
        for(i = 0; codiceFiscale.length() != 3 && i < lowerSurname.length(); ++i){
            if(!isVocale(lowerSurname.charAt(i))){
                codiceFiscale += lowerSurname.charAt(i);
                ++cntConsonanti;
            }
        }
        // COGNOME.2 = SE IL NUMERO DI CONSONANTI NON RAGGIUNGE 3, SI AGGIUNGONO LE VOCALI IN ORDINE
        if(cntConsonanti < 3){
            for(i = 0; codiceFiscale.length() != 3 && i < lowerSurname.length(); ++i){
                if(isVocale(lowerSurname.charAt(i))) codiceFiscale += lowerSurname.charAt(i);
            }
        }
        // COGNOME.3 = se il cognome è di sole 2 lettere, nel terzo carattere DEVE ESSERE inserita una X
        if(surname.length() == 2) codiceFiscale += 'X';

        // NOME.1 = .....
        //It removes all white spaces from TaxCode when name or surname has more than one word
        String lowerName = name.toLowerCase();
        lowerName = (lowerName.replaceAll(" ", ""));

        cntConsonanti = 0;
        for(i = 0; i < lowerName.length(); ++i){
            if(!isVocale(lowerName.charAt(i))) ++cntConsonanti;
        }
        // NOME.2 = .....
        int nConsonante = 0;
        for(i = 0; codiceFiscale.length() != 6 && i < lowerName.length(); ++i){
            if(!isVocale(lowerName.charAt(i))){
                ++nConsonante;
                if(cntConsonanti > 3 && nConsonante == 2);
                else codiceFiscale += lowerName.charAt(i);
            }
        }
        // NOME.3 = .....
        if(cntConsonanti < 3){
            for(i = 0; codiceFiscale.length() != 6 && i < lowerName.length(); ++i){
                if(isVocale(lowerName.charAt(i))) codiceFiscale += lowerName.charAt(i);
            }
        }
        // NOME.4 = .....
        if(name.length() == 2) codiceFiscale += 'X';


        System.out.println(cntConsonanti);

        // DATA NASCITA.1 = .....
        String[] dataNascita = dateB.split("/");

        codiceFiscale += dataNascita[2].charAt(2);
        codiceFiscale += dataNascita[2].charAt(3);

        // DATA NASCITA.2 = .....

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

        // DATA NASCITA.3 = .....
        if(Integer.parseInt(dataNascita[0]) < 10) dataNascita[0] = "0" + dataNascita[0];
        if(sex == "F") dataNascita[0] = String.valueOf(Integer.parseInt(dataNascita[0]) + 40);

        codiceFiscale += dataNascita[0];

        // COMUNE NASCITA.1 = .....
        //Creating Client to do an API request for "CodiceStatale" of CityB
        var client = HttpClient.newHttpClient();
        //Creating request for the API request
        var request = HttpRequest.newBuilder(
                URI.create("https://www.gerriquez.com/comuni/ws.php?dencomune="+ cityB))
                .header("accept", "application/json")
                .build();
        //The response will be in JSON format, and it will contain all the information of the CityB
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
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

        // CARATTERE DI CONTROLLO

        // https://braviincasa.altervista.org/come-si-calcola-ultima-lettera-del-codice-fiscale-esempio-pratico/#:~:text=I%20primi%20tre%20caratteri%20si,trova%20alla%20fine%20del%20codice).



        // UPPERCASE
        codiceFiscale = codiceFiscale.toUpperCase();
    }

    // 4 COMUNE da fare
    // 1 RANDOM da fare

    public String getCodiceFiscale(){return codiceFiscale;}
}