package searchmedapp;

import java.util.ArrayList;
import java.util.List;


import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Telephony;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.content.ClipData;
import android.graphics.drawable.Drawable;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import searchmedapp.webservices.dto.EspecialidadeDTO;
import searchmedapp.webservices.rest.EspecialidadeREST;
import searchmedapp.webservices.rest.MedicoREST;

public class MeusDadosEspecialidadeActivity extends AppCompatActivity {

    private static final String TAG = "MeusDadosEspecialidadeActivity";

    private SharedPreferences pref;

    private EspecialidadeDTO especialidadeSel;

    private List<EspecialidadeDTO> especialidades = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_dados_especialidade);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        pref = getApplicationContext().getSharedPreferences("SearchMedPref", MODE_PRIVATE);

        getEspecialidades();

        findViewById(R.id.dragEspecialidade).setOnDragListener(new EspecialidaeDragListener());
        findViewById(R.id.dropEspecialidade).setOnDragListener(new EspecialidaeDragListener());
    }

    public void getEspecialidades(){
        Log.i(TAG, "getEspecialidades");
        EspecialidadeREST especialidadeREST = new EspecialidadeREST();
        try {
            if(especialidades==null){
                especialidades = especialidadeREST.getEspecialidades();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        if(especialidades!=null){
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.dragEspecialidade);

            for(EspecialidadeDTO e : especialidades){
                TextView t = new TextView(this);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(4, 4, 4, 4);

                t.setText(e.getDescricao());
                t.setId(e.getId().intValue());
                t.setLayoutParams(params);
                t.setTextSize(16);
                t.setTextColor(getResources().getColor(R.color.colorWhite));

                t.setOnTouchListener(new EspecialidadeTouchListener());

                linearLayout.addView(t);
            }
        }
    }

    public void listarPorPrestador(){
        /*String keyUserId = pref.getString("key_user_id", null);
        Long userId = keyUserId!=null ? Long.valueOf(keyUserId) : null;

        try {
            EspecialidadeREST rest = new EspecialidadeREST();
            especialidades = rest.getEspecialidadesMedicas(userId);
        }catch (Exception e){
        }

        if(especialidades!=null){
            ListView listEspecialidadePrestador = (ListView) findViewById(R.id.listEspecialidadePrestador);

            MeusDadosAdapter adapter = new MeusDadosAdapter(this, R.layout.activity_meus_dados_especialidade_item, especialidades);
            listEspecialidadePrestador.setAdapter(adapter);
            listEspecialidadePrestador.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    especialidadeSel = (MedicoEspecialidadeDTO)parent.getItemAtPosition(position);
                }
            });
        }   */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        //ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);
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

    private final class EspecialidadeTouchListener implements OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }

    public void  incluirEspecialidade(int especialidadeId){
        String usuarioId = pref.getString("key_user_id", null);
        MedicoREST medicoREST = new MedicoREST();
        try {
            boolean r = medicoREST.inclurEspecialidade(Long.valueOf(usuarioId), (long)especialidadeId);
            if(r){
                Toast.makeText(this, R.string.toast_add_especialidade, Toast.LENGTH_SHORT).show();
            }else {
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void  excluirEspecialidade(int especialidadeId){
        String usuarioId = pref.getString("key_user_id", null);
        MedicoREST medicoREST = new MedicoREST();
        try {
            boolean r = medicoREST.excluirEspecialidade(Long.valueOf(usuarioId), (long)especialidadeId);
            if(r){
                Toast.makeText(this, R.string.toast_remove_especialidade, Toast.LENGTH_SHORT).show();
            }else {
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    class EspecialidaeDragListener implements OnDragListener {
        Drawable enterShape = getResources().getDrawable(R.drawable.shape_especialidade_droptarget);
        Drawable normalShape = getResources().getDrawable(R.drawable.shape_especialidade);

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    Log.i(TAG, "ACTION_DRAG_STARTED");
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackgroundDrawable(enterShape);
                    Log.i(TAG, "ACTION_DRAG_ENTERED");
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackgroundDrawable(normalShape);
                    Log.i(TAG, "ACTION_DRAG_EXITED");
                    break;
                case DragEvent.ACTION_DROP:
                    Log.i(TAG, "ACTION_DROP");
                    // Dropped, reassign View to ViewGroup
                    View view = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();
                    owner.removeView(view);

                    LinearLayout container = (LinearLayout) v;
                    container.addView(view);
                    view.setVisibility(View.VISIBLE);

                    String resourceEntryName = getResources().getResourceEntryName(container.getId());
                    int especialidadeId = view.getId();

                    Log.i(TAG, "resourceEntryName " + resourceEntryName);
                    Log.i(TAG, "especialidadeId " + especialidadeId);

                    if(resourceEntryName.equals("dropEspecialidade")){
                        incluirEspecialidade(especialidadeId);
                    }else{
                        excluirEspecialidade(especialidadeId);
                    }

                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    Log.i(TAG, "ACTION_DRAG_ENDED");
                    v.setBackgroundDrawable(normalShape);
                default:
                    break;
            }
            return true;
        }
    }
}
