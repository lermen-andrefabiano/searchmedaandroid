package searchmedapp.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import searchmedapp.R;
import searchmedapp.webservices.dto.ConsultaDTO;

/**
 * Created by Andre on 09/07/2015.
 */
public class ConsultaPassadaAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<ConsultaDTO> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<Integer, ConsultaDTO> _listDataChild = new HashMap<Integer, ConsultaDTO>();

    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public ConsultaPassadaAdapter(Context context, List<ConsultaDTO> consultas) {
        this._context = context;
        this._listDataHeader = consultas;

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
            convertView = infalInflater.inflate(R.layout.fragment_consulta_passada_grupo_item, null);
        }

        TextView lblCPData = (TextView) convertView.findViewById(R.id.lblCPData);
        TextView lblCPEspecialidade = (TextView) convertView.findViewById(R.id.lblCPEspecialidade);
        TextView lblCPEndereco = (TextView) convertView.findViewById(R.id.lblCPEndereco);
        TextView lblCPExamesRealizados = (TextView) convertView.findViewById(R.id.lblCPExamesRealizados);

        lblCPData.setText(format.format(childText.getData()));
        lblCPEspecialidade.setText(childText.getEspecialidade().getDescricao());
        lblCPEndereco.setText(childText.getUsuario().getNome());

        if(childText.getExames().size()==0){
            lblCPExamesRealizados.setVisibility(View.GONE);
        }else{
            lblCPExamesRealizados.setVisibility(View.VISIBLE);
        }

        ListView lblCPExames = (ListView ) convertView.findViewById(R.id.lblCPExames);
        final ConsultaExameAdapter adapterC = new ConsultaExameAdapter(this._context, R.layout.fragment_consulta_passada_exame_item, childText.getExames());
        lblCPExames.setAdapter(adapterC);

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
            convertView = infalInflater.inflate(R.layout.fragment_consulta_passada_grupo, null);
        }

        TextView textConsultaPassadaHeaderAgendamento = (TextView) convertView.findViewById(R.id.textConsultaPassadaHeaderAgendamento);
        TextView textConsultaPassadaHeaderMed = (TextView) convertView.findViewById(R.id.textConsultaPassadaHeaderMed);
        textConsultaPassadaHeaderAgendamento.setTypeface(null, Typeface.BOLD);

        textConsultaPassadaHeaderAgendamento.setText(format.format(headerTitle.getData()));
        textConsultaPassadaHeaderMed.setText(headerTitle.getMedico().getMedicoNome());

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
