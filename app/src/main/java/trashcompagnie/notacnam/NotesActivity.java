package trashcompagnie.notacnam;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import org.json.simple.JSONObject;

import java.io.IOException;

public class NotesActivity extends AppCompatActivity {

    private ListView notesListe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        Bundle bundle = getIntent().getExtras();
        String compte_id = bundle.getString("compte");
        String code_auditeur = bundle.getString("audit");
        AuthClass authTools = new AuthClass(compte_id,code_auditeur);
        notesListe = (ListView) findViewById(R.id.liste_notes);

        try {
            JSONObject notesArray = authTools.getListOfNotes();


        } catch (IOException e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(NotesActivity.this);
            builder.setMessage("Erreur de récupération des notes, +infos :" + e.toString());
            builder.setPositiveButton("OK", null);
            builder.show();
        }
    }
}
