package hp.server.view;

import hp.server.controller.NewsTemplates.ParseBBC;
import hp.server.controller.NewsTemplates.ParseINP;
import hp.server.controller.NewsTemplates.ParseNYT;
import hp.server.model.XMLModels.Article;
import java.util.ArrayList;

/**
 *
 * @author Tautvilas Simkus
 */
public class HPServer {
    
    public ArrayList<Article> articlesList = new ArrayList<Article>();

    public static void parseFeed() {
//        ParseNYT parseNYT = new ParseNYT();
//        parseNYT.start();

//        ParseBBC parseBBC = new ParseBBC();
//        parseBBC.start();
//
//        ParseINP parseINP = new ParseINP();
//        parseINP.start();
    }
    
    public static void main(String[] args) {
        parseFeed();
    }
}
