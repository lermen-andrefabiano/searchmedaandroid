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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import searchmedapp.adapter.ConsultaClassificacaoAdapter;
import searchmedapp.adapter.MedicoFavoritoAdapter;
import searchmedapp.webservices.dto.ConsultaDTO;
import searchmedapp.webservices.dto.MedicoFavoritoDTO;
import searchmedapp.webservices.rest.ConsultaREST;

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

        listar(rootView);

        return rootView;
    }

    public void listar(View rootView){
        SharedPreferences pref = getActivity().getSharedPreferences("SearchMedPref", Context.MODE_PRIVATE);
        String key_user_favorito = pref.getString("key_user_favorito", "");

        Log.i(TAG, key_user_favorito);
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonArray array = parser.parse(key_user_favorito).getAsJsonArray();

        for (int i = 0; i < array.size(); i++) {
            favoritos.add(gson.fromJson(array.get(i), MedicoFavoritoDTO.class));
        }

        ListView listFavorito = (ListView) rootView.findViewById(R.id.listFavorito);

        MedicoFavoritoAdapter adapter = new MedicoFavoritoAdapter(getActivity(),
                R.layout.activity_adpater_item,
                favoritos);

        listFavorito.setAdapter(adapter);
        listFavorito.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                medicoFavoritoSel = (MedicoFavoritoDTO) parent.getItemAtPosition(position);
            }
        });

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
                    sleep(20000);
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