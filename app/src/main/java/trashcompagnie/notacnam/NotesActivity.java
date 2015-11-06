package trashcompagnie.notacnam;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import org.json.simple.JSONArray;

import java.util.ArrayList;

public class NotesActivity extends AppCompatActivity implements AuthClassLisner {

    private ListView notesListe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        Bundle bundle = getIntent().getExtras();
        String compte_id = bundle.getString("compte");
        String code_auditeur = bundle.getString("audit");
        AuthClass authTools = new AuthClass(NotesActivity.this);
        String[] parms = {compte_id, code_auditeur};
        authTools.execute(parms);
        notesListe = (ListView) findViewById(R.id.liste_notes);
    }

    @Override
    public void onNoteAreGettingon(JSONArray resultJson) {
        ArrayList<String> newArray = toJsonList(resultJson);
        Log.i(getClass().getName(), "it's back : " + newArray);
        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(NotesActivity.this,android.R.layout.simple_expandable_list_item_1, );
        //notesListe.setAdapter(arrayAdapter);
    }

    private ArrayList<String> toJsonList(JSONArray array){
        ArrayList<String> list = new ArrayList<String>();
        JSONArray jsonArray = (JSONArray)array;
        if (jsonArray != null) {
            int len = jsonArray.size();
            for (int i=0;i<len;i++){
                list.add(jsonArray.get(i).toString());
            }
        }
        return list;
    }
}
