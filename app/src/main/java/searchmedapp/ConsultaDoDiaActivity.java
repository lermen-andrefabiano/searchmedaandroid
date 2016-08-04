package searchmedapp;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.text.SimpleDateFormat;
import java.util.List;

import searchmedapp.webservices.dto.ConsultaDTO;
import searchmedapp.webservices.dto.ConsultaExameDTO;
import searchmedapp.webservices.dto.ExameConsultaDTO;
import searchmedapp.webservices.dto.ExameDTO;
import searchmedapp.webservices.rest.ConsultaREST;
import searchmedapp.webservices.rest.ExameREST;
import searchmedapp.webservices.rest.MedicoREST;

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

        textMapData.setText(format.format(consultaSel.getData()));
        textMapMedico.setText(consultaSel.getUsuario().getNome());

        getExames();
        getExamesConsulta();

        findViewById(R.id.dragExame).setOnDragListener(new ExameDragListener());
        findViewById(R.id.dropExame).setOnDragListener(new ExameDragListener());

        Button btnConcluirConsulta = (Button) findViewById(R.id.btnConcluirConsulta);
        btnConcluirConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ConsultaREST consultaREST = new ConsultaREST();
                    consultaREST.fechar(consultaSel.getId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
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

    public void getExames() {
        Log.i(TAG, "getExames");
        ExameREST exameREST = new ExameREST();
        List<ExameDTO> exames = null;
        try {
            if (exames == null) {
                exames = exameREST.getExames();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (exames != null) {
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.dragExame);

            for (ExameDTO e : exames) {
                TextView t = new TextView(this);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(10, 4, 4, 4);

                t.setText(e.getDescricao());
                t.setId(e.getId().intValue());
                t.setLayoutParams(params);
                t.setTextSize(16);
                t.setTextColor(getResources().getColor(R.color.colorWhite));

                t.setOnTouchListener(new ExameTouchListener());

                linearLayout.addView(t);
            }
        }
    }

    public void getExamesConsulta() {
        Log.i(TAG, "getExamesConsulta");
        ExameREST exameREST = new ExameREST();
        List<ConsultaExameDTO> exames = null;
        try {
            if (exames == null) {
                exames = exameREST.getExamesConsulta(consultaSel.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (exames != null) {
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.dropExame);

            for (ConsultaExameDTO e : exames) {
                TextView t = new TextView(this);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(10, 4, 4, 4);

                t.setText(e.getExame().getDescricao());
                t.setId(e.getExame().getId().intValue());
                t.setLayoutParams(params);
                t.setTextSize(16);
                t.setTextColor(getResources().getColor(R.color.colorWhite));

                t.setOnTouchListener(new ExameTouchListener());

                linearLayout.addView(t);
            }
        }
    }

    private final class ExameTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }

    public void incluir(int exameId) {
        ExameREST exameREST = new ExameREST();
        try {
            boolean r = exameREST.incluir(consultaSel.getId(), (long) exameId);
            if (r) {
                Toast.makeText(this, R.string.toast_add_exame, Toast.LENGTH_SHORT).show();
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void excluir(int exameId) {
        ExameREST exameREST = new ExameREST();
        try {
            boolean r = exameREST.excluir(consultaSel.getId(), (long) exameId);
            if (r) {
                Toast.makeText(this, R.string.toast_remove_exame, Toast.LENGTH_SHORT).show();
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class ExameDragListener implements View.OnDragListener {
        Drawable enterShape = getResources().getDrawable(R.drawable.shape_especialidade_droptarget);
        Drawable normalShape = getResources().getDrawable(R.drawable.shape_especialidade);

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackgroundDrawable(enterShape);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackgroundDrawable(normalShape);
                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    View view = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();
                    owner.removeView(view);

                    LinearLayout container = (LinearLayout) v;
                    container.addView(view);
                    view.setVisibility(View.VISIBLE);

                    String resourceEntryName = getResources().getResourceEntryName(container.getId());
                    int exameId = view.getId();

                    Log.i(TAG, "exameId " + exameId);
                    Log.i(TAG, "resourceEntryName " + resourceEntryName);

                    if (resourceEntryName.equals("dropExame")) {
                        incluir(exameId);
                    } else {
                        excluir(exameId);
                    }

                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setBackgroundDrawable(normalShape);
                default:
                    break;
            }
            return true;
        }
    }

}
