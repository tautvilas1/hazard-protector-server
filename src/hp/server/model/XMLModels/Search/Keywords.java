package hp.server.model.XMLModels.Search;

import java.util.ArrayList;

/**
 * Created by Tautvilas on 03/04/2017.
 */
public class Keywords
{
    public ArrayList<String> goodKeywords = new ArrayList<>();
    public ArrayList<String> badKeywords = new ArrayList<>();

    public Keywords()
    {

    }

    public ArrayList<String> getGoodKeywords()
    {
        return goodKeywords;
    }

    public void setGoodKeywords(ArrayList<String> goodKeywords)
    {
        this.goodKeywords = goodKeywords;
    }

    public ArrayList<String> getBadKeywords()
    {
        return badKeywords;
    }

    public void setBadKeywords(ArrayList<String> badKeywords)
    {
        this.badKeywords = badKeywords;
    }
}
