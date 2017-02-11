package hp.server.controller.NewsFeed;

import hp.server.model.XMLModels.Article;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;




import static org.jsoup.Jsoup.connect;
public class SaveArticle extends Thread{

    Article article;
    int articlesSaved;

    public SaveArticle(Article article,int articlesSaved) {
        super("SaveArticleThread");
        this.article = article;
        this.articlesSaved = articlesSaved;
    }

    @Override
    public void run() {
        try {
            Document doc = connect("http://t-simkus.com/final_project/saveArticle.php")
                    .data("title", article.getTitle())
                    .data("link", article.getLink())
                    .data("description", article.getDescription())
                    .data("publishDate", article.getPublishDate())
                    .data("credit", article.getCredit())
                    .data("tags", article.getTagsString())
                    .data("thumbnail", article.getThumbnail())
                    .userAgent("Mozilla")
                    .post();
            JSONObject response = new JSONObject(doc.body().text());
            System.out.println(doc.body().text());
            int status = response.getInt("status");
            String msg = response.getString("msg");
            
            if(status == 200) {
                this.articlesSaved++;
                System.out.println("Article added, total added: "+this.articlesSaved);
            }


        }
        catch (IOException e) {
            e.printStackTrace();
        } 
        catch (JSONException ex) {
            ex.printStackTrace();
        }
    }
}
