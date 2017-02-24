package hp.server.controller.NewsFeed;

import hp.server.model.XMLModels.Article;
import hp.server.model.XMLModels.Response;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;




import static org.jsoup.Jsoup.connect;
public class SaveArticle implements Callable<Response>
{

    IArticle article;
    public int articlesSaved;
    public Map<String,String> response = new HashMap<String,String>();
    public Response returnResponse = new Response();

    public SaveArticle(IArticle article,int articlesSaved)
    {
        this.article = article;
        this.articlesSaved = articlesSaved;
    }


    @Override
    public Response call() throws Exception {
        try
        {
            Document doc = connect("http://t-simkus.com/final_project/saveArticle.php")
                    .data("title", article.getTitle())
                    .data("link", article.getLink())
                    .data("description", article.getDescription())
                    .data("publishDate", article.getPublishDate())
                    .data("credit", article.getCredit())
                    .data("tags", article.getTagsString())
                    .data("thumbnail", article.getThumbnail())
                    .userAgent("Mozilla")
                    .post();

            JSONObject response = new JSONObject(doc.body().text());
//            System.out.println(doc.body().text());


            returnResponse.setMsg(response.getString("msg"));
            returnResponse.setStatus(response.getInt("status"));
            System.out.println(returnResponse.getStatus());
            System.out.println(returnResponse.getMsg());

            if(returnResponse.getStatus() == 200)
            {
                this.articlesSaved++;
                System.out.println(" Number of articles saved: "+this.articlesSaved);
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (JSONException ex)
        {
            ex.printStackTrace();
        }

        return this.returnResponse;
    }
}
