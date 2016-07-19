package searchmedapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.List;

import searchmedapp.adapter.ConsultaPassadaMedicoAdapter;
import searchmedapp.webservices.dto.ConsultaDTO;
import searchmedapp.webservices.rest.ConsultaREST;

public class ConsultaPassadasMedicoFragment extends Fragment {

    private static final String TAG = "ConsultaPassadasMedicoFragment";
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    private List<ConsultaDTO> consultas;

    private ConsultaDTO consultaSel;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Fragment newInstance(int sectionNumber) {
        ConsultaPassadasMedicoFragment fragment = new ConsultaPassadasMedicoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ConsultaPassadasMedicoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_consulta_passada_medico, container, false);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        carregamento();

        listar(rootView);

        return rootView;
    }

    public void listar(View rootView){
        SharedPreferences pref = getActivity().getSharedPreferences("SearchMedPref", Context.MODE_PRIVATE);
        String medicoId = pref.getString("key_user_medico_id", "");

        try {
            if(consultas==null){
                ConsultaREST rest = new ConsultaREST();
                consultas = rest.consultasAntigasMedico(Long.valueOf(medicoId));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        if(consultas!=null){
            ExpandableListView lstConusltasPassadasMeico = (ExpandableListView) rootView.findViewById(R.id.lstConusltasPassadasMeico);

            ConsultaPassadaMedicoAdapter listAdapter = new ConsultaPassadaMedicoAdapter(getActivity(), consultas);

            // setting list adapter
            lstConusltasPassadasMeico.setAdapter(listAdapter);
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