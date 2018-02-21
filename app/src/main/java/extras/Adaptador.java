package extras;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.admusan.seminarioa.R;

import java.util.ArrayList;

/**
 * Created by admarcar on 07/02/2018.
 */

public class Adaptador extends ArrayAdapter<Quotation>{

    Context context;
    int resource;
    ArrayList<Quotation> data;

    public Adaptador(Context context, int resource, ArrayList<Quotation> data){
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    public View getView(final int posicion, View convertView, ViewGroup parent){
        View view = convertView;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.quotation_list_row, null);
        }
        Quotation d = data.get(posicion);
        TextView tv1 = (TextView) view.findViewById(R.id.texto_cita);
        tv1.setText(d.getQuoteText());
        TextView tv2 = (TextView) view.findViewById(R.id.texto_autor);
        tv2.setText(d.getQuoteAuthor());
        return view;
    }
}
