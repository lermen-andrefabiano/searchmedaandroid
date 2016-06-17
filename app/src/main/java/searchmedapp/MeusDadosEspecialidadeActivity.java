package searchmedapp;

import java.util.List;



import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;

import searchmedapp.adapter.MeusDadosAdapter;
import searchmedapp.webservices.dto.MedicoEspecialidadeDTO;
import searchmedapp.webservices.rest.EspecialidadeREST;


public class MeusDadosEspecialidadeActivity extends AppCompatActivity {

    private SharedPreferences pref;

    private List<MedicoEspecialidadeDTO> especialidades;

    private MedicoEspecialidadeDTO especialidadeSel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_dados_especialidade);

        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec specEspecialidadePrestador = tabHost.newTabSpec(getString(R.string.label_especialidade_listar));
        specEspecialidadePrestador.setContent(R.id.tabEspecialidadePrestador);
        specEspecialidadePrestador.setIndicator(getString(R.string.label_especialidade_listar));

        TabHost.TabSpec specLstEspecialidades = tabHost.newTabSpec(getString(R.string.label_especialidade_add));
        specLstEspecialidades.setContent(R.id.tabLstEspecialidades);
        specLstEspecialidades.setIndicator(getString(R.string.label_especialidade_add));

        tabHost.addTab(specEspecialidadePrestador);
        tabHost.addTab(specLstEspecialidades);

        pref = getApplicationContext().getSharedPreferences("SearchMedPref", MODE_PRIVATE);

        listarPorPrestador();
    }

    public void listarPorPrestador(){
        String keyUserId = pref.getString("key_user_id", null);
        Long userId = keyUserId!=null ? Long.valueOf(keyUserId) : null;

        try {
            EspecialidadeREST rest = new EspecialidadeREST();
            especialidades = rest.getEspecialidadesMedicas(userId);
        }catch (Exception e){
        }

        if(especialidades!=null){
            ListView listEspecialidadePrestador = (ListView) findViewById(R.id.listEspecialidadePrestador);

            MeusDadosAdapter adapter = new MeusDadosAdapter(this, R.layout.activity_meus_dados_especialidade_item, especialidades);
            listEspecialidadePrestador.setAdapter(adapter);
            listEspecialidadePrestador.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    especialidadeSel = (MedicoEspecialidadeDTO)parent.getItemAtPosition(position);
                }
            });
        }   }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        //ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);
        return true;
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
}
