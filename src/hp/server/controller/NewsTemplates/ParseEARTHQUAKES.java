package hp.server.controller.NewsTemplates;



import hp.server.controller.NewsFeed.ParseXML;
import hp.server.controller.NewsFeed.SaveArticle;
import hp.server.model.XMLModels.Article.Article;
import hp.server.model.XMLModels.Common.Response;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.concurrent.*;


public class ParseEARTHQUAKES implements Callable<Integer>
{

    private final String url = "http://earthquake-report.com/feeds/recent-eq";

    public ParseEARTHQUAKES()
    {

    }

    public Integer call()
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
                article.setSource("EARTHQUAKES");
                article.setTitle(item.getElementsByTagName("title").item(0).getTextContent());
                article.setDescription("none");
                article.setLink(item.getElementsByTagName("link").item(0).getTextContent());
                article.setPublishDate(item.getElementsByTagName("pubDate").item(0).getTextContent());
                article.setThumbnail("http://odontologijos-erdve.lt/hazardprotector/images/earthquake.jpg");


                ExecutorService esInner = Executors.newSingleThreadExecutor();
                Future fInner = esInner.submit(new SaveArticle(article,articlesSaved));
                try
                {
                    Response innerResponse = (Response) fInner.get();

                    if(innerResponse.getStatus() == 200)
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

            System.out.println("Articles saved from Earthquakes feed:"+count);
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
