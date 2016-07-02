package searchmedapp;






import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import searchmedapp.webservices.dto.UsuarioDTO;
import searchmedapp.webservices.rest.UsuarioREST;


public class PrimeiroAcessoActivity extends AppCompatActivity{

    private static final String TAG = "PrimeiroAcessoActivity";

    private SharedPreferences pref;

    private EditText editNome;

    private EditText editEmail;

    private EditText editSenha;

    private TextView lbCRM;

    private EditText editCRM;

    private EditText editEndereco;

    private CheckBox chkPrestaServico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primeiro_acesso);

        editNome = (EditText)findViewById(R.id.editNome);
        editEmail = (EditText)findViewById(R.id.editEmail);
        editSenha = (EditText)findViewById(R.id.editSenha);
        editEndereco = (EditText)findViewById(R.id.editEndereco);
        chkPrestaServico = (CheckBox)findViewById(R.id.chkPrestaServico);
        lbCRM = (TextView)findViewById(R.id.lbCRM);
        editCRM = (EditText)findViewById(R.id.editCRM);

        lbCRM.setVisibility(View.GONE);
        editCRM.setVisibility(View.GONE);
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
                    lbCRM.setVisibility(View.VISIBLE);
                    editCRM.setVisibility(View.VISIBLE);
                    editCRM.requestFocus();
                }else {
                    lbCRM.setVisibility(View.GONE);
                    editCRM.setVisibility(View.GONE);
                }
                break;
        }
    }

    public boolean isValidaSenha(){
        String senha = editSenha.getText().toString();
        //String confirmaSenha = editConfirmaSenha.getText().toString();

       /* if(senha.equals("") || confirmaSenha.equals("")){
            Toast.makeText(getApplicationContext(), R.string.toast_senhas_vazias, Toast.LENGTH_SHORT).show();
            return false;
        }else  if(!senha.equals(confirmaSenha)){
            Toast.makeText(getApplicationContext(), R.string.toast_senhas_diferrentes, Toast.LENGTH_SHORT).show();
            return false;
        }*/

        return true;
    }

    public void criar(){
        pref = getApplicationContext().getSharedPreferences("SearchMedPref", MODE_PRIVATE);

        if(editNome.getText().length() == 0){
            Toast.makeText(getApplicationContext(), R.string.toast_informe_nome, Toast.LENGTH_LONG).show();
        }else if (editEmail.getText().length() == 0){
            Toast.makeText(getApplicationContext(), R.string.toast_informe_email, Toast.LENGTH_LONG).show();
        }else  if(editEndereco.getText().length() == 0){
            Toast.makeText(getApplicationContext(), R.string.toast_informe_endereco, Toast.LENGTH_LONG).show();
        }else if (editSenha.getText().length() == 0){
            Toast.makeText(getApplicationContext(), R.string.toast_informe_senha, Toast.LENGTH_LONG).show();
        }else{
            if(chkPrestaServico.isChecked() == true){
                if (editCRM.getText().length() == 0){
                    Toast.makeText(getApplicationContext(), R.string.toast_informe_crm, Toast.LENGTH_LONG).show();
                }else {
                    salvar();
                }
            }else {
                salvar();
            }
        }
    }

    public void salvar(){
        UsuarioDTO retorno = null;
        String keyUserId = pref.getString("key_user_id", null);
        Long userId = keyUserId!=null ? Long.valueOf(keyUserId) : null;
        String tipo = "C";
        String crm = editCRM.getText().length()>0 ? editCRM.getText().toString() : null;

        if(chkPrestaServico.isChecked() == true) {
            tipo = "M";
        }

        try {
            UsuarioREST rest = new UsuarioREST();
            retorno = rest.criar(userId,
                    editNome.getText().toString(),
                    editEmail.getText().toString(),
                    editSenha.getText().toString(),
                    editEndereco.getText().toString(),
                    tipo,
                    crm);
            abreMain(retorno);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), R.string.toast_erro_geral, Toast.LENGTH_LONG).show();
        }
    }

    public void abreMain(UsuarioDTO retorno){
        if(chkPrestaServico.isChecked()){
            Intent r = new Intent(this, MeusDadosEspecialidadeActivity.class);
            startActivity(r);
        }else if(retorno!=null && retorno.getId()!=null){
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("key_user_id", retorno.getId().toString());
            editor.putString("key_user_email", retorno.getEmail());
            editor.putString("key_user_nome", retorno.getNome());
            editor.putString("key_user_endereco", retorno.getEndereco());
            editor.putString("key_user_tipo", retorno.getTipo());
            if(retorno.getMedico()!=null){
                editor.putString("key_user_crm", retorno.getMedico().getCrm());
                editor.putString("key_user_medico_id", ""+retorno.getMedico().getId());
            }
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
