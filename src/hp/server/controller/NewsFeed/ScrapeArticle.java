package hp.server.controller.NewsFeed;

import hp.server.model.XMLModels.Article.Article;
import hp.server.model.XMLModels.Common.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


import java.util.List;
import java.util.concurrent.*;


/**
 * Created by Tautvilas on 17/03/2017.
 */
public class ScrapeArticle implements Callable<String>
{
    private Article article;
    private WebDriver driver;
    public ScrapeArticle(Article article, WebDriver driver)
    {
        this.article = article;
        this.driver = driver;
    }
    @Override
    public String call()
    {
        String result = "";

        driver.get(article.getLink());
        if(article.getSource().equals("NYT"))
        {
            List elements = null;

            if(article.getLink().contains("/video"))
            {
                elements = driver.findElements(By.className("content-description"));
            }
            else
            {
                elements = driver.findElements(By.className("story-body-text"));
            }

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

        return result;
    }

}
