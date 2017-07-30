package klaou.perso.velibus.data;

import klaou.perso.velibus.R;
import klaou.perso.velibus.json.JSONVelibParser;

public class VelibData extends TransportData {

    private final int ERROR_CODE = 999;
    private final int ROW_IMAGE = R.drawable.bike_black_48dp;

    private String urlData;
    private String stationName;
    private int availableDocks;
    private int availableBikes;
    private JSONVelibParser jsonParser;


    VelibData(String pStationName, String pUrlData)
    {
        stationName = pStationName;
        urlData = pUrlData;
        availableBikes = 0;
        availableDocks = 0;
        jsonParser = new JSONVelibParser();
    }

    void refreshData()
    {
        if(jsonParser.init(urlData))
        {
            availableBikes = jsonParser.getVelibBikes();
            availableDocks = jsonParser.getVelibDocks();
        }
        else
        {
            availableBikes = ERROR_CODE;
            availableDocks = ERROR_CODE;
        }

    }

    @Override
    public String getPrintableDetails() {
        return  stationName+"\n> Bikes : "+ availableBikes + "\n> Docks : "+availableDocks;

    }

    @Override
    public int getRowImageResource() {
        return ROW_IMAGE;
    }
}
