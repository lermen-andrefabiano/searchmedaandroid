package searchmedapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import searchmedapp.R;
import searchmedapp.webservices.dto.MedicoHorarioDTO;

/**
 * Created by Andre on 09/07/2015.
 */
public class MedicoHorarioAdapter extends ArrayAdapter<MedicoHorarioDTO> {

    int resource;

    //Initialize adapter
    public MedicoHorarioAdapter(Context context, int resource, List<MedicoHorarioDTO> items) {
        super(context, resource, items);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final LinearLayout view;
        //Get the current alert object
        MedicoHorarioDTO obj = getItem(position);

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
        TextView texto1 = (TextView) view.findViewById(R.id.texto1);
        TextView texto2 = (TextView) view.findViewById(R.id.texto2);
        TextView texto3 = (TextView) view.findViewById(R.id.texto3);
        TextView texto4 = (TextView) view.findViewById(R.id.texto4);
        TextView texto5 = (TextView) view.findViewById(R.id.texto5);

        texto1.setText(obj.getDia());
        //SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        texto2.setText(obj.getInicio());
        texto3.setText("as");
        texto4.setText(obj.getFim());

        if (obj.getOrderChegada()) {
            texto5.setText("Ordem de Chegada");
        }

        return view;
    }

}
