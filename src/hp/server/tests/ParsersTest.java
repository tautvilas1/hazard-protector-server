package hp.server.tests;

import hp.server.controller.Parsers.ParseJSON;
import hp.server.controller.Parsers.ParseXML;
import hp.server.controller.NewsTemplates.JsoupConnections.ParseNYT;
import org.json.JSONObject;
import org.junit.Test;
import org.w3c.dom.Document;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static junit.framework.TestCase.assertNotNull;

/**
 * Created by Tautvilas on 10/04/2017.
 */
public class ParsersTest
{
    @Test
    public void Make_Xml_Document_From_Url()
    {
        ExecutorService es = Executors.newSingleThreadExecutor();
        String testLink = new ParseNYT().url;
        Future f = es.submit(new ParseXML(testLink));
        try
        {
            Document document = (Document) f.get();
            assertNotNull(document);
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
