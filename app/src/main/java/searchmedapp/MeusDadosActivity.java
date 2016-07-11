package searchmedapp;






import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import searchmedapp.webservices.dto.UsuarioDTO;
import searchmedapp.webservices.rest.UsuarioREST;


public class MeusDadosActivity extends AppCompatActivity{

    private static final String TAG = "MeusDadosActivity";

    private SharedPreferences pref;

    private EditText editNome;

    private EditText editEmail;

    private EditText editSenha;

    private EditText editCRM;

    private TextView lbCRM;

    private EditText editEndereco;

    private CheckBox chkPrestaServico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_dados);

        editNome = (EditText)findViewById(R.id.editNome);
        editEmail = (EditText)findViewById(R.id.editEmail);
        editSenha = (EditText)findViewById(R.id.editSenha);
        editEndereco = (EditText)findViewById(R.id.editEndereco);
        editCRM = (EditText)findViewById(R.id.editCRM);
        chkPrestaServico = (CheckBox)findViewById(R.id.chkPrestaServico);
        lbCRM = (TextView)findViewById(R.id.lbCRM);

        editEndereco.setEnabled(false);

        pref = getApplicationContext().getSharedPreferences("SearchMedPref", MODE_PRIVATE);

        meusDados();
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
            if(isValidaSenha()){
                criar();
            }
            return true;
        }else if (id == R.id.action_voltar) {
            Log.i(TAG, "R.id.action_voltar");

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onCheckPrestaServico(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.chkPrestaServico:
                if (checked){
                    //showNoticeDialog();
                }
                break;
        }
    }

    public void meusDados(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("SearchMedPref", MODE_PRIVATE);
        String user = pref.getString("key_user_id", "");

        if(user!=null){
            editNome.setText(pref.getString("key_user_nome", ""));
            editEmail.setText(pref.getString("key_user_email", ""));
            editEndereco.setText(pref.getString("key_user_endereco", ""));

            String tipo = pref.getString("key_user_tipo", "");

            if(tipo.equals("M")){
                chkPrestaServico.setChecked(true);
                editCRM.setText(pref.getString("key_user_crm", ""));
                lbCRM.setVisibility(View.VISIBLE);
                editCRM.setVisibility(View.VISIBLE);
            }else{
                chkPrestaServico.setChecked(false);
                lbCRM.setVisibility(View.GONE);
                editCRM.setVisibility(View.GONE);
            }

        }
    }
    public boolean isValidaSenha(){
        /*String senha = editSenha.getText().toString();
        String confirmaSenha = editConfirmaSenha.getText().toString();

        if(senha.equals("") || confirmaSenha.equals("")){
            Toast.makeText(getApplicationContext(), R.string.toast_senhas_vazias, Toast.LENGTH_SHORT).show();
            return false;
        }else  if(!senha.equals(confirmaSenha)){
            Toast.makeText(getApplicationContext(), R.string.toast_senhas_diferrentes, Toast.LENGTH_SHORT).show();
            return false;
        }
*/
        return true;
    }

    public void criar(){
    	UsuarioDTO retorno = null;
        String keyUserId = pref.getString("key_user_id", null);
        Long userId = keyUserId!=null ? Long.valueOf(keyUserId) : null;

        String tipo = "C";
        String crm = editCRM.getText().length()>0 ? editCRM.getText().toString() : null;

        if(chkPrestaServico.isChecked() == true){
            tipo = "M";
        }

        try {
            UsuarioREST rest = new UsuarioREST();
            retorno = rest.criar(userId,
                    editNome.getText().toString(),
                    editEmail.getText().toString(),                   
                    editEndereco.getText().toString(),
                    editSenha.getText().toString(),
                    tipo,
                    crm);
        }catch (Exception e){
        }

        abreMain(retorno);
    }

    public void abreMain(UsuarioDTO retorno){
        if(chkPrestaServico.isChecked()){
            Intent r = new Intent(this, MeusDadosEspecialidadeActivity.class);
            startActivity(r);
        }else if(retorno!=null && retorno.getEmail()!=null){
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

    public void showNoticeDialog() {
        // Create an instance of the dialog fragment and show it
        //DialogFragment dialog = new EspecialidadeDialogFragment();
        //dialog.show(getSupportFragmentManager(), "EspecialidadeDialogFragment");
    }

}
