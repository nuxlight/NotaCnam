package trashcompagnie.notacnam.Adapteurs;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import trashcompagnie.notacnam.R;

/**
 * Created by thibaud on 03/01/16.
 */
public class CalArrayAdapteur extends ArrayAdapter<String> {

    public CalArrayAdapteur(Context context, int resource, String[] objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.liste_cal, parent, false);
        }
        String singleElement = getItem(position);
        Log.d(getClass().getName(), "Element of list from position " + position + " is :" + singleElement.toString());

        TextView day = (TextView) convertView.findViewById(R.id.Jours);
        TextView matiereMatin = (TextView) convertView.findViewById(R.id.matiere_mat);
        TextView profMatin = (TextView) convertView.findViewById(R.id.prof_mat);
        TextView matiereAp = (TextView) convertView.findViewById(R.id.matiere_ap);
        TextView profAp = (TextView) convertView.findViewById(R.id.prof_ap);

        try {
            JSONObject tempJson = new JSONObject(singleElement);
            //mercredi
            if(position == 0){
                day.setText("Mercredi");
            }
            //Jeudi
            if(position == 1){
                day.setText("Jeudi");
            }
            //Vendredi
            if(position == 2){
                day.setText("Vendredi");
            }
            matiereMatin.setText(tempJson.getString("mat"));
            profMatin.setText(tempJson.getString("mat_prof"));
            matiereAp.setText(tempJson.getString("ap"));
            profAp.setText(tempJson.getString("ap_prof"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return convertView;
    }
}
