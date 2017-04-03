package hp.server.tests;

import hp.server.controller.NewsFeed.SaveArticle;
import hp.server.model.XMLModels.Article.Article;
import hp.server.model.XMLModels.Common.Response;
import org.junit.*;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static junit.framework.TestCase.assertEquals;


/**
 * Created by Tautvilas on 22/02/2017.
 */
public class FirstTest
{
    @Test
    public void Test_Save_Article()
    {

        Article article = new Article();
        Random r = new Random();
        article.setTitle("Test title "+r.nextInt(99999999));
        article.setDescription("Test description "+r.nextInt(99999999));
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future f = es.submit(new SaveArticle(article,0));

        try
        {
            Response response = (Response) f.get();
            System.out.println(response.toString());
            assertEquals("Article was not saved",200,response.getStatus());
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
        }

    }

}
