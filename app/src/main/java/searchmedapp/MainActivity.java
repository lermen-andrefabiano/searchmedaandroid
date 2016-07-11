package searchmedapp;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;



public class MainActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private static final String TAG = "MainActivity";

    private SharedPreferences pref;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //createNotification();

        if(isLogado()){
            this.openNavigationDrawer();
        }else{
            Intent r = new Intent(this, BoasVindasActivity.class);
            startActivity(r);
        }
    }

    public void createNotification() {
        // Prepare intent which is triggered if the
        // notification is selected
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

        // Build notification
        // Actions are just fake
        Notification noti = new Notification.Builder(this)
                .setContentTitle("New mail from " + "test@gmail.com")
                .setContentText("Subject").setSmallIcon(R.drawable.ic_import_contacts_black_18dp)
                .setContentIntent(pIntent)
                .addAction(R.drawable.ic_import_contacts_black_18dp, "Call", pIntent)
                .addAction(R.drawable.ic_import_contacts_black_18dp, "More", pIntent)
                .addAction(R.drawable.ic_import_contacts_black_18dp, "And more", pIntent).build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // hide the notification after its selected
        noti.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, noti);

    }

    private boolean isLogado(){
        pref = getApplicationContext().getSharedPreferences("SearchMedPref", MODE_PRIVATE);
        String user = pref.getString("key_user_id", null);

        return user!=null;
    }

    private void openNavigationDrawer(){
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        Log.i(TAG, "mTitle "+ mTitle);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        Log.i(TAG, "onNavigationDrawerItemSelected "+ position);
        pref = getApplicationContext().getSharedPreferences("SearchMedPref", MODE_PRIVATE);
        String tipo = pref.getString("key_user_tipo", null);

        // update the main content by replacing fragments
        Fragment fragment = null;

        Log.i(TAG, "Tipo usuario "+ tipo);
        if(tipo==null || tipo.equals("C")){
            switch(position) {
                case 0:
                    fragment = ConsultaFragment.newInstance(position + 1);
                    break;
                case 1:
                    fragment = ConsultaPassadasFragment.newInstance(position + 1);
                    break;
                case 2:
                    fragment = ConsultaClassificacaoFragment.newInstance(position + 1);
                    break;
                case 3:
                    fragment = FavoritoFragment.newInstance(position + 1);
                    break;
                case 4:
                    //fragment = PerfilActivity.newInstance(position + 1);
                    Intent r = new Intent(getApplicationContext(), PerfilActivity.class);
                    startActivity(r);
                    break;

            }
        }else{
            switch(position) {
                case 0:
                    fragment = ConsultaAbertaFragment.newInstance(position + 1);
                    break;
                case 1:
                    fragment = ConsultaAgendaFragment.newInstance(position + 1);
                    break;
                case 2:
                    //fragment = PerfilActivity.newInstance(position + 1);
                    Intent r = new Intent(getApplicationContext(), PerfilActivity.class);
                    startActivity(r);
                    break;
            }
        }

        onSectionAttached(position+1);
        restoreActionBar();

        if(fragment!=null){
            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
        }

    }

    public void onSectionAttached(int number) {
        pref = getApplicationContext().getSharedPreferences("SearchMedPref", MODE_PRIVATE);
        String tipo = pref.getString("key_user_tipo", null);
        if(tipo==null || tipo.equals("C")){
            switch (number) {
                case 1:
                    mTitle = getString(R.string.action_encontre_medico);
                    break;
                case 2:
                    mTitle = getString(R.string.action_consultas_passadas);
                    break;
                case 3:
                    mTitle = getString(R.string.action_recomendacao);
                    break;
                case 4:
                    mTitle = getString(R.string.action_medicos_favoritos);
                    break;
                case 5:
                    mTitle = getString(R.string.action_perfil);
                    break;
            }
        }else {
            switch (number) {
                case 1:
                    mTitle = getString(R.string.action_consultas_abertas);
                    break;
                case 2:
                    mTitle = getString(R.string.action_consultas_agendadas);
                    break;
                case 3:
                    mTitle = getString(R.string.action_perfil);
                    break;

            }
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(isLogado()){
            if (!mNavigationDrawerFragment.isDrawerOpen()) {
                // Only show items in the action bar relevant to this screen
                // if the drawer is not showing. Otherwise, let the drawer
                // decide what to show in the action bar.
                getMenuInflater().inflate(R.menu.global, menu);
                restoreActionBar();
                return true;
            }
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static Fragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
