package hp.server.controller.LocationServices;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * Created by Tautvilas on 14/02/2016.
 */
public class ParseJSON implements Callable<ArrayList<String>>
{
    private String lookupLink;

    public ParseJSON(String lookupLink) {
        this.lookupLink = lookupLink;
    }

    @Override
    public ArrayList<String> call()
    {
        ArrayList<String> addressArray = new ArrayList<String>();
        try
        {
            JSONObject locationJSON = readJsonFromUrl(lookupLink);
            JSONArray resultsJSON = locationJSON.getJSONArray("results");
            JSONObject firstJSON = (JSONObject) resultsJSON.get(0);
            JSONArray addressComponents = firstJSON.getJSONArray("address_components");
            Set<String> addressValues = new HashSet<String>();

            for(int i = 0 ; i < addressComponents.length();i++)
            {
                JSONObject component = (JSONObject) addressComponents.get(i);
                if(!component.toString().contains("street_number"))
                {
                    addressValues.add(component.getString("long_name"));
                }
            }
            addressArray.addAll(addressValues);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return addressArray;
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException
    {
        InputStream is = new URL(url).openStream();
        try
        {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String jsonText = null, line;

            while((line = rd.readLine()) != null)
            {
                jsonText = jsonText + "\n" + line;
            }
            return new JSONObject(jsonText.substring(jsonText.indexOf("{"), jsonText.lastIndexOf("}") + 1));
        }
        finally
        {
            is.close();
        }
    }

}
