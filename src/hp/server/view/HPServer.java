package hp.server.view;

import hp.server.controller.NewsFeed.GetArticles;
import hp.server.controller.NewsFeed.ScrapeArticle;
import hp.server.controller.NewsTemplates.ParseBBC;
import hp.server.controller.NewsTemplates.ParseCBC;
import hp.server.controller.NewsTemplates.ParseEARTHQUAKES;
import hp.server.controller.NewsTemplates.ParseNYT;
import hp.server.model.XMLModels.Article;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author Tautvilas Simkus
 */
public class HPServer
{

    public static ArrayList<Article> articlesList = new ArrayList<Article>();

    public static void getArticles()
    {
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future f = es.submit(new GetArticles());

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
    }

    public static void getFullDescriptions()
    {


        for(int i = 0; i < articlesList.size();i++)
        {
            if(articlesList.get(i).getSource().equals("EARTHQUAKES"))
            {
                ExecutorService es = Executors.newSingleThreadExecutor();
                Future f = es.submit(new ScrapeArticle(articlesList.get(i)));
                try
                {
                    String result = (String) f.get();
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

    }

    public static void parseCBC()
    {
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future f = es.submit(new ParseCBC());

        try
        {
            Integer statusCBC = (Integer) f.get();
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

    public static void parseNYT()
    {
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future f = es.submit(new ParseNYT());

        try
        {
            Integer statusNYT = (Integer) f.get();
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

    public static void parseBBC()
    {
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future f = es.submit(new ParseBBC());

        try
        {
            Integer statusBBC = (Integer) f.get();
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

    public static void parseEARTHQUAKES()
    {
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future f = es.submit(new ParseEARTHQUAKES());

        try
        {
            Integer statusEARTHQUAKES = (Integer) f.get();
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

    public static void parseFeed()
    {
        parseCBC();
        parseNYT();
        parseBBC();
        parseEARTHQUAKES();
        getArticles();
        getFullDescriptions();
    }

    public static void main(String[] args) {
        parseFeed();
    }
}
