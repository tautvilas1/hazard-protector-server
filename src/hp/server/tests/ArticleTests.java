package hp.server.tests;

import hp.server.controller.NewsFeed.GetArticles;
import hp.server.controller.NewsFeed.SaveArticle;
import hp.server.controller.NewsFeed.SaveFullDescription;
import hp.server.controller.NewsFeed.ScrapeArticle;
import hp.server.controller.Selenium.SeleniumConfig;
import hp.server.model.XMLModels.Article.Article;
import hp.server.model.XMLModels.Article.TableArticle;
import hp.server.model.XMLModels.Common.Response;
import org.junit.*;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;


/**
 * Created by Tautvilas on 22/02/2017.
 */
public class ArticleTests {
    @Test
    public void Save_Article_To_Database()
    {
        Article article = new Article();
        Random r = new Random();
        article.setTitle("Test title " + r.nextInt(99999999));
        article.setDescription("Test description " + r.nextInt(99999999));
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future f = es.submit(new SaveArticle(article, 0));

        try
        {
            Response response = (Response) f.get();
            System.out.println(response.toString());
            assertEquals("Article failed to save", 200, response.getStatus());
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        } catch (ExecutionException e)
        {
            e.printStackTrace();
        }

    }

    @Test
    public void Save_Article_Full_Description_To_Database()
    {
        Article article = new Article();
        Random r = new Random();
        article.setTitle("Test title " + r.nextInt(99999999));
        article.setFullDescription("Test description " + r.nextInt(99999999));
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future f = es.submit(new SaveFullDescription(article));

        try
        {
            Response response = (Response) f.get();
            System.out.println(response.toString());
            assertEquals("Article failed to save its full description", 200, response.getStatus());
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        } catch (ExecutionException e)
        {
            e.printStackTrace();
        }

    }

    @Test
    public void Get_Articles_From_Database()
    {
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future f = es.submit(new TableArticle(1000,0));
        try
        {
            ArrayList<Article> articlesList = (ArrayList<Article>) f.get();
            System.out.println("Number of articles received from the database: "+articlesList.size());
            assertEquals(true,articlesList.size() > 0);
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

    @Test
    public void Scrape_Article_Using_Selenium()
    {

        SeleniumConfig seleniumConfig = new SeleniumConfig();
        WebDriver driver = seleniumConfig.driver;
        Random random = new Random();

        GetArticles getArticles = new GetArticles();
        ArrayList<Article> articlesList = getArticles.returnList(1,random.nextInt(100));

        ExecutorService es = Executors.newSingleThreadExecutor();
        Future f = es.submit(new ScrapeArticle(articlesList.get(0),driver));
        try
        {
            String result = (String) f.get();
            assertFalse(articlesList.get(0).getFullDescription().equals("none"));
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
        }
        driver.quit();
    }



}
