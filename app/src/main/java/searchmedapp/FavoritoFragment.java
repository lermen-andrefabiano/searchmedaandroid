package searchmedapp;

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
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.util.ArrayList;

import searchmedapp.adapter.MedicoEspecialidadeAdapter;
import searchmedapp.adapter.MedicoFavoritoAdapter;
import searchmedapp.webservices.dto.MedicoFavoritoDTO;

public class FavoritoFragment extends Fragment {

    private static final String TAG = "FavoritoFragment";
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    private ArrayList<MedicoFavoritoDTO> favoritos = new ArrayList<MedicoFavoritoDTO>();;

    private MedicoFavoritoDTO medicoFavoritoSel;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Fragment newInstance(int sectionNumber) {
        FavoritoFragment fragment = new FavoritoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public FavoritoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorito, container, false);

        carregamento();

        listar(rootView);

        return rootView;
    }

    public void listar(View rootView){
        SharedPreferences pref = getActivity().getSharedPreferences("SearchMedPref", Context.MODE_PRIVATE);
        String key_user_favorito = pref.getString("key_user_favorito", null);

        if(key_user_favorito!=null){
            Log.i(TAG, key_user_favorito);
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonArray array = parser.parse(key_user_favorito).getAsJsonArray();

            for (int i = 0; i < array.size(); i++) {
                favoritos.add(gson.fromJson(array.get(i), MedicoFavoritoDTO.class));
            }

            ListView listFavorito = (ListView) rootView.findViewById(R.id.listFavorito);

            MedicoFavoritoAdapter adapter = new MedicoFavoritoAdapter(getActivity(),
                    R.layout.fragment_favorito_item,
                    favoritos);

            listFavorito.setAdapter(adapter);
            listFavorito.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    medicoFavoritoSel = (MedicoFavoritoDTO) parent.getItemAtPosition(position);
                    abrirPopUpMedicoFavoritoSel();
                }
            });
        }
    }

    private void abrirPopUpMedicoFavoritoSel(){
        LayoutInflater li = LayoutInflater.from(getActivity());
        View view = li.inflate(R.layout.fragment_favorito_popup, null);

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(view);
        final AlertDialog alertDialog = alertDialogBuilder.create();

        TextView textMed1 = (TextView) view.findViewById(R.id.textMed1);
        TextView textMed2 = (TextView) view.findViewById(R.id.textMed2);
        TextView textMed3 = (TextView) view.findViewById(R.id.textMed3);

        textMed1.setText(medicoFavoritoSel.getMedico().getMedicoNome());
        textMed2.setText("CRM: " + medicoFavoritoSel.getMedico().getCrm());
        textMed3.setText("Endereço: " + medicoFavoritoSel.getMedico().getMedicoEndereco());

        ListView listFavoritoEspecialidade = (ListView ) view.findViewById(R.id.listFavoritoEspecialidade);
        final MedicoEspecialidadeAdapter adapter = new MedicoEspecialidadeAdapter(getActivity(), R.layout.activity_adpater_item, medicoFavoritoSel.getMedico().getEspecialidades());
        listFavoritoEspecialidade.setAdapter(adapter);

        //ListView listFavoritoConvenio = (ListView ) view.findViewById(R.id.listFavoritoConvenio);
        //final MedicoConvenioAdapter adapterC = new MedicoConvenioAdapter(getActivity(), R.layout.activity_adpater_item, medicoFavoritoSel.getMedico().getConvenios());
        //listFavoritoConvenio.setAdapter(adapterC);

        Log.i(TAG, "size especialidades " + medicoFavoritoSel.getMedico().getEspecialidades().size());
        Log.i(TAG, "size convenio " + medicoFavoritoSel.getMedico().getConvenios().size());

        Button btnFecharFavorito = (Button) view.findViewById(R.id.btnFecharFavorito);
        btnFecharFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
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