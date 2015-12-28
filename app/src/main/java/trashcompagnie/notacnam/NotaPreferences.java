package trashcompagnie.notacnam;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Preferences for application, i hate the arguments system
 * @author Thibaud Pellissier
 * @version 0.1
 */
public class NotaPreferences  {

    private Context appContext;

    public NotaPreferences(Context context) {
        appContext = context;
    }

    public void saveUserPrefs(String compte_id, String code_auditeur) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(appContext);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("compte_id", compte_id);
        editor.putString("code_auditeur", code_auditeur);
        editor.commit();
    }

    public String[] getUserPrefs(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(appContext);
        String[] perfsResult = new String[]{preferences.getString("compte_id", "null"), preferences.getString("code_auditeur", "null")};
        Log.i(getClass().getSimpleName(), "getUserPrefs = "+
                preferences.getString("compte_id", "null")+" - "+
                preferences.getString("code_auditeur", "null"));
        return perfsResult;
    }
}
