package trashcompagnie.notacnam;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText labelOne;
    private EditText labelTow;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Configure label and button
        labelOne = (EditText) findViewById(R.id.compte);
        labelTow = (EditText) findViewById(R.id.auditeur);
        loginBtn = (Button) findViewById(R.id.login);

        //Check history user information
        SharedPreferences preferences = getSharedPreferences(MainActivity.class.getSimpleName(), 1);
        labelOne.setText(preferences.getString("compte_id", ""));
        labelTow.setText(preferences.getString("code_auditeur", ""));

        if(labelOne.getText().equals("") && labelTow.getText().equals("")){
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setMessage("Merci de remplir les champs");
            dialog.show();
        }

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!labelOne.getText().toString().isEmpty() && !labelTow.getText().toString().isEmpty()) {
                    //Save user infromations
                    saveUserPrefs(labelOne.getText().toString(), labelTow.getText().toString());
                    Intent intent = new Intent(getApplicationContext(), NotesActivity.class);
                    intent.putExtra("compte", labelOne.getText().toString());
                    intent.putExtra("audit", labelTow.getText().toString());
                    startActivity(intent);
                }
                else{
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setMessage("Merci de remplir les champs");
                    dialog.setPositiveButton("ok", null);
                    dialog.show();
                }
            }
        });
    }

    private void saveUserPrefs(String compte_id, String code_auditeur) {
        SharedPreferences preferences = getSharedPreferences(MainActivity.class.getSimpleName(), 1);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("compte_id", compte_id);
        editor.putString("code_auditeur", code_auditeur);
        editor.commit();
    }
}
