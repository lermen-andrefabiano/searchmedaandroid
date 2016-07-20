package searchmedapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import searchmedapp.R;
import searchmedapp.webservices.dto.ConsultaDTO;

/**
 * Created by Andre on 09/07/2015.
 */
public class ConsultaDoDiaAdapter extends ArrayAdapter<ConsultaDTO> {

    int resource;

    //Initialize adapter
    public ConsultaDoDiaAdapter(Context context, int resource, List<ConsultaDTO> items) {
        super(context, resource, items);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout view;
        //Get the current alert object
        ConsultaDTO c = getItem(position);

        //Inflate the view
        if (convertView == null) {
            view = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi;
            vi = (LayoutInflater) getContext().getSystemService(inflater);
            vi.inflate(resource, view, true);
        } else {
            view = (LinearLayout) convertView;
        }
        //Get the text boxes from the listitem.xml file
        TextView textAgendamento = (TextView) view.findViewById(R.id.textAgendamento);
        TextView textUser = (TextView) view.findViewById(R.id.textUser);

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        //Assign the appropriate data from our alert object above

        textAgendamento.setText(format.format(c.getData()));
        textUser.setText(c.getUsuario().getNome());

        return view;
    }
}
