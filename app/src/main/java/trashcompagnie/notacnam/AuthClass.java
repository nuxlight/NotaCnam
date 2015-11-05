package trashcompagnie.notacnam;

import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by thibaud on 31/10/15.
 */
public class AuthClass {

    private String urlLogin = "http://iscople.gescicca.net/Cursus.aspx?cr=MPY";
    private String compte_id;
    //private String compte_id = "36944";
    //private String code_auditeur= "MPY271047";
    private String code_auditeur;

    public AuthClass(String user, String code){
        this.compte_id = user;
        this.code_auditeur = code;
    }

    public boolean login() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(urlLogin).openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        OutputStream outputStream = connection.getOutputStream();
        String postParms = "compte_id="+compte_id+"&code_auditeur="+code_auditeur;
        outputStream.write(postParms.getBytes());
        outputStream.flush();
        outputStream.close();

        InputStream stream = connection.getInputStream();
        OutputStream outFile = new FileOutputStream("./notes.txt");
        int read = 0;
        byte[] bytes = new byte[1024];
        while ((read = stream.read(bytes)) != -1) {
            outFile.write(bytes, 0, read);
        }
        MakeJsonNote();
        return false;
    }

    public JSONObject MakeJsonNote() throws IOException {
        File file = new File("./notes.txt");
        Document document = Jsoup.parse(file, "UTF-8");
        Elements tableau = document.select("tr");
        JSONObject jsonReturn = new JSONObject();
        for(Object elements : tableau.toArray()){
            Element intraDoc = (Element) elements;
            JSONObject tempJson = new JSONObject();
            int a = 0;
            for(Object entry : intraDoc.select("span").toArray()){
                Element intraEntry = (Element) entry;
                tempJson.put("entry"+a,intraEntry.text());
                a++;
            }
            String[] entete = intraDoc.text().toString().split(" ");
            if(entete.length > 10 && entete.length != 13){
                jsonReturn.put(entete[0] + entete[1] + entete[2]+"-"+entete[3], tempJson);
            }
        }
        System.out.println(jsonReturn.toJSONString());
        return jsonReturn;
    }
}
