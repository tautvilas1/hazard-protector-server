package hp.server.tests;

import hp.server.controller.NewsTemplates.Callables.GetArticlesBBC;
import hp.server.controller.NewsTemplates.Callables.GetArticlesCBC;
import hp.server.controller.NewsTemplates.Callables.GetArticlesEQ;
import hp.server.controller.NewsTemplates.Callables.GetArticlesNYT;
import hp.server.model.XMLModels.Common.Response;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by Tautvilas on 10/04/2017.
 */
public class FeedTests
{
    @Test
    public void Get_Articles_From_BBC_Feed()
    {
        GetArticlesBBC getArticlesBBC = new GetArticlesBBC();
        Response response = getArticlesBBC.runFeed();
        System.out.println("Articles saved from BBC news feed: "+response.getMsg());
        assertEquals(200,response.getStatus());
    }

    @Test
    public void Get_Articles_From_NYT_Feed()
    {
        GetArticlesNYT getArticlesNYT = new GetArticlesNYT();
        Response response = getArticlesNYT.runFeed();
        System.out.println("Articles saved from NYT news feed: "+response.getMsg());
        assertEquals(200,response.getStatus());
    }

    @Test
    public void Get_Articles_From_CBC_Feed()
    {
        GetArticlesCBC getArticlesCBC = new GetArticlesCBC();
        Response response = getArticlesCBC.runFeed();
        System.out.println("Articles saved from CBC news feed: "+response.getMsg());
        assertEquals(200,response.getStatus());
    }

    @Test
    public void Get_Articles_From_EQ_Feed()
    {
        GetArticlesEQ getArticlesEQ = new GetArticlesEQ();
        Response response = getArticlesEQ.runFeed();
        System.out.println("Articles saved from Earthquakes news feed: "+response.getMsg());
        assertEquals(200,response.getStatus());
    }

}
