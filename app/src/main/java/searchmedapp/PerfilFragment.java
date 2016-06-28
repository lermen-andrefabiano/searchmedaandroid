package searchmedapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class PerfilFragment extends Fragment {

    private static final String TAG = "PerfilActivity";

    private static final String ARG_SECTION_NUMBER = "section_number";

    private SharedPreferences pref;

    public static PerfilFragment newInstance(int sectionNumber) {
        PerfilFragment fragment = new PerfilFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public PerfilFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        this.setUserCabecalho(view);

        this.openLstMeusDados(view);

        this.openLstMais(view);

        Button btnLogon = (Button)view.findViewById(R.id.btnLogon);
        btnLogon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logon();
            }
        });

        return view;
    }

    private void openLstMeusDados(View view){
        ListView lstMeusDados = (ListView) view.findViewById(R.id.lstMeusDados);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.activity_adpater_item,
                R.id.textoAdp,
                new String[]{
                        getString(R.string.label_dados_pessoais),
                        getString(R.string.label_especialidades),
                });

        lstMeusDados.setAdapter(adapter);
        lstMeusDados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent i = new Intent(getActivity(), MeusDadosActivity.class);
                        startActivity(i);
                        break;
                    case 1:
                        Intent m = new Intent(getActivity(), MeusDadosEspecialidadeActivity.class);
                        startActivity(m);
                        break;
                }
            }
        });
    }

    private void openLstMais(View view){
        final ListView lstMais = (ListView) view.findViewById(R.id.lstMais);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                new String[]{
                        getString(R.string.label_redefinir_senha),
                        getString(R.string.label_esqueci_senha),
                });

        lstMais.setAdapter(adapter);
        lstMais.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0 :
                        Intent r = new Intent(getActivity(), RedefinirSenhaActivity.class);
                        startActivity(r);
                        break;
                    case 1 :
                        Intent l = new Intent(getActivity(), LembreteSenhaActivity.class);
                        startActivity(l);
                    break;
                }
            }
        });

    }

    public void setUserCabecalho(View view){
        pref = getActivity().getSharedPreferences("SearchMedPref", Context.MODE_PRIVATE);
        String userNome = pref.getString("key_user_nome", "");

        TextView textUserPerfil = (TextView) view.findViewById(R.id.textUserPerfil);
        textUserPerfil.setText(userNome);
    }

    public void logon(){
        pref = getActivity().getSharedPreferences("SearchMedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("key_user_id", null);
        editor.putString("key_user", null);
        editor.putString("key_user_email", null);
        editor.putString("key_user_nome", null);
        editor.putString("key_user_endereco", null);
        editor.putString("key_user_prestador", null);
        editor.commit();

        Intent r = new Intent(getActivity(), MainActivity.class);
        startActivity(r);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
    }

}
