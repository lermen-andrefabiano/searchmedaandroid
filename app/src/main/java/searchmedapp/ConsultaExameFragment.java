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
import android.widget.ListView;

import java.util.List;

import searchmedapp.adapter.ConsultaExameAdapter;
import searchmedapp.adapter.ConsultaExameExpandableAdapter;
import searchmedapp.adapter.ConsultaPassadaAdapter;
import searchmedapp.webservices.dto.ConsultaDTO;
import searchmedapp.webservices.dto.ConsultaExameDTO;
import searchmedapp.webservices.rest.ConsultaREST;
import searchmedapp.webservices.rest.ExameREST;

public class ConsultaExameFragment extends Fragment {

    private static final String TAG = "ConsultaExameFragment";
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    private List<ConsultaExameDTO> exames;

    private ConsultaDTO consultaSel;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Fragment newInstance(int sectionNumber) {
        ConsultaExameFragment fragment = new ConsultaExameFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ConsultaExameFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_consulta_exame, container, false);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        carregamento();

        getExamesUsuario(rootView);

        return rootView;
    }

    public void getExamesUsuario(View rootView) {
        SharedPreferences pref = getActivity().getSharedPreferences("SearchMedPref", Context.MODE_PRIVATE);
        String user = pref.getString("key_user_id", "");

        try {
            if (exames == null) {
                ExameREST rest = new ExameREST();
                exames = rest.getExamesUsuario(Long.valueOf(user));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (exames != null) {
            ExpandableListView lstConusltasAberta = (ExpandableListView) rootView.findViewById(R.id.lstConusltasExame);

            ConsultaExameExpandableAdapter listAdapter = new ConsultaExameExpandableAdapter(getActivity(), exames);
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
                try {
                    // just doing some long operation
                    sleep(500);
                } catch (Exception e) {
                }
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