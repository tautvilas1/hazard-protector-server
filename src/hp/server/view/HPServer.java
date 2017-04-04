package hp.server.view;

import hp.server.controller.NewsFeed.ParseXML;
import hp.server.controller.Search.ArticleFinder;
import hp.server.controller.NewsFeed.ScrapeArticle;
import hp.server.controller.NewsTemplates.ParseBBC;
import hp.server.controller.NewsTemplates.ParseCBC;
import hp.server.controller.NewsTemplates.ParseEARTHQUAKES;
import hp.server.controller.NewsTemplates.ParseNYT;
import hp.server.controller.Search.KeywordMaker;
import hp.server.model.XMLModels.Article.Article;
import hp.server.model.XMLModels.Article.TableArticle;
import hp.server.model.XMLModels.Search.Keywords;
import hp.server.model.XMLModels.User.TableUser;
import hp.server.model.XMLModels.User.User;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author Tautvilas Simkus
 */
public class HPServer extends TimerTask
{

    public ArrayList<Article> articlesList = new ArrayList<Article>();
    public Keywords keywords;
    public ArrayList<User> usersList = new ArrayList<User>();

    public void getArticles()
    {
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future f = es.submit(new TableArticle());

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

    public void getFullDescriptions()
    {
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\Tautvilas\\Documents\\Final Project\\Server v1\\src\\geckodriver\\geckodriver.exe");
        FirefoxProfile ffProfile = new FirefoxProfile();
        File adBlock = new File("C:\\Users\\Tautvilas\\Documents\\Final Project\\Server v1\\src\\geckodriver\\adblock.xpi");
        ffProfile.addExtension(adBlock);
        WebDriver driver = new FirefoxDriver(ffProfile);

        for(int i = 0; i < articlesList.size();i++)
        {
            if(articlesList.get(i).getFullDescription().equals("none"))
            {
                ExecutorService es = Executors.newSingleThreadExecutor();
                Future f = es.submit(new ScrapeArticle(articlesList.get(i),driver));
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

        driver.quit();
        System.out.println("Full description parsing finished");

    }

    public void parseCBC()
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

    public void parseNYT()
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

    public void parseBBC()
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

    public void parseEARTHQUAKES()
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


    public void getUsers()
    {
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future f = es.submit(new TableUser());

        try
        {
            usersList = (ArrayList<User>) f.get();
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


    private void makeKeywords(User user)
    {
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future f = es.submit(new KeywordMaker(user));

        try
        {
            keywords = (Keywords) f.get();
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

    private void findHazardArticles(User user)
    {
        ArrayList<Article> articlesListFiltered = new ArrayList<Article>();
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future f = es.submit(new ArticleFinder(keywords,articlesList));

        try
        {
            articlesListFiltered = (ArrayList<Article>) f.get();
            setHazardArticles(user,articlesListFiltered);
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

    private void generateHazardArticles()
    {
        getArticles();
        getUsers();
        for(User user : usersList)
        {
            makeKeywords(user);
            findHazardArticles(user);
        }
    }

    private void setHazardArticles(User user,ArrayList<Article> articlesListFiltered)
    {
        int newArticles = 0;
        for(Article article : articlesListFiltered)
        {
            if(user.getHazardArticlesList().size() > 0)
            {

                if(!user.getHazardArticlesList().contains(String.valueOf(article.getId())))
                {
                    user.getHazardArticlesList().add(String.valueOf(article.getId()));
                    newArticles++;
                }
            }
            else {
                user.getHazardArticlesList().add(String.valueOf(article.getId()));
                newArticles++;
            }

        }
        if(newArticles > 0)
        {
            user.saveHazardArticles();
            System.out.println(user.sendPushNotification());
        }
        else
        {
            System.out.println("No new articles for the user "+user.getGcm_id()+" to be notified about");
        }
    }

    private void parseFeed()
    {
        parseCBC();
        parseNYT();
        parseBBC();
        parseEARTHQUAKES();
        getArticles();
        getFullDescriptions();
    }

    @Override
    public void run()
    {
        parseFeed();
        generateHazardArticles();

    }
}
