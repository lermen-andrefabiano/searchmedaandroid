package searchmedapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import searchmedapp.webservices.dto.InfoSalvarHorarioDTO;
import searchmedapp.webservices.dto.MedicoHorarioDTO;
import searchmedapp.webservices.rest.MedicoREST;

public class MeusDadosHorarioActivity extends AppCompatActivity {

    private static final String TAG = "MeusDadosHorarioActivity";

    private SharedPreferences pref;

    private List<MedicoHorarioDTO> horarios = null;

    private List<InfoSalvarHorarioDTO> info;

    private CheckBox checkBoxSegundaChegada;
    private CheckBox checkBoxTercaChegada;
    private CheckBox checkBoxQuartaChegada;
    private CheckBox checkBoxQuintaChegada;
    private CheckBox checkBoxSextaChegada;

    private CheckBox checkBoxSegunda;
    private CheckBox checkBoxTerca;
    private CheckBox checkBoxQuarta;
    private CheckBox checkBoxQuinta;
    private CheckBox checkBoxSexta;
    private CheckBox checkBoxRepetirHorario;

    private EditText editTextSegundaInicio;
    private EditText editTextSegundaFim;
    private EditText editTextTercaInicio;
    private EditText editTextTercaFim;
    private EditText editTextQuartaInicio;
    private EditText editTextQuartaFim;
    private EditText editTextQuintaInicio;
    private EditText editTextQuintaFim;
    private EditText editTextSextaInicio;
    private EditText editTextSextaFim;

