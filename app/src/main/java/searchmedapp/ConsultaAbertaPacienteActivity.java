package searchmedapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import searchmedapp.webservices.dto.ConsultaDTO;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;

public class ConsultaAbertaPacienteActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "ConsultaAbertaPacienteActivity";

    private static final String ARG_SECTION_NUMBER = "section_number";

    private SharedPreferences pref;

    private ConsultaDTO consultaSel;

    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    private GoogleMap mMap;

    public ConsultaAbertaPacienteActivity() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_aberta_paciente);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        pref = this.getSharedPreferences("SearchMedPref", Context.MODE_PRIVATE);

        Intent intent = getIntent();
        String jsonConsultaSel = intent.getStringExtra("jsonConsultaSel");

        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject obj = parser.parse(jsonConsultaSel).getAsJsonObject();
        consultaSel = gson.fromJson(obj, ConsultaDTO.class);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        TextView textMapData = (TextView) findViewById(R.id.textMapData);
        TextView textMapMedico = (TextView) findViewById(R.id.textMapMedico);
        TextView textMapEspecialidade = (TextView) findViewById(R.id.textMapEspecialidade);
        TextView textMapStatus = (TextView) findViewById(R.id.textMapStatus);

        textMapData.setText(format.format(consultaSel.getData()));
        textMapMedico.setText(consultaSel.getMedico().getMedicoNome());
        textMapEspecialidade.setText(consultaSel.getEspecialidade().getDescricao());
        textMapStatus.setText(getStatus(consultaSel.getStatus()));
    }

    private String getStatus(String status){
        if("A".equals(status)){
            return "Aberto";
        }else if("E".equals(status)){
            return "Em andamento";
        }else if("C".equals(status)){
            return "Classificação pendente";
        }else if("R".equals(status)){
            return "Rejeitado";
        }

        return "";
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        LatLng latMed = null;

        Double lat = consultaSel.getUsuario().getLatitude();
        Double lgt = consultaSel.getUsuario().getLongitude();

        if(lat.equals(0) || lat.equals(0.0)){
            latMed = new LatLng(-34, 151);
        }else{
            latMed = new LatLng(lat, lgt);
        }

        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.addMarker(new MarkerOptions().position(latMed).title(consultaSel.getMedico().getMedicoNome()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latMed));
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }

}
