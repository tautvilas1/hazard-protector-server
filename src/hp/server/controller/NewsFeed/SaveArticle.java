package hp.server.controller.NewsFeed;

import hp.server.model.XMLModels.Article.Article;
import hp.server.model.XMLModels.Common.Response;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.concurrent.Callable;

import org.json.JSONException;
import org.json.JSONObject;




import static org.jsoup.Jsoup.connect;
public class SaveArticle implements Callable<Response>
{

    Article article;
    public int articlesSaved;

    public SaveArticle(Article article,int articlesSaved)
    {
        this.article = article;
        this.articlesSaved = articlesSaved;
    }


    @Override
    public Response call() {
        Response dbResponse = new Response();
        try
        {
            Document doc = connect("http://www.odontologijos-erdve.lt/hazardprotector/saveArticle")
                    .data("title", article.getTitle())
                    .data("link", article.getLink())
                    .data("description", article.getDescription())
                    .data("publishDate", article.getPublishDate())
                    .data("credit", article.getCredit())
                    .data("tags", article.getTagsString())
                    .data("thumbnail", article.getThumbnail())
                    .data("source", article.getSource())
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
