package hp.server.controller.NewsFeed;


import hp.server.model.XMLModels.Article;
import hp.server.model.XMLModels.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.concurrent.Callable;

import static org.jsoup.Jsoup.connect;

/**
 * Created by Tautvilas on 17/03/2017.
 */


public class SaveFullDescription implements Callable<Response>
{

    private Article article;

    public SaveFullDescription(Article article)
    {
        this.article = article;
    }


    @Override
    public Response call() {
        Response dbResponse = new Response();
        try
        {
            Document doc = connect("http://www.odontologijos-erdve.lt/hazardprotector/saveFullDescription")
                    .data("id", String.valueOf(article.getId()))
                    .data("fullDescription", article.getFullDescription())
                    .userAgent("Mozilla")
                    .timeout(2000)
                    .post();

            JSONObject response = new JSONObject(doc.body().text());
            dbResponse.setMsg(response.getString("msg"));
            dbResponse.setStatus(response.getInt("status"));
            System.out.println(response.toString());

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (JSONException ex)
        {
            ex.printStackTrace();
        }

        return dbResponse;
    }
}
