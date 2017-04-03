package hp.server.controller.User;

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
public class SaveHazardArticles implements Callable<Response> {
    private User user;
    public SaveHazardArticles(User user)
    {
        this.user = user;
    }

    @Override
    public Response call() {
        Response dbResponse = new Response();
        JSONObject response = null;
        Document doc = null;
        try {
            String hazardArticles = user.getHazardArticlesList().toString();
            hazardArticles = hazardArticles.replaceAll("\\]","");
            hazardArticles = hazardArticles.replaceAll("\\[","");

            doc = connect("http://www.odontologijos-erdve.lt/hazardprotector/saveHazardArticles.php")

                    .data("gcm_id", user.getGcm_id())
                    .data("hazardArticles", hazardArticles)
                    .userAgent("Mozilla")
                    .timeout(10000)
                    .post();

            response = new JSONObject(doc.body().text());
            dbResponse.setMsg(response.getString("msg"));
            dbResponse.setStatus(response.getInt("status"));
            System.out.println(dbResponse.toString());
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
