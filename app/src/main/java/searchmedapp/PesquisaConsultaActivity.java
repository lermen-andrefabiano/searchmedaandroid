package searchmedapp;



import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import searchmedapp.adapter.MedicoHorarioAdapter;
import searchmedapp.adapter.PesquisaConsultaAdapter;
import searchmedapp.webservices.dto.MedicoDTO;
import searchmedapp.webservices.dto.MedicoHorarioDTO;
import searchmedapp.webservices.rest.ConsultaREST;
import searchmedapp.webservices.rest.EspecialidadeREST;


public class PesquisaConsultaActivity extends AppCompatActivity {

    private static final String TAG = "PesquisaConsultaActivity";

    private List<MedicoDTO> especialidadesMedicas = null;

    private MedicoDTO medicoSel;

    private MedicoHorarioDTO medicoHorarioSel;

    private String convenio;

    private Long especialidadeId;

    private String especialidade;

    private Button btnFavorito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa_consulta);

        Bundle bundle = getIntent().getExtras();
        if(bundle.getString("convenio")!= null&&bundle.getString("convenio")!= null)
        {
            convenio = bundle.getString("convenio");
            especialidadeId = bundle.getLong("especialidadeId");
            especialidade = bundle.getString("especialidade");
            openListaMedicos();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
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

    private boolean carregamento() {
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle(getString(R.string.load_carregando));
        progress.setMessage(getString(R.string.load_aguarde));
        progress.show();
        new Thread() {
            public void run() {
                try{
                    // just doing some long operation
                    sleep(20000);
                } catch (Exception e) {  }
                progress.dismiss();
            }
        }.start();
        return true;
    }

    private void openListaMedicos(){
       //Log.i(TAG, "openListaMedicos");

        EspecialidadeREST especialidadeREST = new EspecialidadeREST();
        try {
            if(especialidadesMedicas==null){
                especialidadesMedicas = especialidadeREST.getMedicoEspecialidades(convenio, especialidadeId);
            }
        }catch (Exception e){
        }

        if(especialidadesMedicas!=null) {
            ListView listPrestadores = (ListView) findViewById(R.id.listPesquisaMedicos);

            PesquisaConsultaAdapter adapter = new PesquisaConsultaAdapter(this, R.layout.activity_pesquisa_consulta_item, especialidadesMedicas);

            listPrestadores.setAdapter(adapter);
            listPrestadores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    medicoSel = (MedicoDTO) parent.getItemAtPosition(position);
                    abrirPopUpConsulta();
                }
            });
        }
    }

    private void abrirPopUpConsulta(){
        LayoutInflater li = LayoutInflater.from(this);
        View view = li.inflate(R.layout.activity_abrir_consulta, null);

        ListView lv = (ListView ) view.findViewById(R.id.listHorario);
        final MedicoHorarioAdapter adapter = new MedicoHorarioAdapter(this, R.layout.activity_adpater_horario_item, medicoSel.getHorarios());
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                medicoHorarioSel = (MedicoHorarioDTO) parent.getItemAtPosition(position);
            }
        });

        TextView textMed1 = (TextView) view.findViewById(R.id.textMed1);
        TextView textMed2 = (TextView) view.findViewById(R.id.textMed2);
        TextView textMed3 = (TextView) view.findViewById(R.id.textMed3);
        TextView textMed4 = (TextView) view.findViewById(R.id.textMed4);

        textMed1.setText(medicoSel.getMedicoNome());
        textMed2.setText("CRM: " + medicoSel.getCrm());
        textMed3.setText("Endereço: " + medicoSel.getMedicoEndereco());
        textMed4.setText(especialidade);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(view);

        final AlertDialog alertDialog = alertDialogBuilder.create();

        Button btnAbrirConsulta = (Button) view.findViewById(R.id.btnAbrirConsulta);
        btnAbrirConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirConsulta();
            }
        });

        Button btnCancelarConsulta = (Button) view.findViewById(R.id.btnCancelarConsulta);
        btnCancelarConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        btnFavorito = (Button) view.findViewById(R.id.btnFavorito);
        btnFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favorito();
            }
        });

        alertDialog.show();
    }

    public void favorito(){
        SharedPreferences pref = this.getSharedPreferences("SearchMedPref", Context.MODE_PRIVATE);
        String user = pref.getString("key_user_id", null);

        try {
            ConsultaREST rest = new ConsultaREST();
            boolean r = rest.favorito(Long.valueOf(user), medicoSel.getId());
            if(r){
                Toast.makeText(this, R.string.toast_favorito_adicionado, Toast.LENGTH_SHORT).show();
                btnFavorito.setText("Favorito");
            }else {
                Toast.makeText(this, R.string.toast_favorito_removido, Toast.LENGTH_SHORT).show();
                btnFavorito.setText("Desfavoritar");
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), R.string.toast_erro_geral, Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public void abrirConsulta(){
        SharedPreferences pref = this.getSharedPreferences("SearchMedPref", Context.MODE_PRIVATE);
        String user = pref.getString("key_user_id", null);

        try {
            ConsultaREST rest = new ConsultaREST();
            boolean r = rest.abrir(Long.valueOf(user), medicoSel.getId(), especialidadeId, medicoHorarioSel.getId());
            if(r){
                Toast.makeText(this, R.string.toast_consulta_aberto, Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, R.string.toast_consulta_erro, Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), R.string.toast_erro_geral, Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

}
