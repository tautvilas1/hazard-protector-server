package hp.server.controller.NewsTemplates;

import hp.server.controller.NewsFeed.ParseXML;
import hp.server.controller.NewsFeed.SaveArticle;
import hp.server.model.XMLModels.Article;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.*;


/**
 * Created by Tautvilas Simkus on 29/12/2016.
 */
/*This class is responsible for retrieving independent rss feed*/

public class ParseINP implements Callable<Integer>
{

    private final String url = "http://www.independent.co.uk/news/world/rss";
    private final String nsAtom = "http://www.w3.org/2005/Atom";
    private final String nsContent = "http://purl.org/rss/1.0/modules/content";
    private final String nsRdf = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";

    private final String nsMedia = "http://search.yahoo.com/mrss";
    private final String nsDc = "http://purl.org/dc/elements/1.1";
    private final String nsTaxo = "http://purl.org/rss/1.0/modules/taxonomy/";


    public ParseINP() { }


    @Override
    public Integer call() throws Exception
    {

        Document xmlText = null;
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future f = es.submit(new ParseXML(url));
        try
        {
            xmlText = (Document) f.get();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
        }

        NodeList nodeList = xmlText.getElementsByTagName("item");

        int count = 0,articlesSaved = 0;

        for(int i = 0; i <= nodeList.getLength() - 1; i++)
        {
            Element item = (Element) nodeList.item(i);
            Article article = new Article();
            article.setTitle(item.getElementsByTagName("title").item(0).getTextContent());
            article.setDescription(item.getElementsByTagName("description").item(0).getTextContent());
            article.setLink(item.getElementsByTagName("link").item(0).getTextContent());
            article.setPublishDate(item.getElementsByTagName("pubDate").item(0).getTextContent());
            article.setCredit(item.getElementsByTagName("author").item(0).getTextContent());

            //Add image thumbnail NOT COMPLETED!

            //Add tags
            if(item.getElementsByTagName("media:text").item(0) != null)
            {
                String text = item.getElementsByTagName("media:text").item(0).getTextContent();
                String x[] = text.split(" ");
                ArrayList<String> tags = new ArrayList<>();
                for(int k = 0;k < x.length;k++)
                {
                    tags.add(x[k]);
                }
                article.setTags(tags);
            }

            ExecutorService esInner = Executors.newSingleThreadExecutor();
            Future fInner = esInner.submit(new SaveArticle(article,articlesSaved));
            try
            {
                int status = (int) fInner.get();

                if(status == 200)
                {
                    count++;
                }
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
        System.out.println("Articles found in INP news feed: "+count);
        return count;
    }

}
