package hp.server.controller.NewsFeed;

import hp.server.model.XMLModels.Article;
import hp.server.model.XMLModels.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;


import java.util.List;
import java.util.concurrent.*;


/**
 * Created by Tautvilas on 17/03/2017.
 */
public class ScrapeArticle implements Callable<String>
{
    private Article article;
    public ScrapeArticle(Article article)
    {
        this.article = article;
    }
    @Override
    public String call()
    {
        String result = "";

        System.setProperty("webdriver.gecko.driver", "C:\\Users\\Tautvilas\\Documents\\Final Project\\Server v1\\src\\geckodriver\\geckodriver.exe");
        WebDriver driver = new FirefoxDriver();
        driver.get(article.getLink());
        if(article.getSource().equals("NYT"))
        {
            List elements = driver.findElements(By.className("story-body-text"));
            for(int i = 0; i < elements.size();i++)
            {
                WebElement element = (WebElement) elements.get(i);
                result = result+""+element.getText()+"\n";
            }

        }
        else if(article.getSource().equals("BBC"))
        {
            WebElement element = null;
            element = driver.findElement(By.className("story-body"));
            result = element.getText();
        }
        else if(article.getSource().equals("CBC"))
        {
            WebElement storyContent = driver.findElement(By.className("story-content"));
            List<WebElement> pTags = storyContent.findElements(By.tagName("p"));
            for(WebElement element : pTags)
            {
                result = result + " " + element.getText() + "\n";
            }
        }
        else if(article.getSource().equals("EARTHQUAKES"))
        {
            WebElement storyContainer = driver.findElement(By.className("entry-content"));
            WebElement storyContent = storyContainer.findElement(By.tagName("div"));
            List<WebElement> paragraphs = storyContent.findElements(By.tagName("p"));
            for(WebElement element : paragraphs)
            {
                result = result + " " + element.getText() + "\n";
            }
        }

        if(result.equals(""))
        {
            result = "none";
        }

        article.setFullDescription(result);
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future f = es.submit(new SaveFullDescription(article));

        try
        {
            Response dbResponse = (Response) f.get();
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
        return result;
    }

}
