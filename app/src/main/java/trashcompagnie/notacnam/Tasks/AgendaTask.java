package trashcompagnie.notacnam.Tasks;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by thibaud on 03/01/16.
 */
public class AgendaTask extends AsyncTask<Void, Void, String> {

    private String url = "http://blog.nuxlight.ovh:7070";
    private AgendaTaskListener taskListener;

    public AgendaTask(AgendaTaskListener listener){
        taskListener = listener;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            InputStream stream = connection.getInputStream();
            String inputStreamString = new Scanner(stream,"UTF-8").useDelimiter("\\A").next();
            return inputStreamString.replaceAll("\\<.*?>","");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        try {
            taskListener.onAgendaAreGettingon(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        super.onPostExecute(s);
    }
}
