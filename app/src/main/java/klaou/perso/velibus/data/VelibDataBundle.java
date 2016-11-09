package klaou.perso.velibus.data;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import klaou.perso.velibus.R;

/**
 * Created by Klaou on 02/11/2016.
 */
public class VelibDataBundle {

    private VelibData velib_1;
    private VelibData velib_2;
    private VelibData velib_3;

    /*Getters*/
    public VelibData getVelib1(){return velib_1;}
    public VelibData getVelib2(){return velib_2;}
    public VelibData getVelib3(){return velib_3;}

    public VelibDataBundle(Context context)
    {
        velib_1 = new VelibData(context.getResources().getString(R.string.station_velib_1), context.getResources().getString(R.string.url_velib_1));
        velib_2 = new VelibData(context.getResources().getString(R.string.station_velib_2), context.getResources().getString(R.string.url_velib_2));
        velib_3 = new VelibData(context.getResources().getString(R.string.station_velib_3), context.getResources().getString(R.string.url_velib_3));
    }

    public void refresh()
    {
        velib_1.refreshData();
        velib_2.refreshData();
        velib_3.refreshData();
    }

    public List<TransportData> toList()
    {
        List<TransportData> velibDatas = new ArrayList<TransportData>(){};
        velibDatas.add(velib_1);
        velibDatas.add(velib_2);
        velibDatas.add(velib_3);
        return velibDatas;
    }
}

