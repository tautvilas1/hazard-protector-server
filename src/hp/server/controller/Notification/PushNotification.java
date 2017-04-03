package hp.server.controller.Notification;

import hp.server.model.XMLModels.Common.Response;
import hp.server.model.XMLModels.User.User;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.concurrent.Callable;

import static org.jsoup.Jsoup.connect;

/**
 * Created by Tautvilas on 03/04/2017.
 */
public class PushNotification implements Callable<Response>
{
    private User user;

    public PushNotification(User user)
    {
        this.user = user;
    }
    @Override
    public Response call()
    {
        Response dbResponse = new Response();
        JSONObject response = null;
        Document doc = null;
        try {

            doc = connect("http://www.odontologijos-erdve.lt/hazardprotector/sendNotification.php")
                    .data("registrationId", user.getRegistrationId())
                    .userAgent("Mozilla")
                    .timeout(10000)
                    .post();

            response = new JSONObject(doc.body().text());
            dbResponse.setMsg(response.getString("msg"));
            dbResponse.setStatus(response.getInt("status"));
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return dbResponse;
    }
}
