package trashcompagnie.notacnam.Fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import trashcompagnie.notacnam.Adapteurs.CalArrayAdapteur;
import trashcompagnie.notacnam.R;
import trashcompagnie.notacnam.Tasks.AgendaTask;
import trashcompagnie.notacnam.Tasks.AgendaTaskListener;

public class CalFragment extends Fragment implements AgendaTaskListener {

    private ListView agendaListe;
    private ProgressDialog loadDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cal, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadDialog = new ProgressDialog(getActivity());
        loadDialog.setMessage("Chargement de l'agenda...");
        loadDialog.setCancelable(false);
        AgendaTask agendaTask = new AgendaTask(this);
        agendaTask.execute();
        loadDialog.show();
        agendaListe = (ListView) view.getRootView().findViewById(R.id.cal_list);
    }

    @Override
    public void onAgendaAreGettingon(String json) throws JSONException {
        loadDialog.dismiss();
        JSONObject jsonObject = new JSONObject(json);
        String[] resultArray = makeArrayWeek(jsonObject);
        CalArrayAdapteur adapteur = new CalArrayAdapteur(getActivity(),resultArray.length,resultArray);
        agendaListe.setAdapter(adapteur);
    }

    private String[] makeArrayWeek(JSONObject jsonObject) throws JSONException {
        ArrayList<String> list = new ArrayList<String>();
        list.add(0, jsonObject.getString("mercredi").toString());
        list.add(1, jsonObject.getString("jeudi").toString());
        list.add(2, jsonObject.getString("vendredi").toString());
        Log.d(getClass().getName(),"Array is : "+list.toString());
        String[] listFinal = new String[list.size()];
        listFinal = list.toArray(listFinal);
        return listFinal;
    }
}
