package searchmedapp;

import java.util.List;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import searchmedapp.adapter.ConsultaAdapter;
import searchmedapp.webservices.dto.MedicoEspecialidadeDTO;
import searchmedapp.webservices.rest.ConsultaREST;
import searchmedapp.webservices.rest.EspecialidadeREST;


public class ConsultaFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    private static final String TAG = "ConsultaFragment";

    private List<MedicoEspecialidadeDTO> listaMedicoEspecialidade = null;

    private MedicoEspecialidadeDTO medicoEspecialidadeSel;

    private Spinner spinnerConvenio;

    private String convenio;

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

        spinnerConvenio = (Spinner) view.findViewById(R.id.spinnerConvenio);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.convenio_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerConvenio.setAdapter(adapter);
        spinnerConvenio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               convenio = (String) parent.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        return view;
    }

    public void getListaEspecialidades(String query, View view){
        if(query!=null&&query.length() >= 3){
            EspecialidadeREST especialidadeREST = new EspecialidadeREST();
            try {
                listaMedicoEspecialidade = especialidadeREST.getMedicoEspecialidade(query);
            }catch (Exception e){
            }

            Log.i(TAG, "listaMedicoEspecialidade");
            if(listaMedicoEspecialidade!=null){
                openListaPrestadores(view);
            }
        }
    }

    private void openListaPrestadores(View view){
        Log.i(TAG, "openListaPrestadores");

        ListView listPrestadores = (ListView) view.findViewById(R.id.listPrestadores);

        ConsultaAdapter adapter = new ConsultaAdapter(getActivity(), R.layout.fragment_consulta_item, listaMedicoEspecialidade);

        listPrestadores.setAdapter(adapter);
        listPrestadores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                medicoEspecialidadeSel = (MedicoEspecialidadeDTO)parent.getItemAtPosition(position);
                abrirPopUpConsulta();
            }
        });
    }

    private void abrirPopUpConsulta(){
        LayoutInflater li = LayoutInflater.from(getActivity());
        View view = li.inflate(R.layout.activity_abrir_consulta, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(view);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(R.string.label_abrir_consulta,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            	 abrirConsulta();
                        }})
                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
    }

    public void abrirConsulta(){
        SharedPreferences pref = getActivity().getSharedPreferences("SearchMedPref", Context.MODE_PRIVATE);
        String user = pref.getString("key_user_id", "");

        try {
            ConsultaREST rest = new ConsultaREST();
            rest.abrir(Long.valueOf(user), medicoEspecialidadeSel.getMedico().getId(), medicoEspecialidadeSel.getEspecialidade().getId());
        }catch (Exception e){
            e.printStackTrace();
        }
        Toast.makeText(getActivity(), R.string.toast_consulta_aberto, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
    }
}