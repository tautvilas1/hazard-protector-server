package hp.server.model.XMLModels.User;

/**
 * Created by Tautvilas on 28/03/2017.
 */



import hp.server.controller.LocationServices.CoordinatesToString;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;


/**
 * Created by Tautvilas Simkus on 11/02/2017.
 */




import static org.jsoup.Jsoup.connect;


public class TableUser implements Callable<ArrayList<User>>
{


    public TableUser()
    {

    }

    public ArrayList<User> getData()
    {
        String result = null;
        try
        {

            Document doc = connect("http://www.odontologijos-erdve.lt/hazardprotector/getUsers.php")
                    .data("secretKey", "hKSDJ723MngsK6aI9RT3nHq")
                    .userAgent("Mozilla")
                    .timeout(20000)
                    .post();

            result = doc.body().text();
        }
        catch (IOException e)
        {
            System.out.println("connection timed out to database");
            e.printStackTrace();
        }

        return processResponse(result);
    }


    public ArrayList<User> processResponse(String response)
    {
        ArrayList<JSONObject> usersJSON = new ArrayList<JSONObject>();
        try
        {
            JSONObject jsonObject = new JSONObject(response);
            if(jsonObject != null)
            {
                JSONArray root = jsonObject.getJSONArray("data");
                for(int i = 0; i < root.length();i++)
                {
                    JSONObject item = (JSONObject) root.get(i);
                    usersJSON.add(item);
                }
            }

        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
        return jsonToJava(usersJSON);
    }

    public ArrayList<User> jsonToJava(ArrayList<JSONObject> usersJSON)
    {
        ArrayList<User> usersList = new ArrayList<User>();
        for(int i = 0; i < usersJSON.size();i++)
        {
            User user = new User();
            try
            {
                user.setId(Integer.parseInt(usersJSON.get(i).getString("id")));
                user.setGcm_id(usersJSON.get(i).getString("gcm_id"));
                user.setHazardArticles(usersJSON.get(i).getString("hazardArticles"));
                user.setRegistrationId(usersJSON.get(i).getString("registrationId"));
                user.setLatitude(Double.parseDouble(usersJSON.get(i).getString("latitude")));
                user.setLongitude(Double.parseDouble(usersJSON.get(i).getString("longitude")));
                user.setTerror(usersJSON.get(i).getString("terror"));
                user.setFlood(usersJSON.get(i).getString("flood"));
                user.setWar(usersJSON.get(i).getString("war"));
                user.setEarthquake(usersJSON.get(i).getString("earthquake"));
                user.setPolitical(usersJSON.get(i).getString("political"));
                user.setCriminal(usersJSON.get(i).getString("criminal"));
                if(user.getLatitude() != 0.0 && user.getLongitude() != 0.0)
                {
                    CoordinatesToString converter = new CoordinatesToString(user.getLatitude(),user.getLongitude());
                    ArrayList<String> addressComponents = converter.convert();
                    user.setAddressComponents(addressComponents);
                }
                usersList.add(user);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return usersList;
    }

    @Override
    public ArrayList<User> call() throws Exception
    {
        return getData();
    }
}
