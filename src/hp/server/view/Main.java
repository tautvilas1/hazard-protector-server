package hp.server.view;

import java.util.Timer;

/**
 * Created by Tautvilas on 24/03/2017.
 */
public class Main
{
    public static void main(String[] args)
    {
        HPServer server = new HPServer();
        Timer timer = new Timer();
        timer.schedule(server,0,300000);
    }
}
