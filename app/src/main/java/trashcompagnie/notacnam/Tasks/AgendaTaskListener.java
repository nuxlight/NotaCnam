package trashcompagnie.notacnam.Tasks;

import org.json.JSONException;

/**
 * Created by thibaud on 03/01/16.
 */
public interface AgendaTaskListener {
    void onAgendaAreGettingon(String json) throws JSONException;
}
