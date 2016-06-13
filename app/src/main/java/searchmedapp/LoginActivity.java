package searchmedapp;



import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

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
        getMenuInflater().inflate(R.menu.menu_main, menu);

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
            login();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void login(){
        EditText editLogin = (EditText)findViewById(R.id.editLogin);
        EditText editSenha = (EditText)findViewById(R.id.editSenha);
        UsuarioDTO retorno = null;
        String login = editLogin.getText().toString();
        try {
            UsuarioREST rest = new UsuarioREST();
            retorno = rest.login(login, editSenha.getText().toString());
        }catch (Exception e){
        }

        Intent r = new Intent(this, MainActivity.class);
        startActivity(r);

        //abreMain(retorno);

    }

    public void abreMain(UsuarioDTO retorno){
        if(retorno!=null && retorno.getEmail()!=null){
            SharedPreferences pref = getApplicationContext().getSharedPreferences("HomeHelpPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("key_user_id", retorno.getId().toString());
            editor.putString("key_user_email", retorno.getEmail());
            editor.putString("key_user_nome", retorno.getNome());
            editor.putString("key_user_endereco", retorno.getEndereco());
            editor.commit();

            Intent r = new Intent(this, MainActivity.class);
            startActivity(r);
        }
    }
}
