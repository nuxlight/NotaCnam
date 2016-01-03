package trashcompagnie.notacnam;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import trashcompagnie.notacnam.Fragments.CalFragment;
import trashcompagnie.notacnam.Fragments.NotaFragment;

public class DashActivity extends ActionBarActivity {

    private NotaFragment notaFragment;
    private CalFragment agenFragment;
    private FragmentManager fm = getFragmentManager();
    private android.support.v7.app.ActionBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);

        //Configure Toolbar
        Toolbar toolbar =   (Toolbar) findViewById(R.id.dash_toolbar);
        setSupportActionBar(toolbar);
        bar = getSupportActionBar();
        bar.setTitle("NotaCnam - Notes");
        bar.setHomeButtonEnabled(true);

        Bundle argv = getIntent().getExtras();

        notaFragment = new NotaFragment();
        agenFragment = new CalFragment();
        if (findViewById(R.id.fragment_container) != null){
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.add(R.id.fragment_container, notaFragment);
            transaction.addToBackStack(null).commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dash_base_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Fragement manager
        FragmentTransaction transaction = fm.beginTransaction();
        switch (item.getItemId()) {
            case R.id.action_note:
                transaction.replace(R.id.fragment_container, notaFragment);
                transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                transaction.addToBackStack(null).commit();
                bar.setTitle("NotaCnam - Notes");
                return true;

            case R.id.action_agenda:
                transaction.replace(R.id.fragment_container, agenFragment);
                transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                transaction.addToBackStack(null).commit();
                bar.setTitle("NotaCnam - Agenda");
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
}
