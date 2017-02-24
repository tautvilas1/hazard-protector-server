package hp.server.controller.NewsTemplates;



import hp.server.controller.NewsFeed.ParseXML;
import hp.server.controller.NewsFeed.SaveArticle;
import hp.server.model.XMLModels.Article;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.concurrent.*;


public class ParseNYT implements Callable<Integer> {

    private final String nsAtom = "http://www.w3.org/2005/Atom";
    private final String nsNyt = "http://www.nytimes.com/namespaces/rss/2.0";
    private final String nsMedia = "http://search.yahoo.com/mrss/";
    private final String nsDc = "http://purl.org/dc/elements/1.1/";
    private final String url = "http://rss.nytimes.com/services/xml/rss/nyt/World.xml";

    public ParseNYT()
    {

    }

    public Integer call() throws Exception
    {
        int count = 0;
        Document xmlText;
        ExecutorService es = Executors.newSingleThreadExecutor();

        try
        {
            Future f = es.submit(new ParseXML(url));
            xmlText = (Document) f.get();

            NodeList nodeList = xmlText.getElementsByTagName("item");

            int articlesSaved = 0;

            for(int i = 0; i <= nodeList.getLength() - 1; i++)
            {
                Element item = (Element) nodeList.item(i);

                Article article = new Article();
                article.setTitle(item.getElementsByTagName("title").item(0).getTextContent());
                article.setDescription(item.getElementsByTagName("description").item(0).getTextContent());
                article.setLink(item.getElementsByTagName("link").item(0).getTextContent());
                article.setPublishDate(item.getElementsByTagName("pubDate").item(0).getTextContent());

                //Add all the categories
                for(int b = 0; b < item.getElementsByTagName("category").getLength();b++) {
                    article.getTags().add(item.getElementsByTagName("category").item(b).getTextContent());
                }


                //Add thumbnail
                NodeList nlThumb = item.getElementsByTagNameNS(nsMedia,"content");

                if(nlThumb.item(0) != null) {
                    article.setThumbnail(nlThumb.item(0).getAttributes().getNamedItem("url").getTextContent());
                }

                //Add credits
                if(item.getElementsByTagName("media:credit").item(0) != null) {
                    article.setCredit(item.getElementsByTagName("media:credit").item(0).getTextContent());
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

            System.out.println("Articles saved from NYT:"+count);
        }

        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        catch (ExecutionException e)
        {
            e.printStackTrace();
        }

        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return count;
    }


}
