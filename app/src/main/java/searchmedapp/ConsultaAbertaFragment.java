package searchmedapp;

import java.util.List;


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

import searchmedapp.adapter.ConsultaAbertaAdapter;
import searchmedapp.webservices.dto.ConsultaDTO;
import searchmedapp.webservices.rest.ConsultaREST;


public class ConsultaAbertaFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    private static final String TAG = "ConsultaAbertaFragment";

    private List<ConsultaDTO> consultasAbertas;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Fragment newInstance(int sectionNumber) {
        ConsultaAbertaFragment fragment = new ConsultaAbertaFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ConsultaAbertaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_consulta_aberta, container, false);

        carregamento();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        consultasAbertas(rootView);

        return rootView;
    }

    public void consultasAbertas(View rootView){
        SharedPreferences pref = getActivity().getSharedPreferences("SearchMedPref", Context.MODE_PRIVATE);
        String medicoId = pref.getString("key_user_medico_id", "");

        try {
            ConsultaREST rest = new ConsultaREST();
            consultasAbertas = rest.consultasAbertas(Long.valueOf(medicoId));
        }catch (Exception e){
            e.printStackTrace();
        }

        if(consultasAbertas!=null){
            ExpandableListView lstConusltasAberta = (ExpandableListView) rootView.findViewById(R.id.lstConusltasAberta);

            ConsultaAbertaAdapter listAdapter = new ConsultaAbertaAdapter(getActivity(), consultasAbertas, Long.valueOf(medicoId));
            lstConusltasAberta.setAdapter(listAdapter);
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