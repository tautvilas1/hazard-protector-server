package hp.server.model.XMLModels.User;


import hp.server.controller.Notification.PushNotification;
import hp.server.controller.User.SaveHazardArticles;
import hp.server.model.XMLModels.Common.Response;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Tautvilas on 28/03/2017.
 */
public class User
{


    private int id = 0;
    private String gcm_id = "";
    private String registrationId = "";
    private ArrayList<String> hazardArticlesList = new ArrayList<String>();
    private double latitude = 0.0;
    private double longitude = 0.0;
    private ArrayList<String> preferences = new ArrayList<String>();
    private ArrayList<String> addressComponents = new ArrayList<String>();
    private String terror = "true";
    private String flood = "true";
    private String war = "true";
    private String earthquake = "true";
    private String political = "true";
    private String criminal = "true";

    public User()
    {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGcm_id() {
        return gcm_id;
    }

    public void setGcm_id(String gcm_id) {
        this.gcm_id = gcm_id;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }


    public void setHazardArticles(String hazardArticles)
    {
        if(!hazardArticles.equals("0"))
        {
            String[] articles = hazardArticles.split(",");
            for(String article: articles)
            {
                hazardArticlesList.add(article.trim());
            }
        }
    }

    public ArrayList<String> getHazardArticlesList()
    {
        return hazardArticlesList;
    }

    public void setHazardArticlesList(ArrayList<String> hazardArticlesList)
    {
        this.hazardArticlesList = hazardArticlesList;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setPreferences(ArrayList<String> preferences)
    {
        this.preferences = preferences;
    }

    public String getTerror()
    {
        return terror;
    }

    public void setTerror(String terror)
    {
        this.terror = terror;
    }

    public String getFlood()
    {
        return flood;
    }

    public void setFlood(String flood)
    {
        this.flood = flood;
    }

    public String getWar()
    {
        return war;
    }

    public void setWar(String war)
    {
        this.war = war;
    }

    public String getEarthquake()
    {
        return earthquake;
    }

    public void setEarthquake(String earthquake)
    {
        this.earthquake = earthquake;
    }

    public String getPolitical()
    {
        return political;
    }

    public void setPolitical(String political)
    {
        this.political = political;
    }

    public String getCriminal()
    {
        return criminal;
    }

    public void setCriminal(String criminal)
    {
        this.criminal = criminal;
    }

    public ArrayList<String> getAddressComponents() {
        return addressComponents;
    }

    public void setAddressComponents(ArrayList<String> addressComponents)
    {
        this.addressComponents = addressComponents;
    }


    public ArrayList<String> getPreferences()
    {
        ArrayList<String> preferences = new ArrayList<String>();
        if(this.getTerror().equals("true"))
        {
            preferences.add("terror");
            preferences.add("bomb");
            preferences.add("explosion");
        }
        if(this.getFlood().equals("true"))
        {
            preferences.add("flood");
        }
        if(this.getWar().equals("true"))
        {
            preferences.add("war");
            preferences.add("military");
        }
        if(this.getEarthquake().equals("true"))
        {
            preferences.add("earthquake");
        }
        if(this.getPolitical().equals("true"))
        {
            preferences.add("political");
            preferences.add("government");
        }
        if(this.getCriminal().equals("true"))
        {
            preferences.add("criminal");
            preferences.add("jail");
            preferences.add("police");
        }
        return preferences;
    }

    public Response saveHazardArticles()
    {
        Response response = new Response();
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future f = es.submit(new SaveHazardArticles(this));

        try
        {
            response = (Response) f.get();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
        }

        return response;
    }

    public Response sendPushNotification()
    {
        Response response = new Response();
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future f = es.submit(new PushNotification(this));

        try
        {
            response = (Response) f.get();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
        }

        return response;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", gcm_id='" + gcm_id + '\'' +
                ", registrationId='" + registrationId + '\'' +
                ", hazardArticles='" + hazardArticlesList.toString() + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", addressComponents=" + addressComponents.toString() +
                '}';
    }
}
