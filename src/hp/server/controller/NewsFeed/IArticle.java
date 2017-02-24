package hp.server.controller.NewsFeed;

import hp.server.model.XMLModels.Article;

import java.util.ArrayList;

/**
 * Created by Tautvilas on 23/02/2017.
 */
public interface IArticle {

    public String getThumbnail();

    public void setThumbnail(String thumbnail);

    public String getCredit();

    public void setCredit(String credit);

    public ArrayList<String> getTags();

    public String getTagsString();

    public void setTags(ArrayList<String> tags);

    public String getPublishDate ();

    public void setPublishDate (String pubDate);

    public String getTitle ();

    public void setTitle (String title);

    public String getDescription ();

    public void setDescription (String description);

    public String getLink ();

    public void setLink (String link);

    public String toString();

    public String validate(Article articleParam, ArrayList<Article> articlesList);

}
