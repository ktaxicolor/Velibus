package klaou.perso.velibus.customlayout;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import klaou.perso.velibus.R;
import klaou.perso.velibus.data.TransportData;

/**
 * Created by Klaou on 05/11/2016.
 */

public class DataRowArrayAdapter extends ArrayAdapter<TransportData> {

    private Context context;
    private List<TransportData> transportDataList;
    //private int ressourceLayoutDataRow;

    //constructor, call on creation
    public DataRowArrayAdapter(Context context, int resource, List<TransportData> pBusDataList) {//int pRessourceLayoutDataRow
        super(context, resource, pBusDataList);

        this.context = context;
        this.transportDataList = pBusDataList;
    }

    //called when rendering the list
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        //get the property we are displaying
        TransportData transportDataRow = transportDataList.get(position);

        //get the inflater and inflate the XML layout for each item
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.data_row_velibus, null);

        ImageView transportImage = (ImageView) view.findViewById(R.id.row_image_view);
        transportImage.setImageResource(transportDataRow.getRowImageResource());

        TextView rowDetails = (TextView) view.findViewById(R.id.list_details);
        rowDetails.setText(transportDataRow.getPrintableDetails());

        return view;
    }
}
