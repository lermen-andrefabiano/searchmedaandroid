package searchmedapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import searchmedapp.BoasVindasActivity;
import searchmedapp.MainActivity;
import searchmedapp.R;
import searchmedapp.webservices.dto.ConsultaDTO;
import searchmedapp.webservices.rest.ConsultaREST;

/**
 * Created by Andre on 09/07/2015.
 */
public class ConsultaAbertaAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<ConsultaDTO> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<Integer, ConsultaDTO> _listDataChild = new HashMap<Integer, ConsultaDTO>();

    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    private Long _medicoId;

    public ConsultaAbertaAdapter(Context context, List<ConsultaDTO> consultas, Long medicoId) {
        this._context = context;
        this._listDataHeader = consultas;
        this._medicoId = medicoId;

        for(int i = 0; i < consultas.size(); i++){
            this._listDataChild.put(i, consultas.get(i));
        }
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(groupPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final ConsultaDTO childText = (ConsultaDTO) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.fragment_consulta_aberta_grupo_item, null);
        }

        Button btnConsultaAberta = (Button) convertView.findViewById(R.id.btnConsultaAberta);
        TextView lblCPData = (TextView) convertView.findViewById(R.id.lblCAData);
        TextView lblCPEspecialidade = (TextView) convertView.findViewById(R.id.lblCAEspecialidade);
        TextView lblCPEndereco = (TextView) convertView.findViewById(R.id.lblCAEndereco);

        lblCPData.setText(format.format(childText.getData()));
        lblCPEspecialidade.setText(childText.getEspecialidade().getDescricao());
        lblCPEndereco.setText(childText.getUsuario().getEndereco());

        btnConsultaAberta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ConsultaREST rest = new ConsultaREST();
                    rest.agendar(_medicoId);
                    Toast.makeText(_context, R.string.toast_agendamento_realizado, Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                }

                Intent main = new Intent(_context, MainActivity.class);
                main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                _context.getApplicationContext().startActivity(main);
            }
        });

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ConsultaDTO headerTitle = (ConsultaDTO) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.fragment_consulta_aberta_grupo, null);
        }

        TextView textConsultaAbertaHeaderMed = (TextView) convertView.findViewById(R.id.textConsultaAbertaHeaderMed);
        TextView textConsultaAbertaHeaderAgendamento = (TextView) convertView.findViewById(R.id.textConsultaAbertaHeaderAgendamento);

        textConsultaAbertaHeaderMed.setText(headerTitle.getUsuario().getNome());
        textConsultaAbertaHeaderAgendamento.setText(format.format(headerTitle.getData()));

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
