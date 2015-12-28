package trashcompagnie.notacnam.Fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.json.simple.JSONArray;

import java.util.ArrayList;

import trashcompagnie.notacnam.AuthClass;
import trashcompagnie.notacnam.AuthClassLisner;
import trashcompagnie.notacnam.NotaArrayAdapteur;
import trashcompagnie.notacnam.NotaPreferences;
import trashcompagnie.notacnam.R;

public class NotaFragment extends Fragment implements AuthClassLisner, SwipeRefreshLayout.OnRefreshListener {
    private ListView notesListe;
    private ProgressDialog loadDialog;
    private SwipeRefreshLayout swipeRefreshLayout;
    private AuthClass authTools;
    private String[] userPrefs;
    private FragmentActivity faActivity;
    private View faLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //faActivity = (FragmentActivity) super.getActivity();
        return inflater.inflate(R.layout.fragment_nota, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NotaPreferences notaPreferences = new NotaPreferences(getActivity());
        userPrefs = notaPreferences.getUserPrefs();
        //Create Progress dialog
        loadDialog = new ProgressDialog(getActivity());
        loadDialog.setMessage("Chargement des notes en cours...");
        loadDialog.setCancelable(false);
        authTools = new AuthClass(this);
        String[] parms = userPrefs;
        authTools.execute(parms);
        loadDialog.show();
        notesListe = (ListView) view.getRootView().findViewById(R.id.liste_notes);
        swipeRefreshLayout = (SwipeRefreshLayout) view.getRootView().findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        authTools = new AuthClass(this);
        String[] parms = userPrefs;
        authTools.execute(parms);
    }

    @Override
    public void onNoteAreGettingon(JSONArray resultJson) {
        ArrayList<String> newArray = toJsonList(resultJson);
        Log.i(getClass().getName(), "it's back : " + newArray);
        String[] updateNewArray = new String[newArray.size()];
        updateNewArray = newArray.toArray(updateNewArray);
        Log.i(getClass().getName(), "it's back lenght : " + updateNewArray.length);
        ListAdapter arrayAdapter = new NotaArrayAdapteur(getActivity(),updateNewArray);
        notesListe.setAdapter(arrayAdapter);
        if (loadDialog.isShowing()){
            loadDialog.dismiss();
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    private ArrayList<String> toJsonList(JSONArray array){
        ArrayList<String> list = new ArrayList<String>();
        JSONArray jsonArray = (JSONArray)array;
        if (jsonArray != null) {
            for (int i=0;i<jsonArray.size();i++){
                if(!jsonArray.get(i).toString().replace('"',' ').equals("[ AnnéeuniversitaireCode-Unité ]")){
                    list.add(jsonArray.get(i).toString());
                }
            }
        }
        return list;
    }
}
