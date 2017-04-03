package hp.server.model.XMLModels.Common;

import java.util.ArrayList;

/**
 * Created by Tautvilas on 03/04/2017.
 */
public class FeedCategories
{

    public ArrayList<String> categories = new ArrayList<String>();

    public FeedCategories()
    {
        categories.add("terror");
        categories.add("flood");
        categories.add("war");
        categories.add("earthquake");
        categories.add("political");
        categories.add("criminal");
    }

    public ArrayList<String> getCategories()
    {
        return categories;
    }

    public void setCategories(ArrayList<String> categories)
    {
        this.categories = categories;
    }
}
