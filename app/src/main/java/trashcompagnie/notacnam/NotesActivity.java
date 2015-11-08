package trashcompagnie.notacnam;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.json.simple.JSONArray;

import java.util.ArrayList;

public class NotesActivity extends AppCompatActivity implements AuthClassLisner {

    private ListView notesListe;
    private ProgressDialog loadDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        Bundle bundle = getIntent().getExtras();
        String compte_id = bundle.getString("compte");
        String code_auditeur = bundle.getString("audit");

        //Create Progress dialog
        loadDialog = new ProgressDialog(this);
        loadDialog.setMessage("Chargement des notes en cours...");
        loadDialog.setCancelable(false);

        AuthClass authTools = new AuthClass(NotesActivity.this);
        String[] parms = {compte_id, code_auditeur};
        authTools.execute(parms);
        loadDialog.show();
        notesListe = (ListView) findViewById(R.id.liste_notes);
    }

    @Override
    public void onNoteAreGettingon(JSONArray resultJson) {
        ArrayList<String> newArray = toJsonList(resultJson);
        Log.i(getClass().getName(), "it's back : " + newArray);
        String[] updateNewArray = new String[newArray.size()];
        updateNewArray = newArray.toArray(updateNewArray);
        Log.i(getClass().getName(), "it's back lenght : " + updateNewArray.length);
        ListAdapter arrayAdapter = new NotaArrayAdapteur(getApplicationContext(),updateNewArray);
        notesListe.setAdapter(arrayAdapter);
        loadDialog.dismiss();
    }

    private ArrayList<String> toJsonList(JSONArray array){
        ArrayList<String> list = new ArrayList<String>();
        JSONArray jsonArray = (JSONArray)array;
        if (jsonArray != null) {
            for (int i=0;i<jsonArray.size();i++){
                if(!jsonArray.get(i).toString().replace('"',' ').equals("[ AnnéeuniversitaireCode-Unité ]")){
                    list.add(jsonArray.get(i).toString());
                }
            }
        }
        return list;
    }
}
