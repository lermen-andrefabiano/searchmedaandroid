package searchmedapp;



import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import searchmedapp.webservices.dto.UsuarioDTO;
import searchmedapp.webservices.rest.UsuarioREST;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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

    public void login(View view){
        EditText editLogin = (EditText)findViewById(R.id.editLogin);
        EditText editSenha = (EditText)findViewById(R.id.editSenha);

        if(editLogin.getText().length() == 0){
            Toast.makeText(getApplicationContext(), R.string.toast_informe_email, Toast.LENGTH_LONG).show();
        }else if (editSenha.getText().length() == 0){
            Toast.makeText(getApplicationContext(), R.string.toast_informe_senha, Toast.LENGTH_LONG).show();
        }else{
            UsuarioDTO retorno = null;
            carregamento();
            try {
                UsuarioREST rest = new UsuarioREST();
                retorno = rest.login(editLogin.getText().toString(), editSenha.getText().toString());
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), R.string.toast_erro_geral, Toast.LENGTH_LONG).show();
            }

            abreMain(retorno);
        }
    }

    public void abreMain(UsuarioDTO retorno){
        if(retorno!=null && retorno.getId()!=null){
            SharedPreferences pref = getApplicationContext().getSharedPreferences("SearchMedPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("key_user_id", retorno.getId().toString());
            editor.putString("key_user_email", retorno.getEmail());
            editor.putString("key_user_nome", retorno.getNome());
            editor.putString("key_user_endereco", retorno.getEndereco());
            editor.putString("key_user_tipo", retorno.getTipo());

            Gson gson = new Gson();

            String jsonFavorito = gson.toJson(retorno.getFavoritos());
            editor.putString("key_user_favorito", jsonFavorito);

            if(retorno.getMedico()!=null){
                editor.putString("key_user_crm", retorno.getMedico().getCrm());
                editor.putString("key_user_medico_id", ""+retorno.getMedico().getId());
            }
            editor.commit();

            Intent r = new Intent(this, MainActivity.class);
            startActivity(r);
        }else{
            Toast.makeText(getApplicationContext(), R.string.toast_login_invalido, Toast.LENGTH_LONG).show();
        }

    }
}
