package hp.server.controller.NewsTemplates;



import hp.server.controller.NewsFeed.ParseXML;
import hp.server.controller.NewsFeed.SaveArticle;
import hp.server.model.XMLModels.Article.Article;
import hp.server.model.XMLModels.Common.Response;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ParseCBC implements Callable<Integer>
{

    private final String nsCbc = "http://www.cbc.ca/rss/cbc";
    private final String url = "http://www.cbc.ca/cmlink/rss-world";

    public ParseCBC()
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
                System.out.println(item.toString());
                Article article = new Article();
                article.setTitle(item.getElementsByTagName("title").item(0).getTextContent());
                article.setSource("CBC");

                String descriptionAndImg = item.getElementsByTagName("description").item(0).getTextContent();
                final Pattern pattern = Pattern.compile("<p>(.+?)</p>");
                final Matcher matcher = pattern.matcher(descriptionAndImg);
                String description = "";
                if(matcher.find())
                {
                    description = matcher.group(1);
                }
                article.setDescription(description);

                int start = descriptionAndImg.indexOf("src=\'") + 5;
                int end = descriptionAndImg.indexOf("\'", start);
                String thumbnail = descriptionAndImg.substring(start, end);
                article.setThumbnail(thumbnail);

                article.setLink(item.getElementsByTagName("link").item(0).getTextContent());
                article.setPublishDate(item.getElementsByTagName("pubDate").item(0).getTextContent());
                String tags = item.getElementsByTagName("category").item(0).getTextContent();

                String[] categories = tags.split("/");
                ArrayList<String> cats = new ArrayList<String>();
                for(int b = 0; b < categories.length; b++)
                {
                    cats.add(categories[b]);
                }
                article.setTags(cats);


                //Add credits
                if(item.getElementsByTagName("author").item(0) != null) {
                    article.setCredit(item.getElementsByTagName("author").item(0).getTextContent());
                }

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

            System.out.println("Articles saved from CBC:"+count);
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
