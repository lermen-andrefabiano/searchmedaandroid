package searchmedapp;

import java.util.List;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import searchmedapp.adapter.ConsultaClassificacaoAdapter;
import searchmedapp.webservices.dto.ConsultaDTO;
import searchmedapp.webservices.rest.ConsultaREST;

public class ConsultaClassificacaoFragment extends Fragment {

    private static final String TAG = "ConsultaClassificacaoFragment";
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    private List<ConsultaDTO> classificacoes;

    private ConsultaDTO classificacaoSel;

    private EditText editRecomendacao;

    private Spinner spinnerNota;

    private Long nota;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Fragment newInstance(int sectionNumber) {
        ConsultaClassificacaoFragment fragment = new ConsultaClassificacaoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ConsultaClassificacaoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_consulta_classificacao, container, false);

        listarClassificacoes(rootView);

        return rootView;
    }

    public void listarClassificacoes(View rootView){
        SharedPreferences pref = getActivity().getSharedPreferences("SearchMedPref", Context.MODE_PRIVATE);
        String user = pref.getString("key_user_id", "");

        Log.i(TAG, user);

        try {
            ConsultaREST rest = new ConsultaREST();
            classificacoes = rest.listarClassificacoes(Long.valueOf(user));
        }catch (Exception e){
            e.printStackTrace();
        }

        if(classificacoes!=null){
            openClassificacao(rootView);
        }
    }

    public void openClassificacao(View rootView){
        Log.i(TAG, "openClassificacao");
        carregamento();

        ListView listClassificacao = (ListView) rootView.findViewById(R.id.listClassificacao);

        ConsultaClassificacaoAdapter adapter = new ConsultaClassificacaoAdapter(getActivity(),
                R.layout.fragment_consulta_classificacao_item,
                classificacoes);

        listClassificacao.setAdapter(adapter);
        listClassificacao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                classificacaoSel = (ConsultaDTO) parent.getItemAtPosition(position);
                abrirPopUpClassificacao();
            }
        });
    }

    private void abrirPopUpClassificacao(){
        LayoutInflater li = LayoutInflater.from(getActivity());
        View classificarChamadoView = li.inflate(R.layout.activity_classificar_consulta, null);

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(classificarChamadoView);

        editRecomendacao = (EditText) classificarChamadoView.findViewById(R.id.editRecomendacao);
        RatingBar ratingBar = (RatingBar) classificarChamadoView.findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                nota = (long)rating;
                Log.i(TAG, "nota" + nota);
            }
        });

        // create alert dialog
        final AlertDialog alertDialog = alertDialogBuilder.create();

        Button btnClassificarConsulta = (Button) classificarChamadoView.findViewById(R.id.btnClassificarConsulta);
        btnClassificarConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editRecomendacao.getText().toString().equals("")) {
                    classificar();
                    alertDialog.dismiss();
                }else {
                    Toast.makeText(getActivity(), R.string.toast_classificacao_recomendacao, Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button btnCancelarConsulta = (Button) classificarChamadoView.findViewById(R.id.btnCancelarClassificar);
        btnCancelarConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    public void classificar(){
        carregamento();
        try {
            ConsultaREST rest = new ConsultaREST();
            boolean b = rest.classificar(nota, editRecomendacao.getText().toString(), classificacaoSel.getId());
            if(b){
                Toast.makeText(getActivity(), R.string.toast_classificacao_aberta, Toast.LENGTH_SHORT).show();
                listarClassificacoes(getView());
            }else{
                Toast.makeText(getActivity(), R.string.toast_classificacao_erro, Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            Toast.makeText(getActivity(), R.string.toast_erro_geral, Toast.LENGTH_LONG).show();
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