package hp.server.controller.Parsers;


import hp.server.model.XMLModels.Common.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

import static org.jsoup.Jsoup.connect;

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

            Document doc = connect(lookupLink)
                    .userAgent("Mozilla")
                    .timeout(2000)
                    .ignoreContentType(true)
                    .post();

            JSONObject locationJSON = new JSONObject(doc.body().text());

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

}
