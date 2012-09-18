package cr.ac.ucr.ecci.ci2354.netsamples;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.util.Log;

public class DomDataParser implements DataParser {
    public static final String TAG = DomDataParser.class.getName();

    @Override
    public User parseUser(InputStream in) throws DataParseException {
        User user = new User();
        try {
            Document doc = null;
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            doc = db.parse(in);
            Node root = doc.getFirstChild();

            // id attribute
            Node nodeUserId = root.getAttributes().getNamedItem("id");
            int userId = Integer.parseInt(nodeUserId.getNodeValue());
            Log.d(TAG, "User with id: " + userId);
            user.setId(userId);

            //FirstName
            String firstname = getTagValue("firstname", (Element) root);
            Log.d(TAG, "User firstname: " + firstname);
            user.setFirstname(firstname);

            //lastName
            String lastname = getTagValue("lastname", (Element) root);
            Log.d(TAG, "User lastname: " + lastname);
            user.setLastname(lastname);

            //Phone list
            String[] phonelist = getTagValues("phonelist", (Element) root).toArray(new String[] {});
            Log.d(TAG, "User phone list size: " + phonelist.length);
            user.setPhonelist(phonelist);

        } catch (ParserConfigurationException e) {
            Log.e(TAG, e.getMessage(), e);
            throw new DataParseException(e);
        } catch (SAXException e) {
            Log.e(TAG, e.getMessage(), e);
            throw new DataParseException(e);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
            throw new DataParseException(e);
        }
        return user;
    }

    private static String getTagValue(String sTag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();

        Node nValue = (Node) nlList.item(0);
        return nValue.getNodeValue();
    }

    private static List<String> getTagValues(String sTag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();

        List<String> values = new LinkedList<String>();
        for (int i = 0; i < nlList.getLength(); i++) {
            Node nValue = (Node) nlList.item(i);
            if (nValue.getNodeType() == Node.ELEMENT_NODE) {
                values.add(nValue.getNodeValue());
            }
        }
        return values;
    }
}