    private TextView segundaId;
    private TextView tercaId;
    private TextView quartaId;
    private TextView quintaId;
    private TextView sextaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_dados_horario);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        pref = getApplicationContext().getSharedPreferences("SearchMedPref", MODE_PRIVATE);

        checkBoxSegundaChegada = (CheckBox) findViewById(R.id.checkBoxSegundaChegada);
        checkBoxTercaChegada = (CheckBox) findViewById(R.id.checkBoxTercaChegada);
        checkBoxQuartaChegada = (CheckBox) findViewById(R.id.checkBoxQuartaChegada);
        checkBoxQuintaChegada = (CheckBox) findViewById(R.id.checkBoxQuintaChegada);
        checkBoxSextaChegada = (CheckBox) findViewById(R.id.checkBoxSextaChegada);
        checkBoxSegunda = (CheckBox) findViewById(R.id.checkBoxSegunda);
        checkBoxTerca = (CheckBox) findViewById(R.id.checkBoxTerca);
        checkBoxQuarta = (CheckBox) findViewById(R.id.checkBoxQuarta);
        checkBoxQuinta = (CheckBox) findViewById(R.id.checkBoxQuinta);
        checkBoxSexta = (CheckBox) findViewById(R.id.checkBoxSexta);
        checkBoxRepetirHorario = (CheckBox) findViewById(R.id.checkBoxRepetirHorario);
        editTextSegundaInicio = (EditText) findViewById(R.id.editTextSegundaInicio);
        editTextSegundaFim = (EditText) findViewById(R.id.editTextSegundaFim);
        editTextTercaInicio = (EditText) findViewById(R.id.editTextTercaInicio);
        editTextTercaFim = (EditText) findViewById(R.id.editTextTercaFim);
        editTextQuartaInicio = (EditText) findViewById(R.id.editTextQuartaInicio);
        editTextQuartaFim = (EditText) findViewById(R.id.editTextQuartaFim);
        editTextQuintaInicio = (EditText) findViewById(R.id.editTextQuintaInicio);
        editTextQuintaFim = (EditText) findViewById(R.id.editTextQuintaFim);
        editTextSextaInicio = (EditText) findViewById(R.id.editTextSextaInicio);
        editTextSextaFim = (EditText) findViewById(R.id.editTextSextaFim);

        segundaId = (TextView) findViewById(R.id.segundaId);
        tercaId = (TextView) findViewById(R.id.tercaId);
        quartaId = (TextView) findViewById(R.id.quartaId);
        quintaId = (TextView) findViewById(R.id.quintaId);
        sextaId = (TextView) findViewById(R.id.sextaId);

        getMedicoHorario();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            try {
                salvar();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void salvar() throws ParseException {
        Log.i(TAG, "salvar--------------------");
        Long medicoId = Long.valueOf(pref.getString("key_user_medico_id", null));

        info = new ArrayList<InfoSalvarHorarioDTO>();

        if (checkBoxSegunda.isChecked()) {
            InfoSalvarHorarioDTO m = new InfoSalvarHorarioDTO();
            m.setHorarioId(segundaId.getText().toString());
            m.setDia("SEGUNDA");
            m.setInicio(editTextSegundaInicio.getText().toString());
            m.setFim(editTextSegundaFim.getText().toString());
            m.setOrderChegada(checkBoxSegundaChegada.isChecked());
            m.setRepetirHorario(checkBoxRepetirHorario.isChecked());
            info.add(m);
        }
        if (checkBoxTerca.isChecked()) {
            InfoSalvarHorarioDTO m = new InfoSalvarHorarioDTO();
            m.setHorarioId(tercaId.getText().toString());
            m.setDia("TERCA");
            m.setInicio(editTextTercaInicio.getText().toString());
            m.setFim(editTextTercaFim.getText().toString());
            m.setOrderChegada(checkBoxTercaChegada.isChecked());
            m.setRepetirHorario(checkBoxRepetirHorario.isChecked());
            info.add(m);
        }
        if (checkBoxQuarta.isChecked()) {
            InfoSalvarHorarioDTO m = new InfoSalvarHorarioDTO();
            m.setHorarioId(quartaId.getText().toString());
            m.setDia("QUARTA");
            m.setInicio(editTextQuartaInicio.getText().toString());
            m.setFim(editTextQuartaFim.getText().toString());
            m.setOrderChegada(checkBoxQuartaChegada.isChecked());
            m.setRepetirHorario(checkBoxRepetirHorario.isChecked());
            info.add(m);
        }
        if (checkBoxQuinta.isChecked()) {
            InfoSalvarHorarioDTO m = new InfoSalvarHorarioDTO();
            m.setHorarioId(quintaId.getText().toString());
            m.setDia("QUINTA");
            m.setInicio(editTextQuintaInicio.getText().toString());
            m.setFim(editTextQuintaFim.getText().toString());
            m.setOrderChegada(checkBoxQuintaChegada.isChecked());
            m.setRepetirHorario(checkBoxRepetirHorario.isChecked());
            info.add(m);
        }
        if (checkBoxSexta.isChecked()) {
            InfoSalvarHorarioDTO m = new InfoSalvarHorarioDTO();
            m.setHorarioId(sextaId.getText().toString());
            m.setDia("SEXTA");
            m.setInicio(editTextSextaInicio.getText().toString());
            m.setFim(editTextSextaFim.getText().toString());
            m.setOrderChegada(checkBoxSextaChegada.isChecked());
            m.setRepetirHorario(checkBoxRepetirHorario.isChecked());
            info.add(m);
        }

        MedicoREST medicoREST = new MedicoREST();
        try {
            boolean r = medicoREST.inclurHorario(medicoId, info);
            if (r) {
                Toast.makeText(this, "Horarios adicionados com sucesso!", Toast.LENGTH_SHORT).show();
                getMedicoHorario();
            } else {
                Toast.makeText(this, "Erro ao adicionar horarios!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getMedicoHorario() {
        Log.i(TAG, "getMedicoHorario");
        Long medicoId = Long.valueOf(pref.getString("key_user_medico_id", null));
        MedicoREST medicoREST = new MedicoREST();
        try {
            horarios = medicoREST.getMedicoHorario(medicoId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (horarios != null) {
            for (MedicoHorarioDTO h : horarios) {
                if (h.getRepetirHorario() != null) {
                    checkBoxRepetirHorario.setChecked(h.getRepetirHorario());
                }

                if ("SEGUNDA".equals(h.getDia())) {
                    checkBoxSegunda.setChecked(true);
                    if (h.getOrderChegada()) {
                        checkBoxSegundaChegada.setChecked(true);
                    }
                    editTextSegundaInicio.setText(h.getInicio());
                    editTextSegundaFim.setText(h.getFim());
                    segundaId.setText(h.getId() + "");
                } else if ("TERCA".equals(h.getDia())) {
                    checkBoxTerca.setChecked(true);
                    if (h.getOrderChegada()) {
                        checkBoxTercaChegada.setChecked(true);
                    }
                    editTextTercaInicio.setText(h.getInicio());
                    editTextTercaFim.setText(h.getFim());
                    tercaId.setText(h.getId() + "");
                } else if ("QUARTA".equals(h.getDia())) {
                    checkBoxQuarta.setChecked(true);
                    if (h.getOrderChegada()) {
                        checkBoxQuartaChegada.setChecked(true);
                    }
                    editTextQuartaInicio.setText(h.getInicio());
                    editTextQuartaFim.setText(h.getFim());
                    quartaId.setText(h.getId() + "");
                } else if ("QUINTA".equals(h.getDia())) {
                    checkBoxQuinta.setChecked(true);
                    if (h.getOrderChegada()) {
                        checkBoxQuintaChegada.setChecked(true);
                    }
                    editTextQuintaInicio.setText(h.getInicio());
                    editTextQuintaFim.setText(h.getFim());
                    quintaId.setText(h.getId() + "");
                } else if ("SEXTA".equals(h.getDia())) {
                    checkBoxSexta.setChecked(true);
                    if (h.getOrderChegada()) {
                        checkBoxSextaChegada.setChecked(true);
                    }
                    editTextSextaInicio.setText(h.getInicio());
                    editTextSextaFim.setText(h.getFim());
                    sextaId.setText(h.getId() + "");
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        //ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);
        return true;
    }

}
