package hp.server.controller.User;

import hp.server.model.XMLModels.Article.Article;
import hp.server.model.XMLModels.Article.TableArticle;
import hp.server.model.XMLModels.User.TableUser;
import hp.server.model.XMLModels.User.User;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Tautvilas on 09/04/2017.
 */
public class GetUsers
{
    private ArrayList<User> usersList = new ArrayList<User>();

    public GetUsers()
    {

    }

    public ArrayList<User> returnList()
    {
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future f = es.submit(new TableUser());

        try
        {
            usersList = (ArrayList<User>) f.get();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
        }
        return usersList;
    }
}
