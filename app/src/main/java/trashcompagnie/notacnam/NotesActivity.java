package trashcompagnie.notacnam;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import org.json.simple.JSONObject;

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
    public void onNoteAreGettingon(JSONObject resultJson) {
        JSONObject notesArray = resultJson;
        Log.i(getClass().getName(),"it's back : "+resultJson);
    }
}
