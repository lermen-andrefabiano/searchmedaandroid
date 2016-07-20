package searchmedapp;

import java.util.List;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import searchmedapp.adapter.ConvenioAdapter;
import searchmedapp.adapter.EspecialidadeAdapter;
import searchmedapp.util.GPSTracker;
import searchmedapp.webservices.dto.EspecialidadeDTO;
import searchmedapp.webservices.rest.EspecialidadeREST;


public class ConsultaFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    private static final String TAG = "ConsultaFragment";

    private List<EspecialidadeDTO> especialidades = null;

    private EspecialidadeDTO especialidadeSel;

    private String convenio;

    private TextView lbConvenio;

    private TextView lbEspecialidade;

    private TextView lbLocalizacao;

    private Button btnEncontreMedico;

    private GPSTracker gps;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Fragment newInstance(int sectionNumber) {
        ConsultaFragment fragment = new ConsultaFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ConsultaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consulta, container, false);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        lbLocalizacao = (TextView) view.findViewById(R.id.lbLocalizacao);

        openLstPesquisa(view);

        gps = new GPSTracker(getActivity());

        // check if GPS enabled
        if(gps.canGetLocation()){
            final double latitude = gps.getLatitude();
            final double longitude = gps.getLongitude();
            final String localy =  gps.getLocality();
            lbLocalizacao.setText(localy);

            Toast.makeText(getActivity().getApplicationContext(), localy, Toast.LENGTH_LONG).show();
            Toast.makeText(getActivity().getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        }else{
            new Thread() {
                public void run() {
                    try{
                        sleep(500);
                        lbLocalizacao.setText("GPS não está habilitado");
                    } catch (Exception e) {  }
                }
            }.start();

            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
        return view;
    }

    private void openLstPesquisa(View view){
        lbEspecialidade = (TextView) view.findViewById(R.id.lbEspecialidade);
        lbConvenio = (TextView) view.findViewById(R.id.lbConvenio);
        btnEncontreMedico = (Button) view.findViewById(R.id.btnEncontreMedico);

        btnEncontreMedico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               pesquisar();
            }
        });

        lbEspecialidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carregamento();
                getEspecialidades();
            }
        });

        lbConvenio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getConvenio();
            }
        });
    }

    public void getEspecialidades(){
        Log.i(TAG, "getEspecialidades");
        EspecialidadeREST especialidadeREST = new EspecialidadeREST();
        try {
            if(especialidades==null){
                especialidades = especialidadeREST.getEspecialidades();
            }
        }catch (Exception e){
        }

        if(especialidades!=null){
            final Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.activity_list);

            ListView lv = (ListView ) dialog.findViewById(R.id.listPesquisa);
            final EspecialidadeAdapter adapter = new EspecialidadeAdapter(getActivity(), R.layout.activity_adpater_item, especialidades);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    especialidadeSel = (EspecialidadeDTO) parent.getItemAtPosition(position);
                    Log.i(TAG, especialidadeSel.getDescricao());
                    lbEspecialidade.setText(especialidadeSel.getDescricao());
                    dialog.dismiss();
                }
            });
            dialog.setCancelable(true);
            dialog.setTitle("Selecione a Especialidade");
            dialog.show();
        }
    }

    public void getConvenio(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.activity_list);

        ListView lv = (ListView ) dialog.findViewById(R.id.listPesquisa);
        ConvenioAdapter adapter = new ConvenioAdapter(getActivity(), R.layout.activity_adpater_item, getResources().getStringArray(R.array.convenio_array));
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                convenio = (String) parent.getItemAtPosition(position);
                lbConvenio.setText(convenio);
                dialog.dismiss();
            }
        });

        dialog.setCancelable(true);
        dialog.setTitle("Selecione o Convênio Médico");
        dialog.show();
    }

    public void pesquisar(){
        if(convenio== null || convenio.isEmpty()){
            Toast.makeText(getActivity().getApplicationContext(), R.string.toast_convenio, Toast.LENGTH_LONG).show();
        }else if (especialidadeSel == null){
            Toast.makeText(getActivity().getApplicationContext(), R.string.toast_especialidade, Toast.LENGTH_LONG).show();
        }else{
            Intent r = new Intent(getActivity(), PesquisaConsultaActivity.class);
            r.putExtra("convenio", convenio);
            r.putExtra("especialidadeId", especialidadeSel.getId());
            r.putExtra("especialidade", especialidadeSel.getDescricao());
            startActivity(r);
        }
    }

    private boolean carregamento() {
        final ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setTitle(getString(R.string.load_carregando));
        progress.setMessage(getString(R.string.load_aguarde));
        progress.show();
        new Thread() {
            public void run() {
                try{
                    // just doing some long operation
                    sleep(500);
                } catch (Exception e) {  }
                progress.dismiss();
            }
        }.start();
        return true;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
    }
}