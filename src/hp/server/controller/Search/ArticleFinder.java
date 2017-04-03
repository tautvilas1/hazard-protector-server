package hp.server.controller.Search;

import hp.server.model.XMLModels.Article.Article;
import hp.server.model.XMLModels.Search.Keywords;

import java.util.ArrayList;
import java.util.concurrent.Callable;

/**
 * Created by Tautvilas on 28/03/2017.
 */
public class ArticleFinder implements Callable<ArrayList>
{
    private Keywords keywords;
    private ArrayList<Article> articlesList;

    public ArrayList<Article> articlesListFiltered = new ArrayList<Article>();

    public ArticleFinder(Keywords keywords, ArrayList<Article> articlesList)
    {
        this.keywords = keywords;
        this.articlesList = articlesList;
    }

    @Override
    public ArrayList call() throws Exception
    {
        for(Article article: articlesList)
        {
            for(String goodKeyword : keywords.getGoodKeywords())
            {
                if(article.toString().toLowerCase().contains(goodKeyword.toLowerCase()))
                {
                    articlesListFiltered.add(article);
                    break;
                }
            }

            for(String badKeyword : keywords.getBadKeywords())
            {
                if(article.toString().toLowerCase().contains(badKeyword.toLowerCase()))
                {
                    break;
                }
            }
        }
        return articlesListFiltered;
    }
}