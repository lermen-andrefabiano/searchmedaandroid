package searchmedapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class PerfilActivity extends Fragment {

    private static final String TAG = "PerfilActivity";

    private static final String ARG_SECTION_NUMBER = "section_number";

    private SharedPreferences pref;

    public static PerfilActivity newInstance(int sectionNumber) {
        PerfilActivity fragment = new PerfilActivity();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public PerfilActivity() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        pref = getActivity().getSharedPreferences("SearchMedPref", Context.MODE_PRIVATE);

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
        String tipo = pref.getString("key_user_tipo", null);
        String[] menuArray;
        ViewGroup.LayoutParams params = lstMeusDados.getLayoutParams();

        if(tipo==null || tipo.equals("C")){
            menuArray =  new String[]{getString(R.string.label_dados_pessoais)};
            params.height = 40;
            lstMeusDados.setLayoutParams(params);
        }else{
            menuArray = new String[]{
                    getString(R.string.label_dados_pessoais),
                    getString(R.string.label_especialidades),
                    getString(R.string.label_convenio),
                    getString(R.string.label_horario),
            };
            params.height = 160;
            lstMeusDados.setLayoutParams(params);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.activity_adpater_item,
                R.id.textoAdp,
                menuArray);

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
                    case 2:
                        Intent c = new Intent(getActivity(), MeusDadosConvenioActivity.class);
                        startActivity(c);
                        break;
                    case 3:
                        Intent h = new Intent(getActivity(), MeusDadosHorarioActivity.class);
                        startActivity(h);
                        break;
                }
            }
        });
    }

    private void openLstMais(View view){
        final ListView lstMais = (ListView) view.findViewById(R.id.lstMais);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.activity_adpater_item,
                R.id.textoAdp,
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
        editor.putString("key_user_tipo", null);
        editor.putString("key_user_medico_id", null);
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
