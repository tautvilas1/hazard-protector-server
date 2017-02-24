package hp.server.model.XMLModels;

import hp.server.controller.NewsFeed.IArticle;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Tautvilas Simkus on 16/10/2016.
 */
public class Article implements IArticle
{
    private String publishDate = "";
    private String title = "";
    private String description = "";
    private String link = "";
    private String thumbnail = "";
    private String credit = "";
    private ArrayList<String> tags = new ArrayList<String>();

    public Article() {

    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public String getTagsString() {
        String result = "|";
        for(int i = 0; i < tags.size();i++) {
            result += tags.get(i) + ",";
        }
        result += "|";
        return result;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public String getPublishDate ()
    {

        return publishDate;
    }

    public void setPublishDate (String pubDate)
    {

        this.publishDate = pubDate;
    }

    public String getTitle ()
    {

        return title;
    }

    public void setTitle (String title)
    {

        this.title = title;
    }

    public String getDescription ()
    {

        return description;
    }

    public void setDescription (String description)
    {

        this.description = description;
    }

    public String getLink ()
    {

        return link;
    }

    public void setLink (String link)
    {

        this.link = link;
    }

    public String toString() {
        return "Title: "+ title + "\n"+
                "Link: "+ link + "\n"+
                "Description: " +description + "\n"+
                "Publish date: " + publishDate + "\n"+
                "Thumbnail: " +thumbnail + "\n"+
                "Credit: " + credit + "\n"+
                "Tags: " + getTagsString();
    }

    public String validate(Article articleParam, ArrayList<Article> articlesList) {
        String result = "Valid";
        if(articlesList.size() > 0) {
            for(Article article : articlesList) {
                if(article.getTitle().equals(articleParam.getTitle())) {
                    result = "Invalid";
                    break;
                }
            }
        }
        else {
            result = "No Articles In The List";
        }
        return result;
    }


}