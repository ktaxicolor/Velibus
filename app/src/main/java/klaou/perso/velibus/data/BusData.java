package klaou.perso.velibus.data;

import klaou.perso.velibus.R;
import klaou.perso.velibus.json.JSONBusParser;

/**
 * Created by Klaou on 04/11/2016.
 */
public class BusData extends TransportData
{
    // Constants
    private static final String NO_DATA_FOUND = "NO DATA FOUND";
    private final int ROW_IMAGE = R.drawable.bus_logo;


    // Fields
    private String urlData;
    private JSONBusParser jsonParser;

    private String minutesToFirstBus;
    private String minutesToSecondBus;
    private String busStopName;
    private String busDestName;


    BusData(String pBusStopName, String pBusDestName, String pBusUrl)
    {
        busStopName = pBusStopName;
        busDestName = pBusDestName;

        minutesToFirstBus = NO_DATA_FOUND;
        minutesToSecondBus = NO_DATA_FOUND;

        urlData = pBusUrl;
        jsonParser = new JSONBusParser();


    }

    void refreshData()
    {
        if(jsonParser.init(urlData))
        {
            minutesToFirstBus = jsonParser.getMinutesToBus(0);
            minutesToSecondBus = jsonParser.getMinutesToBus(1);
        }
    }


    @Override
    public String getPrintableDetails()
    {
        return  busStopName+"  >  "+busDestName+"\n"+minutesToFirstBus+"\n"+minutesToSecondBus;
    }

    @Override
    public int getRowImageResource() {
        return ROW_IMAGE;
    }
}
