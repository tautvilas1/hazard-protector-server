package hp.server.controller.NewsFeed;



import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Tautvilas Simkus on 14/02/2016.
 */
public class ParseXML implements Callable<Document> {
    private String lookupLink;

    public ParseXML(String lookupLink) {
        this.lookupLink = lookupLink;
    }

    @Override
    public Document call() throws Exception {
        return Parse();
    }

    public Document Parse() throws InterruptedException, ParserConfigurationException, SAXException {
        Document xmlText = null;
        try {
            xmlText = readXmlFromUrl(lookupLink);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return xmlText;
    }

    /*
    @params: url - string to lookup location
    @return: String object with xml source
     */

    private Document readXmlFromUrl(String url) throws IOException, ParserConfigurationException, SAXException
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();

        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(10000);
        conn.setUseCaches(true);
        conn.addRequestProperty("Content-Type", "text/xml; charset=utf-8");

        InputSource is = new InputSource(conn.getInputStream());
        is.setEncoding("UTF-8");

        return db.parse(is);
    }
}
