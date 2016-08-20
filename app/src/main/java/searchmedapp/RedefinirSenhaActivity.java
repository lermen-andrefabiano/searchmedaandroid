package searchmedapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import searchmedapp.webservices.rest.UsuarioREST;

public class RedefinirSenhaActivity extends AppCompatActivity {

	private SharedPreferences pref;

	private EditText editSenhaAtual;

	private EditText editSenhaNova;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_redefinir_senha);

		pref = getApplicationContext().getSharedPreferences("SearchMedPref", MODE_PRIVATE);

		editSenhaAtual = (EditText) findViewById(R.id.editSenhaAtual);
		editSenhaNova = (EditText) findViewById(R.id.editSenhaNova);
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

		// noinspection SimplifiableIfStatement
		if (id == R.id.action_save) {
			redefinirSenha();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void redefinirSenha() {
		String usuarioId = pref.getString("key_user_id", null);
		String senha = editSenhaAtual.getText().toString();
		String novaSenha = editSenhaNova.getText().toString();

		senha = md5(senha);
		novaSenha = md5(novaSenha);

		try {
			UsuarioREST rest = new UsuarioREST();
			boolean result = rest.trocarSenha(Long.valueOf(usuarioId), senha, novaSenha);
			if (result) {
				Toast.makeText(this, "Senha alterada com sucesso.", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "Erro na tentativa de troca da senha.", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
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

}
