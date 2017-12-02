package klaou.perso.velibus.data;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import klaou.perso.velibus.R;

/**
 * Created by Klaou on 27/12/2016.
 */

public class RerDataBundle {

        // Fields
        private RerData rer_1;
        private RerData rer_2;
        /*
        private RerData rer_3;
        private RerData rer_4;
        */

        // Getters
        public RerData getRer_1(){return rer_1;}
        public RerData getRer_2(){return rer_2;}
        /*
        public RerData getRer_3(){return rer_3;}
        public RerData getRer_4(){return rer_4;}
        */
        public RerDataBundle(Context context) {
            rer_1 = new RerData(
                    context.getResources().getString(R.string.station_rer_1),
                    context.getResources().getString(R.string.destination_rer_1),
                    context.getResources().getString(R.string.url_rer_1));
            rer_2 = new RerData(
                    context.getResources().getString(R.string.station_rer_2),
                    context.getResources().getString(R.string.destination_rer_2),
                    context.getResources().getString(R.string.url_rer_2));
            /*rer_3 = new RerData(
                    context.getResources().getString(R.string.station_rer_3),
                    context.getResources().getString(R.string.destination_rer_3),
                    context.getResources().getString(R.string.url_rer_3));
            rer_4 = new RerData(
                    context.getResources().getString(R.string.station_rer_4),
                    context.getResources().getString(R.string.destination_rer_4),
                    context.getResources().getString(R.string.url_rer_4));
            */
        }

        public void refresh()
        {
            rer_1.refreshData();
            rer_2.refreshData();
            /*
            rer_3.refreshData();
            rer_4.refreshData();
            */
        }

        public List<TransportData> toList()
        {
            List<TransportData> rerDatas = new ArrayList<TransportData>(){};
            rerDatas.add(rer_1);
            rerDatas.add(rer_2);
            /*
            rerDatas.add(rer_3);
            rerDatas.add(rer_4);
            */
            return rerDatas;
        }
}
