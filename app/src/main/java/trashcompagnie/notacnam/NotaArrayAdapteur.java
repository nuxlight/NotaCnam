package trashcompagnie.notacnam;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * Created by thibaud on 06/11/15.
 */
public class NotaArrayAdapteur extends ArrayAdapter<String> {
    public NotaArrayAdapteur(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        return v;
    }
}
