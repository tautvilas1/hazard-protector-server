package hp.server.controller.Search;

import hp.server.model.XMLModels.Common.FeedCategories;
import hp.server.model.XMLModels.Search.Keywords;
import hp.server.model.XMLModels.User.User;

import java.util.ArrayList;
import java.util.concurrent.Callable;

/**
 * Created by Tautvilas on 28/03/2017.
 */
public class KeywordMaker implements Callable<Keywords>
{
    private User user;
    private Keywords keywords;
    private FeedCategories feedCategories;

    public KeywordMaker(User user)
    {
        this.user = user;
        this.keywords = new Keywords();
        this.feedCategories = new FeedCategories();
    }
    @Override
    public Keywords call()
    {
        addPreferenceKeywords();
        addLocationKeywords();
        return keywords;
    }

    private void addPreferenceKeywords()
    {
        for(String category : feedCategories.getCategories())
        {
            if(user.getPreferences().contains(category))
            {
                keywords.getGoodKeywords().add(category);
            }
            else
            {
                keywords.getBadKeywords().add(category);
            }
        }
    }

    private void addLocationKeywords()
    {
        ArrayList<String> address = user.getAddressComponents();
        for(String keyword : address)
        {
            keywords.getGoodKeywords().add(keyword);
        }
    }
}
