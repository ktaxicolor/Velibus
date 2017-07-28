package klaou.perso.velibus.data;

import klaou.perso.velibus.R;
import klaou.perso.velibus.json.JSONRatpParser;

/**
 * Created by Klaou on 27/12/2016.
 */
public class RerData extends TransportData
{
    // Constants
    private static final String NO_DATA_FOUND = "999";


    // Fields
    private JSONRatpParser jsonParser;

    private String rerUrlData;
    private String rerStopName;
    private String rerDestName;

    private String minutesToFirstRer;
    private String minutesToSecondRer;


    RerData(String pRerStopName, String pRerDestName, String pRerUrl)
    {
        rerStopName = pRerStopName;
        rerDestName = pRerDestName;
        rerUrlData = pRerUrl;

        jsonParser = new JSONRatpParser("rerData.json");
        minutesToFirstRer = NO_DATA_FOUND;
        minutesToSecondRer = NO_DATA_FOUND;
    }

    void refreshData()
    {
        if(jsonParser.init(rerUrlData))
        {
            minutesToFirstRer = jsonParser.getMinutesToBus(0);
            minutesToSecondRer = jsonParser.getMinutesToBus(1);
        }
    }


    @Override
    public String getPrintableDetails()
    {
        return  rerStopName + "  >  " + rerDestName +
                "\n" + minutesToFirstRer +
                "\n" + minutesToSecondRer;
    }

    @Override
    public int getRowImageResource() {
        return R.drawable.rer_a_logo;
    }


}
