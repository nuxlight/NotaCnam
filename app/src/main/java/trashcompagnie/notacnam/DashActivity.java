package trashcompagnie.notacnam;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toolbar;

import trashcompagnie.notacnam.Fragments.NotaFragment;

public class DashActivity extends AppCompatActivity implements ActionBar.TabListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Create a tab navigation bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.nota_tabBar);
        setActionBar(toolbar);

        setContentView(R.layout.activity_dash);
        Bundle argv = getIntent().getExtras();

        //Fragement manager
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        NotaFragment notaFragment = new NotaFragment();
        fragmentTransaction.replace(R.id.fragment_nota, notaFragment);
        fragmentTransaction.show(notaFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }
}
