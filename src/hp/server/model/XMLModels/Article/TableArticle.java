package hp.server.model.XMLModels.Article;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

/**
 * Created by Tautvilas on 17/03/2017.
 */
public class TableArticle implements Callable<ArrayList<Article>>
{

    private ArrayList<JSONObject> articlesList = new ArrayList<JSONObject>();

    public TableArticle()
    {

    }

    public ArrayList getData() {
        String result = null;
        try
        {
            Document doc = Jsoup.connect("http://www.odontologijos-erdve.lt/hazardprotector/getArticles.php")
                    .data("limit","1000")
                    .data("offset","0")
                    .timeout(10000)
                    .userAgent("Mozilla")
                    .post();


            String body = doc.body().text();
            result = body;

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        articlesToArray(result);

        return jsonToJava(this.articlesList);
    }

    public void articlesToArray(String response)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(response);
            if(jsonObject != null)
            {
                JSONArray root = jsonObject.getJSONArray("data");
                for(int i = 0; i < root.length();i++)
                {
                    JSONObject item = (JSONObject) root.get(i);
                    articlesList.add(item);
                }
            }

        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
    }

    public ArrayList<Article> jsonToJava(ArrayList<JSONObject> articles)
    {
        ArrayList<Article> articlesList = new ArrayList<Article>();
        for(int i = 0; i < articles.size();i++)
        {
            Article article = new Article();
            try
            {
                article.setId(Integer.parseInt(articles.get(i).getString("id")));
                article.setTitle(articles.get(i).getString("title"));
                article.setCredit(articles.get(i).getString("credit"));
                article.setPublishDate(articles.get(i).getString("publishDate"));
                article.setThumbnail(articles.get(i).getString("thumbnail"));
                article.setDescription(articles.get(i).getString("description"));
                article.setLink(articles.get(i).getString("link"));
                article.setSource(articles.get(i).getString("source"));
                article.setFullDescription(articles.get(i).getString("fullDescription"));
                String tags = articles.get(i).getString("tags");
                ArrayList<String> tagsList = new ArrayList<>();
                String[] tagsListTemp = tags.split(",");
                for (String tag : tagsListTemp)
                {
                    tagsList.add(tag);
                }
                article.setTags(tagsList);
                articlesList.add(article);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return articlesList;
    }

    @Override
    public ArrayList call() throws Exception
    {
        return getData();
    }
}
