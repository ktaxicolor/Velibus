package klaou.perso.velibus.data;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import klaou.perso.velibus.R;

public class BusDataBundle {

    // Fields
    private BusData bus_1;
    private BusData bus_2;
    private BusData bus_3;
    private BusData bus_4;

    // Getters
    public BusData getBus_1(){return bus_1;}
    public BusData getBus_2(){return bus_2;}
    public BusData getBus_3(){return bus_3;}
    public BusData getBus_4(){return bus_4;}


    public BusDataBundle(Context context)
    {
        bus_1 = new BusData(
                context.getResources().getString(R.string.station_bus_1),
                context.getResources().getString(R.string.destination_bus_1),
                context.getResources().getString(R.string.url_bus_1));
        bus_2 = new BusData(
                context.getResources().getString(R.string.station_bus_2),
                context.getResources().getString(R.string.destination_bus_2),
                context.getResources().getString(R.string.url_bus_2));
        bus_3 = new BusData(
                context.getResources().getString(R.string.station_bus_3),
                context.getResources().getString(R.string.destination_bus_3),
                context.getResources().getString(R.string.url_bus_3));
        bus_4 = new BusData(
                context.getResources().getString(R.string.station_bus_4),
                context.getResources().getString(R.string.destination_bus_4),
                context.getResources().getString(R.string.url_bus_4));
    }

    public void refresh()
    {
        bus_1.refreshData();
        bus_2.refreshData();
        bus_3.refreshData();
        bus_4.refreshData();
    }

    public List<TransportData> toList()
    {
        List<TransportData> busDatas = new ArrayList<TransportData>(){};
        busDatas.add(bus_2);
        busDatas.add(bus_1);
        busDatas.add(bus_3);
        busDatas.add(bus_4);
        return busDatas;
    }

}


