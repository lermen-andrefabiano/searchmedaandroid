package searchmedapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import searchmedapp.adapter.ConsultaClassificacaoAdapter;
import searchmedapp.adapter.ConsultaPassadaAdapter;
import searchmedapp.webservices.dto.ConsultaDTO;
import searchmedapp.webservices.rest.ConsultaREST;

public class ConsultaPassadasFragment extends Fragment {

    private static final String TAG = "ConsultaPassadasFragment";
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
        ConsultaPassadasFragment fragment = new ConsultaPassadasFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ConsultaPassadasFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_consulta_passada, container, false);

        carregamento();

        listar(rootView);

        return rootView;
    }

    public void listar(View rootView){
        SharedPreferences pref = getActivity().getSharedPreferences("SearchMedPref", Context.MODE_PRIVATE);
        String user = pref.getString("key_user_id", "");

        try {
            if(consultas==null){
                ConsultaREST rest = new ConsultaREST();
                consultas = rest.consultasAntigas(Long.valueOf(user));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        if(consultas!=null){
            ExpandableListView lstConusltasPassadas = (ExpandableListView) rootView.findViewById(R.id.lstConusltasPassadas);

            ConsultaPassadaAdapter listAdapter = new ConsultaPassadaAdapter(getActivity(), consultas);

            // setting list adapter
            lstConusltasPassadas.setAdapter(listAdapter);
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