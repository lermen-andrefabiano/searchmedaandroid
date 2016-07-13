package searchmedapp.adapter;

import java.util.List;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import searchmedapp.R;
import searchmedapp.webservices.dto.MedicoDTO;

/**
 * Created by Andre on 09/07/2015.
 */
public class PesquisaConsultaAdapter extends ArrayAdapter<MedicoDTO> {

    int resource;

    //Initialize adapter
    public PesquisaConsultaAdapter(Context context, int resource, List<MedicoDTO> items) {
        super(context, resource, items);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LinearLayout view;
        //Get the current alert object
        MedicoDTO m = getItem(position);

        //Inflate the view
        if(convertView==null){
            view = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi;
            vi = (LayoutInflater)getContext().getSystemService(inflater);
            vi.inflate(resource, view, true);
        }else{
            view = (LinearLayout) convertView;
        }
        //Get the text boxes from the listitem.xml file
        TextView textMedico =(TextView)view.findViewById(R.id.textMedico);
        TextView textEndereco =(TextView)view.findViewById(R.id.textEndereco);
        TextView textCRM =(TextView)view.findViewById(R.id.textCRM);

        textMedico.setText(m.getMedicoNome());
        textEndereco.setText(m.getMedicoEndereco());
        textCRM.setText(m.getCrm());

        return view;
    }
}
