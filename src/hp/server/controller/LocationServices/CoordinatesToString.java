package hp.server.controller.LocationServices;

import hp.server.controller.Parsers.ParseJSON;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;



/**
 * Created by Tautvilas on 14/02/2016.
 */
public class CoordinatesToString
{

    private ArrayList<String> address = new ArrayList<String>();
    private double latitude, longitude;

    public CoordinatesToString(double latitude, double longitude)
    {
        this.latitude = latitude;
        this.longitude = longitude;
    }


    /*
    @Return: formatted address
     */
    public ArrayList<String> convert()
    {
        if(latitude != 0.0 && longitude != 0.0)
        {
            final String lookupLink = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" +
                    String.valueOf(latitude) + "," + String.valueOf(longitude) + "&key=AIzaSyD_bf5Bw26seqpx7IQRt3pr9zQd6j-tXLs";
            ExecutorService es = Executors.newSingleThreadExecutor();
            Future f = es.submit(new ParseJSON(lookupLink));

            try
            {
                address = (ArrayList<String>) f.get();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            catch (ExecutionException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
           address.add("NO_COORDINATES_GIVEN");
        }
        return address;
    }

}
