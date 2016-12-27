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


    // Fields
    private JSONBusParser jsonParser;

    private LittleBusDataLot bus_option_1;
    private LittleBusDataLot bus_option_2;

    private String busStopName;
    private String busDestName;


    BusData(String pBusStopName, String pBusDestName, String pBusUrl, String pLineBus)
    {
        this(pBusStopName, pBusDestName, pBusUrl, pLineBus, "", "");
    }

    BusData(String pBusStopName, String pBusDestName, String pBusUrl1, String pBusLine1, String pBusUrl2, String pBusLine2)
    {
        bus_option_1 = new LittleBusDataLot(pBusUrl1, pBusLine1);
        if(pBusLine2 != "" && pBusUrl2 != "")
        {
            bus_option_2 = new LittleBusDataLot(pBusUrl2, pBusLine2);
        }

        busStopName = pBusStopName;
        busDestName = pBusDestName;

        jsonParser = new JSONBusParser();
    }

    void refreshData()
    {
        if(bus_option_1 != null)
        {
            bus_option_1.refresh();
        }
        if(bus_option_2 != null)
        {
            bus_option_2.refresh();
        }
    }


    @Override
    public String getPrintableDetails()
    {
        String firstLine = busStopName+"  >  "+busDestName;
        String secondLine = bus_option_1 != null ? "\n" + bus_option_1.busLine + " : " +bus_option_1.minutesToFirstBus+ ", " + bus_option_1.minutesToSecondBus : ":(";
        String thirdLine = bus_option_2 != null ? "\n" + bus_option_2.busLine + " : " +bus_option_2.minutesToFirstBus+ ", " + bus_option_2.minutesToSecondBus : "";

        return (thirdLine == "") ? (firstLine + secondLine) : (firstLine + secondLine + thirdLine);
    }

    @Override
    public int getRowImageResource() {
        return R.drawable.bus_logo;
    }



    private class LittleBusDataLot
    {
        String urlData;
        String minutesToFirstBus;
        String minutesToSecondBus;
        String busLine;

        LittleBusDataLot(String pBusUrl, String pBusLine)
        {
            urlData = pBusUrl;
            busLine = pBusLine;

            minutesToFirstBus = NO_DATA_FOUND;
            minutesToSecondBus = NO_DATA_FOUND;
        }

        void refresh()
        {
            if(jsonParser.init(urlData))
            {
                minutesToFirstBus = jsonParser.getMinutesToBus(0);
                minutesToSecondBus = jsonParser.getMinutesToBus(1);
            }
        }

    }
}