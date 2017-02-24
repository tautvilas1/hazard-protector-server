package hp.server.controller.NewsTemplates;

import hp.server.controller.NewsFeed.ParseXML;
import hp.server.controller.NewsFeed.SaveArticle;
import hp.server.model.XMLModels.Article;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.concurrent.*;


/**
 * Created by Tautvilas Simkus on 29/12/2016.
 */
/*This class is responsible for retrieving bbc rss feed*/

public class ParseBBC implements Callable<Integer> {

    private final String url = "https://feeds.bbci.co.uk/news/world/rss.xml?edition=uk";
    private final String nsAtom = "http://www.w3.org/2005/Atom";
    private final String nsContent = "http://purl.org/rss/1.0/modules/content";
    private final String nsMedia = "http://search.yahoo.com/mrss";
    private final String nsDc = "http://purl.org/dc/elements/1.1";

    public ParseBBC() { }


    @Override
    public Integer call() throws Exception {
        Document xmlText = null;
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future f = es.submit(new ParseXML(url));
        try {
            xmlText = (Document) f.get();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch (ExecutionException e) {
            e.printStackTrace();
        }

        NodeList nodeList = xmlText.getElementsByTagName("item");
        int count = 0,articlesSaved = 0;
        for(int i = 0; i <= nodeList.getLength() - 1; i++) {
            Element item = (Element) nodeList.item(i);
            Article article = new Article();
            article.setTitle(item.getElementsByTagName("title").item(0).getTextContent());
            article.setDescription(item.getElementsByTagName("description").item(0).getTextContent());
            article.setLink(item.getElementsByTagName("link").item(0).getTextContent());
            article.setPublishDate(item.getElementsByTagName("pubDate").item(0).getTextContent());
            //Add thumbnail
            NodeList nlThumb = item.getElementsByTagNameNS(nsMedia,"thumbnail");

            if(nlThumb.item(0) != null) {
                article.setThumbnail(nlThumb.item(0).getAttributes().getNamedItem("url").getTextContent());
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

        System.out.println("Number of Articles in BBC feed: "+count);
        return count;
    }
}
