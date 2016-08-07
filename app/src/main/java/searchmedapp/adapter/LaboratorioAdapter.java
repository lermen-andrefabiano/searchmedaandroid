package searchmedapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import searchmedapp.R;
import searchmedapp.webservices.dto.LaboratorioDTO;
import searchmedapp.webservices.dto.MedicoConvenioDTO;

/**
 * Created by Andre on 09/07/2015.
 */
public class LaboratorioAdapter extends ArrayAdapter<LaboratorioDTO> {

    int resource;

    //Initialize adapter
    public LaboratorioAdapter(Context context, int resource, List<LaboratorioDTO> items) {
        super(context, resource, items);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout view;
        //Get the current alert object
        LaboratorioDTO obj = getItem(position);

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
        TextView t = (TextView) view.findViewById(R.id.textoAdp);
        t.setText(obj.getNome());

        return view;
    }
}
