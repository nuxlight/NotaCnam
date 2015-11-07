package trashcompagnie.notacnam;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import org.json.simple.JSONArray;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by thibaud on 31/10/15.
 */
public class AuthClass extends AsyncTask<String, Void, JSONArray> {

    private String urlLogin = "http://iscople.gescicca.net/Cursus.aspx?cr=MPY";
    private String compte_id;
    private AuthClassLisner authClassLisner;
    //private String compte_id = "36944";
    //private String code_auditeur= "MPY271047";
    private String code_auditeur;
    private String filePath;

    public AuthClass(AuthClassLisner lisner){
        authClassLisner = lisner;
    }

    @Override
    protected JSONArray doInBackground(String... strings) {
        this.compte_id = strings[0];
        this.code_auditeur = strings[1];
        try {
            return getListOfNotes();
        } catch (IOException e) {
            Log.e(getClass().getName(), "ERROR :" + e.toString());
            return null;
        }
    }

    @Override
    protected void onPostExecute(JSONArray jsonObject) {
        authClassLisner.onNoteAreGettingon(jsonObject);
        super.onPostExecute(jsonObject);
    }

    public JSONArray getListOfNotes() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(urlLogin).openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        OutputStream outputStream = connection.getOutputStream();
        String postParms = "compte_id="+compte_id+"&code_auditeur="+code_auditeur;
        outputStream.write(postParms.getBytes());
        outputStream.flush();
        outputStream.close();

        InputStream stream = connection.getInputStream();
        //Recuperation du reperoire de travail
        filePath = Environment.getExternalStorageDirectory() + "/notes.txt";
        Log.d(getClass().getName(),"File path"+filePath);
        OutputStream outFile = new FileOutputStream(filePath);
        int read = 0;
        byte[] bytes = new byte[1024];
        while ((read = stream.read(bytes)) != -1) {
            outFile.write(bytes, 0, read);
        }
        return MakeJsonNote();
    }

    public JSONArray MakeJsonNote() throws IOException {
        File file = new File(filePath);
        Document document = Jsoup.parse(file, "UTF-8");
        Elements tableau = document.select("tr");
        JSONArray jsonReturn = new JSONArray();
        int b=0;
        for(Object elements : tableau.toArray()){
            Element intraDoc = (Element) elements;
            JSONArray tempJson = new JSONArray();
            int a = 0;
            for(Object entry : intraDoc.select("span").toArray()){
                Element intraEntry = (Element) entry;
                //tempJson.put("entry"+a,intraEntry.text());
                tempJson.add(intraEntry.text());
                a++;
            }
            String[] entete = intraDoc.text().toString().split(" ");
            if(entete.length > 10 && entete.length != 13){
                //jsonReturn.put(entete[0] + entete[1] + entete[2]+"-"+entete[3], tempJson);
                //jsonReturn.put(b, tempJson);
                tempJson.add(a-a,entete[0] + entete[1] + entete[2]+"-"+entete[3]);
                jsonReturn.add(tempJson);
                b++;
            }
        }
        Log.w(getClass().getName(),"JSON Created : "+jsonReturn);
        return jsonReturn;
    }

}
