package hp.server.controller.NewsFeed;

import hp.server.model.XMLModels.Article.Article;
import hp.server.model.XMLModels.Article.TableArticle;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Tautvilas on 09/04/2017.
 */
public class GetArticles
{
    private ArrayList<Article> articlesList = new ArrayList<Article>();

    public GetArticles()
    {

    }

    public ArrayList<Article> returnList(int limit, int offset)
    {
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future f = es.submit(new TableArticle(limit,offset));

        try
        {
            articlesList = (ArrayList<Article>) f.get();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
        }
        return articlesList;
    }
}
