package searchmedapp;

import android.content.ClipData;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import searchmedapp.webservices.dto.EspecialidadeDTO;
import searchmedapp.webservices.dto.MedicoConvenioDTO;
import searchmedapp.webservices.dto.MedicoEspecialidadeDTO;
import searchmedapp.webservices.rest.EspecialidadeREST;
import searchmedapp.webservices.rest.MedicoREST;

public class MeusDadosConvenioActivity extends AppCompatActivity {

    private static final String TAG = "MeusDadosConvenioActivity";

    private SharedPreferences pref;

    private List<MedicoConvenioDTO> medicoConvenios = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_dados_convenio);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        pref = getApplicationContext().getSharedPreferences("SearchMedPref", MODE_PRIVATE);

        getConvenio();
        getConveniosMedicos();

        findViewById(R.id.dragConvenio).setOnDragListener(new ConvenioDragListener());
        findViewById(R.id.dropConvenio).setOnDragListener(new ConvenioDragListener());
    }

    public void getConvenio(){
        Log.i(TAG, "getConvenio");
        CharSequence[] convenios = getResources().getStringArray(R.array.convenio_array);
         if(convenios!=null){
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.dragConvenio);

            for(CharSequence c : convenios){
                TextView t = new TextView(this);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(10, 4, 4, 4);

                t.setText(c);
                //t.setId(c);
                t.setLayoutParams(params);
                t.setTextSize(16);
                t.setTextColor(getResources().getColor(R.color.colorWhite));

                t.setOnTouchListener(new ConvenioTouchListener());

                linearLayout.addView(t);
            }
        }
    }

    public void getConveniosMedicos(){
        Log.i(TAG, "getConveniosMedicos");
        Long medicoId = Long.valueOf(pref.getString("key_user_medico_id", null));
        MedicoREST medicoREST = new MedicoREST();
        try {
            if(medicoConvenios==null){
                medicoConvenios = medicoREST.getMedicoConvenio(medicoId);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        if(medicoConvenios!=null){
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.dropConvenio);

            for(MedicoConvenioDTO e : medicoConvenios){
                TextView t = new TextView(this);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(10, 4, 4, 4);

                t.setText(e.getConvenio());
               // t.setId(e.getConvenio());
                t.setLayoutParams(params);
                t.setTextSize(16);
                t.setTextColor(getResources().getColor(R.color.colorWhite));

                t.setOnTouchListener(new ConvenioTouchListener());

                linearLayout.addView(t);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_main, menu);

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

    private final class ConvenioTouchListener implements OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                DragShadowBuilder shadowBuilder = new DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }

    public void  incluirConvenio(String convenio){
        String medicoId = pref.getString("key_user_medico_id", null);
        MedicoREST medicoREST = new MedicoREST();
        try {
            boolean r = medicoREST.inclurConvenio(Long.valueOf(medicoId), convenio);
            if(r){
                Toast.makeText(this, R.string.toast_add_convenio, Toast.LENGTH_SHORT).show();
            }else {
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void  excluirConvenio(String convenio){
        String medicoId = pref.getString("key_user_medico_id", null);
        MedicoREST medicoREST = new MedicoREST();
        try {
            boolean r = medicoREST.excluirConvenio(Long.valueOf(medicoId), convenio);
            if(r){
                Toast.makeText(this, R.string.toast_remove_convenio, Toast.LENGTH_SHORT).show();
            }else {
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    class ConvenioDragListener implements OnDragListener {
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
                    String convenio = ""+((TextView)view).getText();

                    Log.i(TAG, "convenio " + convenio);
                    Log.i(TAG, "resourceEntryName " + resourceEntryName);

                    if(resourceEntryName.equals("dropConvenio")){
                        incluirConvenio(convenio);
                    }else{
                        excluirConvenio(convenio);
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
