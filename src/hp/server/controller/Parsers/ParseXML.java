package hp.server.controller.Parsers;



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
public class ParseXML implements Callable<Document>
{
    private String lookupLink;

    public ParseXML(String lookupLink) {
        this.lookupLink = lookupLink;
    }

    @Override
    public Document call()
    {
        return Parse();
    }

    public Document Parse()
    {
        Document xmlText = readXmlFromUrl(lookupLink);
        return xmlText;
    }

    /*
    @params: url - string to lookup location
    @return: String object with xml source
     */

    private Document readXmlFromUrl(String url)
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder db = null;
        Document document = null;

        try
        {
            db = dbf.newDocumentBuilder();
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(10000);
            conn.setUseCaches(true);
            conn.addRequestProperty("Content-Type", "text/xml; charset=utf-8");
            InputSource is = null;
            is = new InputSource(conn.getInputStream());
            is.setEncoding("UTF-8");
            document = db.parse(is);
        }
        catch (SAXException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ParserConfigurationException e)
        {
            e.printStackTrace();
        }
        return document;
    }
}
