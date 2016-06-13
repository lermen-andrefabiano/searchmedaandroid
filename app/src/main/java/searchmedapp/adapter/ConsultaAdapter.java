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
import searchmedapp.webservices.dto.MedicoEspecialidadeDTO;

/**
 * Created by Andre on 09/07/2015.
 */
public class ConsultaAdapter extends ArrayAdapter<MedicoEspecialidadeDTO> {

    int resource;

    //Initialize adapter
    public ConsultaAdapter(Context context, int resource, List<MedicoEspecialidadeDTO> items) {
        super(context, resource, items);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LinearLayout view;
        //Get the current alert object
        MedicoEspecialidadeDTO u = getItem(position);

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
        TextView textValor =(TextView)view.findViewById(R.id.textValor);
        TextView textEspecialidade =(TextView)view.findViewById(R.id.textEspecialidade);
        TextView textPrestador =(TextView)view.findViewById(R.id.textPrestador);

        String valorCobrado = getContext().getString(R.string.label_valor_dinheiro) + " " + u.getValor().toString();
        textValor.setText(valorCobrado);
        textEspecialidade.setText(u.getEspecialidade().getDescricao());
        textPrestador.setText(u.getMedico().getUsuario().getNome());

        return view;
    }
}
