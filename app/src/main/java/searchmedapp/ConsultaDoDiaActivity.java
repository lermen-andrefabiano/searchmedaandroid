package searchmedapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.text.SimpleDateFormat;

import searchmedapp.webservices.dto.ConsultaDTO;

public class ConsultaDoDiaActivity extends AppCompatActivity {

    private static final String TAG = "ConsultaDoDiaActivity";

    private static final String ARG_SECTION_NUMBER = "section_number";

    private SharedPreferences pref;

    private ConsultaDTO consultaSel;

    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    private GoogleMap mMap;

    public ConsultaDoDiaActivity() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_do_dia);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        pref = this.getSharedPreferences("SearchMedPref", Context.MODE_PRIVATE);

        Intent intent = getIntent();
        String jsonConsultaSel = intent.getStringExtra("jsonConsultaSel");

        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject obj = parser.parse(jsonConsultaSel).getAsJsonObject();
        consultaSel = gson.fromJson(obj, ConsultaDTO.class);

        TextView textMapData = (TextView) findViewById(R.id.textMapData);
        TextView textMapMedico = (TextView) findViewById(R.id.textMapMedico);
        TextView textMapEspecialidade = (TextView) findViewById(R.id.textMapEspecialidade);
        TextView textMapStatus = (TextView) findViewById(R.id.textMapStatus);

        textMapData.setText(format.format(consultaSel.getData()));
        textMapMedico.setText(consultaSel.getMedico().getMedicoNome());
        textMapEspecialidade.setText(consultaSel.getEspecialidade().getDescricao());
        textMapStatus.setText(getStatus(consultaSel.getStatus()));
    }

    private String getStatus(String status) {
        if ("A".equals(status)) {
            return "Aberto";
        } else if ("E".equals(status)) {
            return "Em andamento";
        } else if ("C".equals(status)) {
            return "Classificação pendente";
        } else if ("R".equals(status)) {
            return "Rejeitado";
        }

        return "";
    }

}
