package searchmedapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import searchmedapp.R;
import searchmedapp.webservices.dto.MedicoEspecialidadeDTO;

/**
 * Created by Andre on 09/07/2015.
 */
public class DrawerItemAdapter extends ArrayAdapter<DrawerItem> {

    int resource;

    //Initialize adapter
    public DrawerItemAdapter(Context context, int resource, List<DrawerItem> items) {
        super(context, resource, items);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LinearLayout view;
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

        DrawerItem item = getItem(position);

        //Get the text boxes from the listitem.xml file
        ImageView img =(ImageView)view.findViewById(R.id.drawerImage);
        TextView texto =(TextView)view.findViewById(R.id.drawerTexto);

        img.setImageDrawable(view.getResources().getDrawable(item.getImgResID()));
        texto.setText(item.getItemName());

        return view;
    }
}
