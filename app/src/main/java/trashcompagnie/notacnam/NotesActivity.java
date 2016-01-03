package trashcompagnie.notacnam;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.json.simple.JSONArray;

import java.util.ArrayList;

import trashcompagnie.notacnam.Adapteurs.NotaArrayAdapteur;
import trashcompagnie.notacnam.Tasks.AuthClass;
import trashcompagnie.notacnam.Tasks.AuthClassLisner;

/**
 * DEPRECATED Class, don't use it because now it's only Fragments
 * @author Thibaud Pellissier
 * @deprecated NOW
 */
public class NotesActivity extends AppCompatActivity implements AuthClassLisner, SwipeRefreshLayout.OnRefreshListener {

    private ListView notesListe;
    private ProgressDialog loadDialog;
    private SwipeRefreshLayout swipeRefreshLayout;
    private AuthClass authTools;
    private String compte_id;
    private String code_auditeur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        Bundle bundle = getIntent().getExtras();
        compte_id = bundle.getString("compte");
        code_auditeur = bundle.getString("audit");

        //Create Progress dialog
        loadDialog = new ProgressDialog(this);
        loadDialog.setMessage("Chargement des notes en cours...");
        loadDialog.setCancelable(false);

        authTools = new AuthClass(NotesActivity.this);
        String[] parms = {compte_id, code_auditeur};
        authTools.execute(parms);
        loadDialog.show();
        notesListe = (ListView) findViewById(R.id.liste_notes);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
    }


    @Override
    public void onRefresh() {
        authTools = new AuthClass(NotesActivity.this);
        String[] parms = {compte_id, code_auditeur};
        authTools.execute(parms);
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
        if (loadDialog.isShowing()){
            loadDialog.dismiss();
        }
        swipeRefreshLayout.setRefreshing(false);
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
