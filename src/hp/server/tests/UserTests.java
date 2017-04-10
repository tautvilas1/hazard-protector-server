package hp.server.tests;

import hp.server.controller.NewsFeed.SaveArticle;
import hp.server.controller.User.GetUsers;
import hp.server.model.XMLModels.Common.Response;
import hp.server.model.XMLModels.User.TableUser;
import hp.server.model.XMLModels.User.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Created by Tautvilas on 09/04/2017.
 */
public class UserTests
{
    @Test
    public void Get_Users_From_Database()
    {
        GetUsers getUsers = new GetUsers();
        ArrayList<User> usersList = getUsers.returnList();
        System.out.println("Users list size: "+usersList.size());
        assertEquals(true,usersList.size() > 0);
    }

    @Test
    public void Save_User_Hazard_Articles()
    {
        User user = new User();
        String hazardousArticles = "1,9,13,28";
        user.setHazardArticles(hazardousArticles);
        user.setId(14);

        Response response = user.saveHazardArticles();
        System.out.println(response.toString());
        assertEquals(200,response.getStatus());
    }

    @Test
    public void Send_Notification_To_User()
    {
        User user = new User();
        user.setGcm_id("eVC_76I7ig4");
        user.setRegistrationId("eVC_76I7ig4:APA91bEzRn24s-3XClHe6r61HGAG7m5UTWY6dyfcr-B6gFpwE8Nr1-R-Ct_zFRUfJXn-JTlhvKDBtY-roWNIlQoxSYtJMqHH32SgGGXzJbHcXu-I6lrO2P5ngj2XlvCcpP3W3zZ1Fg8x");
        Response response = user.sendPushNotification();
        System.out.println(response.toString());
        assertEquals(200,response.getStatus());
    }


}
