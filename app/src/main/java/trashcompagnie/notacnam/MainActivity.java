package trashcompagnie.notacnam;

import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        SharedPreferences preferences = getPreferences(getApplicationContext().MODE_PRIVATE);
        labelOne.setText(preferences.getString("COMPTE",""));
        labelTow.setText(preferences.getString("AUDITEUR",""));

        if(labelOne.getText().equals("") && labelTow.getText().equals("")){
            AlertDialog.Builder dialog = new AlertDialog.Builder(getApplicationContext());
            dialog.setMessage("Merci de remplir les champs");
            dialog.show();
        }

    }
}
