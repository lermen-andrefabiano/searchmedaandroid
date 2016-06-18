package searchmedapp;



import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import searchmedapp.adapter.PesquisaConsultaAdapter;
import searchmedapp.webservices.dto.MedicoDTO;
import searchmedapp.webservices.dto.MedicoEspecialidadeDTO;
import searchmedapp.webservices.rest.ConsultaREST;
import searchmedapp.webservices.rest.EspecialidadeREST;


public class PesquisaConsultaActivity extends AppCompatActivity {

    private static final String TAG = "PesquisaConsultaActivity";

    private List<MedicoDTO> especialidadesMedicas = null;

    private MedicoDTO medicoEspecialidadeSel;

    private String convenio;

    private Long especialidadeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa_consulta);

        Bundle bundle = getIntent().getExtras();
        if(bundle.getString("convenio")!= null&&bundle.getString("convenio")!= null)
        {
            convenio = bundle.getString("convenio");
            especialidadeId = bundle.getLong("especialidadeId");
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
                    medicoEspecialidadeSel = (MedicoDTO) parent.getItemAtPosition(position);
                    abrirPopUpConsulta();
                }
            });
        }
    }

    private void abrirPopUpConsulta(){
        LayoutInflater li = LayoutInflater.from(this);
        View view = li.inflate(R.layout.activity_abrir_consulta, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(view);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(R.string.label_abrir_consulta,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                abrirConsulta();
                            }})
                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void abrirConsulta(){
        SharedPreferences pref = this.getSharedPreferences("SearchMedPref", Context.MODE_PRIVATE);
        String user = pref.getString("key_user_id", "");

        try {
            ConsultaREST rest = new ConsultaREST();
            rest.abrir(Long.valueOf(user), medicoEspecialidadeSel.getId(), especialidadeId);
        }catch (Exception e){
            e.printStackTrace();
        }
        Toast.makeText(this, R.string.toast_consulta_aberto, Toast.LENGTH_SHORT).show();
    }

}
