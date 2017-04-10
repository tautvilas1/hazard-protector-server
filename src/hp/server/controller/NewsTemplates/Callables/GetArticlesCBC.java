package hp.server.controller.NewsTemplates.Callables;

import hp.server.controller.NewsTemplates.JsoupConnections.ParseCBC;
import hp.server.model.XMLModels.Common.Response;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Tautvilas on 10/04/2017.
 */
public class GetArticlesCBC
{
    public GetArticlesCBC()
    {

    }

    public Response runFeed()
    {
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future f = es.submit(new ParseCBC());
        Response statusCBC = null;
        try
        {
            statusCBC = (Response) f.get();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
        }
        return statusCBC;
    }
}
