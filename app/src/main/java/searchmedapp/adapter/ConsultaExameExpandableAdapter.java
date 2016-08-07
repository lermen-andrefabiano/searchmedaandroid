package searchmedapp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import searchmedapp.MainActivity;
import searchmedapp.R;
import searchmedapp.webservices.dto.ConsultaDTO;
import searchmedapp.webservices.dto.ConsultaExameDTO;
import searchmedapp.webservices.dto.LaboratorioDTO;
import searchmedapp.webservices.rest.ConsultaREST;
import searchmedapp.webservices.rest.ExameREST;

/**
 * Created by Andre on 09/07/2015.
 */
public class ConsultaExameExpandableAdapter extends BaseExpandableListAdapter {

    private static final String TAG = "ConsultaExameExpandableAdapter";

    private Context _context;
    private List<ConsultaExameDTO> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<Integer, List<LaboratorioDTO>> _listDataChild = new HashMap<Integer, List<LaboratorioDTO>>();

    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public ConsultaExameExpandableAdapter(Context context, List<ConsultaExameDTO> exames) {
        this._context = context;
        this._listDataHeader = exames;

        for (int i = 0; i < exames.size(); i++) {
            this._listDataChild.put(i, getLaboratorios(exames.get(i).getExame().getId()));
        }
    }

    public List<LaboratorioDTO> getLaboratorios(Long exameId) {
        try {
            ExameREST rest = new ExameREST();
            return rest.getLaboratorio(exameId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<LaboratorioDTO>();
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
        final List<LaboratorioDTO> labs = (List<LaboratorioDTO>) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.fragment_consulta_exame_grupo_item, null);
        }

        TextView lbExameLaboratorio = (TextView) convertView.findViewById(R.id.lbExameLaboratorio);
        lbExameLaboratorio.setVisibility(View.VISIBLE);
        if (labs.isEmpty()) {
            lbExameLaboratorio.setVisibility(View.VISIBLE);
        } else {
            lbExameLaboratorio.setVisibility(View.GONE);
        }

        ListView listExameLab = (ListView) convertView.findViewById(R.id.listExameLab);
        LaboratorioAdapter adapter = new LaboratorioAdapter(this._context, R.layout.activity_adpater_item, labs);
        listExameLab.setAdapter(adapter);

        listExameLab.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                abrirPopUpLab();
            }
        });

        return convertView;
    }

    private void abrirPopUpLab() {
        LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popUpView = infalInflater.inflate(R.layout.fragment_consulta_exame_poppup, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this._context);
        alertDialogBuilder.setView(popUpView);

        final AlertDialog alertDialog = alertDialogBuilder.create();

        DatePicker dtConsultaExame = (DatePicker) popUpView.findViewById(R.id.dtConsultaExame);
        Button btnConsultaExame = (Button) popUpView.findViewById(R.id.btnConsultaExame);
        Button btnCancelarExame = (Button) popUpView.findViewById(R.id.btnCancelarExame);

        btnConsultaExame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        btnCancelarExame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
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
        ConsultaExameDTO consultaExame = (ConsultaExameDTO) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.fragment_consulta_exame_grupo, null);
        }
        TextView textConsultaExameHeader = (TextView) convertView.findViewById(R.id.textConsultaExameHeader);
        textConsultaExameHeader.setText(consultaExame.getExame().getDescricao());
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
