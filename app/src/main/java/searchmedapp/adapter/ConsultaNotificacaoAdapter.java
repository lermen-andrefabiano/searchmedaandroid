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
import searchmedapp.webservices.dto.ConsultaDTO;

/**
 * Created by Andre on 09/07/2015.
 */
public class ConsultaNotificacaoAdapter extends ArrayAdapter<ConsultaDTO> {

    int resource;

    //Initialize adapter
    public ConsultaNotificacaoAdapter(Context context, int resource, List<ConsultaDTO> items) {
        super(context, resource, items);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LinearLayout view;
        //Get the current alert object
        ConsultaDTO c = getItem(position);

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

        if(c!=null){
            TextView textData =(TextView)view.findViewById(R.id.textData);
            TextView textUsuario =(TextView)view.findViewById(R.id.textUsuario);
            TextView textEndereco =(TextView)view.findViewById(R.id.textEndereco);
            TextView textEspecialidade =(TextView)view.findViewById(R.id.textEspecialidade);

            textData.setText(c.getData().toString());
            textUsuario.setText(c.getUsuario().getNome());
            textEndereco.setText(c.getEndereco());
            textEspecialidade.setText(c.getEspecialidade().getDescricao());
        }

        return view;
    }
}
