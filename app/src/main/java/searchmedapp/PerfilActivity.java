package searchmedapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class PerfilActivity extends AppCompatActivity {

    private static final String TAG = "PerfilActivity";

    private static final String ARG_SECTION_NUMBER = "section_number";

    private SharedPreferences pref;

    public PerfilActivity() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_perfil);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        pref = this.getSharedPreferences("SearchMedPref", Context.MODE_PRIVATE);

        this.setUserCabecalho();

        this.openLstMeusDados();

        this.openLstMais();

        Button btnLogon = (Button)findViewById(R.id.btnLogon);
        btnLogon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logon();
            }
        });
    }

    private void openLstMeusDados(){
        ListView lstMeusDados = (ListView) findViewById(R.id.lstMeusDados);
        String tipo = pref.getString("key_user_tipo", null);
        String[] menuArray;
        //ViewGroup.LayoutParams params = lstMeusDados.getLayoutParams();

        if(tipo==null || tipo.equals("C")){
            menuArray =  new String[]{getString(R.string.label_dados_pessoais)};
            //params.height = 40;
        }else{
            menuArray = new String[]{
                    getString(R.string.label_dados_pessoais),
                    getString(R.string.label_especialidades),
                    getString(R.string.label_convenio),
                    getString(R.string.label_horario),
            };
            //params.height = 160;
        }

        // lstMeusDados.setLayoutParams(params);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.activity_adpater_item,
                R.id.textoAdp,
                menuArray);

        lstMeusDados.setAdapter(adapter);
        lstMeusDados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent i = new Intent(getApplicationContext(), MeusDadosActivity.class);
                        startActivity(i);
                        break;
                    case 1:
                        Intent m = new Intent(getApplicationContext(), MeusDadosEspecialidadeActivity.class);
                        startActivity(m);
                        break;
                    case 2:
                        Intent c = new Intent(getApplicationContext(), MeusDadosConvenioActivity.class);
                        startActivity(c);
                        break;
                    case 3:
                        Intent h = new Intent(getApplicationContext(), MeusDadosHorarioActivity.class);
                        startActivity(h);
                        break;
                }
            }
        });
    }

    private void openLstMais(){
        final ListView lstMais = (ListView) findViewById(R.id.lstMais);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.activity_adpater_item,
                R.id.textoAdp,
                new String[]{
                        getString(R.string.label_redefinir_senha)
                });

        lstMais.setAdapter(adapter);
        lstMais.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0 :
                        Intent r = new Intent(getApplicationContext(), RedefinirSenhaActivity.class);
                        startActivity(r);
                        break;
                }
            }
        });

    }

    public void setUserCabecalho(){
        pref = getApplicationContext().getSharedPreferences("SearchMedPref", Context.MODE_PRIVATE);
        String userNome = pref.getString("key_user_nome", "");

        TextView textUserPerfil = (TextView) findViewById(R.id.textUserPerfil);
        textUserPerfil.setText(userNome);
    }

    public void logon(){
        pref = getApplicationContext().getSharedPreferences("SearchMedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("key_user_id", null);
        editor.putString("key_user", null);
        editor.putString("key_user_email", null);
        editor.putString("key_user_nome", null);
        editor.putString("key_user_endereco", null);
        editor.putString("key_user_prestador", null);
        editor.putString("key_user_tipo", null);
        editor.putString("key_user_medico_id", null);
        editor.putString("key_user_favorito", null);
        editor.commit();

        Intent r = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(r);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        ListView lstMeusDados = (ListView) findViewById(R.id.lstMeusDados);
        String tipo = pref.getString("key_user_tipo", null);
        ViewGroup.LayoutParams params = lstMeusDados.getLayoutParams();

        if (tipo == null || tipo.equals("C")) {
            params.height = 160;
            Log.i(TAG, " params.height = 40");
        } else {
            params.height = 480;
            Log.i(TAG, " params.height = 160");
        }

        lstMeusDados.setLayoutParams(params);
    }
}
