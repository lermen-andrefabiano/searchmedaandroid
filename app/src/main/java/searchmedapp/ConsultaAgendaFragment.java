package searchmedapp;

import android.app.Activity;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.List;

import searchmedapp.adapter.ConsultaNotificacaoAdapter;
import searchmedapp.webservices.dto.ConsultaDTO;
import searchmedapp.webservices.rest.ConsultaREST;


public class ConsultaAgendaFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    private static final String TAG = "ConsultaAgendaFragment";

    private List<ConsultaDTO> consultasAbertas;

    private ConsultaDTO consultaSel;

    private EditText editObservacao;

    private StringBuilder agendamento = new StringBuilder("");;

    private DatePicker dateAgendamento;

    private TimePicker timeAgendaemnto;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Fragment newInstance(int sectionNumber) {
        ConsultaAgendaFragment fragment = new ConsultaAgendaFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ConsultaAgendaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_consulta_notificacao, container, false);

        listarChamados(rootView);
        return rootView;
    }

    public void listarChamados(View rootView){
        SharedPreferences pref = getActivity().getSharedPreferences("SearchMedPref", Context.MODE_PRIVATE);
        String user = pref.getString("key_user_id", "");

        try {
            ConsultaREST rest = new ConsultaREST();
            consultasAbertas = rest.consultasAbertas(Long.valueOf(user));
        }catch (Exception e){
            e.printStackTrace();
        }

        if(consultasAbertas!=null){
            openChamado(rootView);
        }
    }

    public void openChamado(View rootView){
        Log.i(TAG, "openChamado");

        ListView listNotificacao = (ListView) rootView.findViewById(R.id.listNotificacao);

        ConsultaNotificacaoAdapter adapter = new ConsultaNotificacaoAdapter(getActivity(),
                R.layout.fragment_consulta_notificacao_item,
                consultasAbertas);

        listNotificacao.setAdapter(adapter);
        listNotificacao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                consultaSel = (ConsultaDTO)parent.getItemAtPosition(position);
                abrirPopUpNotificacao();
            }
        });
    }

    private void abrirPopUpNotificacao(){
        LayoutInflater li = LayoutInflater.from(getActivity());
        View agendarChamadoView = li.inflate(R.layout.activity_agendar_consulta, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(agendarChamadoView);

        dateAgendamento = (DatePicker) agendarChamadoView.findViewById(R.id.dateAgendamento);
        timeAgendaemnto = (TimePicker) agendarChamadoView.findViewById(R.id.timeAgendaemnto);
        editObservacao = (EditText) agendarChamadoView.findViewById(R.id.editObservacao);

        // set dialog message
        alertDialogBuilder
                .setPositiveButton(R.string.label_agendar,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                agendamento = new StringBuilder();
                                agendamento.append(dateAgendamento.getDayOfMonth());
                                agendamento.append("_");
                                agendamento.append(dateAgendamento.getMonth());
                                agendamento.append("_");
                                agendamento.append(dateAgendamento.getYear());
                                agendamento.append("_");
                                agendamento.append(timeAgendaemnto.getCurrentHour());
                                agendamento.append("_");
                                agendamento.append(timeAgendaemnto.getCurrentMinute());

                                Log.i(TAG, agendamento.toString());

                                agendar();
                            }
                        })
                .setNegativeButton(R.string.label_rejeitar,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                agendar();
                                dialog.cancel();
                            }
                        })
                .setCancelable(true);

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void agendar(){
        try {
            ConsultaREST rest = new ConsultaREST();
            rest.agendar(consultaSel.getId(), agendamento.toString(), editObservacao.getText().toString());
        }catch (Exception e){
        }
        Toast.makeText(getActivity(), R.string.toast_agendamento_realizado, Toast.LENGTH_SHORT).show();

        listarChamados(getView());
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
    }
}