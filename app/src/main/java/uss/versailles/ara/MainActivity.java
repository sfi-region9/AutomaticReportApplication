package uss.versailles.ara;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import fr.colin.arssdk.ARSdk;
import fr.colin.arssdk.objects.Vessel;
import uss.versailles.ara.fragments.LoginFragment;
import uss.versailles.ara.fragments.MainFragment;
import uss.versailles.ara.fragments.RegisterFragment;
import uss.versailles.ara.fragments.ReportFragment;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private static Fragment fragmentRegister;
    private static Fragment loginFragment;
    public static ArrayList<Vessel> allVessels = new ArrayList<>();
    private static Fragment mainFragment;
    private static Fragment reportFragment;
    public static final int FRAG_REG = 0;
    private static UserLocalStore store;


    public static NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        store = new UserLocalStore(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Any problems ? A question ? Contact me by mail ( contact@nwa2coco.fr ) or Messenger !", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //625 ms
        showFirstFragment();
        new FetchVessel().execute();

        //725 ms

        //1023ms
    }


    private void showFirstFragment() {
        Fragment visibleFragment = getSupportFragmentManager().findFragmentById(R.id.activity_main_frame_layout);
        if (visibleFragment == null) {
            showMainFrag(getSupportFragmentManager());

            this.navigationView.getMenu().getItem(0).setChecked(true);

        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_register) {
            showRegisterFrag(getSupportFragmentManager());
            this.navigationView.getMenu().getItem(2).setChecked(true);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static UserLocalStore getStore() {
        return store;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.report) {
            showReportFrag(getSupportFragmentManager());
        } else if (id == R.id.register) {
            showRegisterFrag(getSupportFragmentManager());
        } else if (id == R.id.main) {
            showMainFrag(getSupportFragmentManager());
        } else if (id == R.id.login) {
            showLoginFrag(getSupportFragmentManager());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public static void showRegisterFrag(FragmentManager manager) {

        if (fragmentRegister == null) fragmentRegister = RegisterFragment.newInstance();
        startTransactionFragment(fragmentRegister, manager);

    }

    public static void showMainFrag(FragmentManager manager) {
        if (mainFragment == null) mainFragment = MainFragment.newInstance();
        startTransactionFragment(mainFragment, manager);
    }

    public static void showLoginFrag(FragmentManager manager) {
        if (loginFragment == null) loginFragment = LoginFragment.newInstance();
        startTransactionFragment(loginFragment, manager);
    }


    public static void showReportFrag(FragmentManager manager) {
        if (reportFragment == null) reportFragment = ReportFragment.newInstance();
        startTransactionFragment(reportFragment, manager);
    }

    public static void startTransactionFragment(Fragment fragment, FragmentManager manager) {
        if (!fragment.isVisible()) {
            manager.beginTransaction()
                    .replace(R.id.activity_main_frame_layout, fragment).commit();
        }
    }



    static class FetchVessel extends AsyncTask<Void, Void, ArrayList<Vessel>> {

        @Override
        protected ArrayList<Vessel> doInBackground(Void... voids) {
            try {
                ArrayList<Vessel> v;
                v = ARSdk.DEFAULT_INSTANCE.allVessels();
                MainActivity.allVessels = v;
                return ARSdk.DEFAULT_INSTANCE.allVessels();
            } catch (IOException e) {
                return new ArrayList<>();
            }
        }
    }

}
