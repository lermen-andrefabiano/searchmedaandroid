package searchmedapp;






import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import searchmedapp.util.GPSTracker;
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

    private GPSTracker gps;

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

        editEndereco.setEnabled(false);
        lbCRM.setVisibility(View.GONE);
        editCRM.setVisibility(View.GONE);

        getGps();

        editEmail.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(editEndereco.getText().length() == 0){
                    getGps();
                }
                return false;
            }
        });
    }

    private void getGps(){
        gps = new GPSTracker(this);
        // check if GPS enabled
        if(gps.canGetLocation()){
            final String localy =  gps.getLocality();
            editEndereco.setText(localy);
        }else{
            gps.showSettingsAlert();
        }
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
        //}else  if(editEndereco.getText().length() == 0){
        //    Toast.makeText(getApplicationContext(), R.string.toast_informe_endereco, Toast.LENGTH_LONG).show();
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

        String senha = editSenha.getText().toString();

        //Log.i(TAG, "senha: "+senha);
        senha = md5(senha);
        //Log.i(TAG, "senha md5: "+senha);

        try {
            UsuarioREST rest = new UsuarioREST();
            retorno = rest.criar(userId,
                    editNome.getText().toString(),
                    editEmail.getText().toString(),
                    senha,
                    editEndereco.getText().toString(),
                    tipo,
                    crm,
                    gps.getLatitude(),
                    gps.getLongitude());
            abreMain(retorno);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), R.string.toast_erro_geral, Toast.LENGTH_LONG).show();
        }

    }

    private static String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void abreMain(UsuarioDTO retorno){
        if(retorno!=null && retorno.getId()!=null){
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("key_user_id", retorno.getId().toString());
            editor.putString("key_user_email", retorno.getEmail());
            editor.putString("key_user_nome", retorno.getNome());
            editor.putString("key_user_endereco", retorno.getEndereco());
            editor.putString("key_user_tipo", retorno.getTipo());
            editor.putString("key_latitude", retorno.getLongitude()+"");
            editor.putString("key_longitude", retorno.getLatitude()+"");
            if(retorno.getMedico()!=null){
                editor.putString("key_user_crm", retorno.getMedico().getCrm());
                editor.putString("key_user_medico_id", ""+retorno.getMedico().getId());
            }
            editor.commit();

            if(chkPrestaServico.isChecked()){
                Intent r = new Intent(this, MeusDadosEspecialidadeActivity.class);
                startActivity(r);
            }else{
                Intent r = new Intent(this, MainActivity.class);
                startActivity(r);
            }
        }
    }

    public void showNoticeDialog() {
        // Create an instance of the dialog fragment and show it
        //DialogFragment dialog = new EspecialidadeDialogFragment();
        //dialog.show(getSupportFragmentManager(), "EspecialidadeDialogFragment");
    }

}
