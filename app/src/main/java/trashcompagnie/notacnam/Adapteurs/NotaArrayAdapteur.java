package trashcompagnie.notacnam.Adapteurs;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import trashcompagnie.notacnam.R;

/**
 * Created by thibaud on 06/11/15.
 */
public class NotaArrayAdapteur extends ArrayAdapter<String> {

    public NotaArrayAdapteur(Context context, String[] resource) {
        super(context, 0, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.liste_style, parent, false);
        }

        String singleElement = getItem(position);
        Log.d(getClass().getName(),"Element of list from position "+position+" is :"+singleElement);
        TextView UE_text = (TextView) convertView.findViewById(R.id.UE_txt);
        TextView UE_Title = (TextView) convertView.findViewById(R.id.TitleUE_txt);
        TextView Inscrit = (TextView) convertView.findViewById(R.id.InscripUE_txt);
        TextView Note1 = (TextView) convertView.findViewById(R.id.Note_1);
        TextView Note2 = (TextView) convertView.findViewById(R.id.Note_2);
        TextView Region = (TextView) convertView.findViewById(R.id.Region_txt);
        TextView Altern = (TextView) convertView.findViewById(R.id.Alternance_txt);

        //Change strig in array
        String[] listOfElement = singleElement.split(",");

        UE_text.setText(listOfElement[0].replace('[',' ').replace('"', '='));
        UE_Title.setText(listOfElement[1].replace('"', ' '));
        Note1.setText(listOfElement[2].replace('"', ' ').replace("\\", ""));
        Note2.setText(listOfElement[3].replace('"', ' ').replace("\\", "").replace(']',' '));

        return convertView;
    }
}
