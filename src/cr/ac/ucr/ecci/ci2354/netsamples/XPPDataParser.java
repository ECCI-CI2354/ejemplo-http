package cr.ac.ucr.ecci.ci2354.netsamples;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

public class XPPDataParser implements DataParser {
    private static enum TagFlag {
        USER, FIRSTNAME, LASTNAME, PHONELIST, PHONE, BIRTHDATE, WEIGHT, HEIGHT, LOCATION, CITY, STATE, COUNTRY
    }

    @Override
    public User parseUser(InputStream in) throws DataParseException {
        User user = new User();

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            /*
             * NOTE: If an input encoding string is passed, it MUST be used. 
             * Otherwise, if inputEncoding is null, the parser SHOULD try to determine 
             * input encoding following XML 1.0 specification (see below). 
             * If encoding detection is supported then following feature 
             * http://xmlpull.org/v1/doc/features.html#detect-encoding 
             * MUST be true amd otherwise it must be false
             */

            xpp.setInput(in, null);

            int eventType = xpp.getEventType();
            TagFlag currentTag = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    if ("user".equals(xpp.getName())) {
                        currentTag = TagFlag.USER;
                        String idValue = xpp.getAttributeValue(null, "id");
                        Log.d(TAG, "User with id: " + idValue);
                        user.setId(Integer.valueOf(idValue));
                    } else if ("firstname".equals(xpp.getName())) {
                        currentTag = TagFlag.FIRSTNAME;

                    } else if ("lastname".equals(xpp.getName())) {
                        currentTag = TagFlag.LASTNAME;

                    } else if ("phonelist".equals(xpp.getName())) {
                        currentTag = TagFlag.PHONELIST;
                        String[] phonelist = getPhoneList(xpp);
                        currentTag = null;
                        user.setPhonelist(phonelist);
                    }
                } else if (eventType == XmlPullParser.TEXT && currentTag != null) {
                    String content = xpp.getText();
                    Log.d(TAG, "Content: " + content + " on tag: " + currentTag);
                    switch (currentTag) {
                    case FIRSTNAME:
                        user.setFirstname(content);
                        break;
                    case LASTNAME:
                        user.setLastname(content);
                    default:
                        break;
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    currentTag = null;
                }
                eventType = xpp.next();
            }
        } catch (XmlPullParserException e) {
            Log.e(TAG, e.getMessage(), e);
            throw new DataParseException(e);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
            throw new DataParseException(e);
        }

        return user;
    }

    private String[] getPhoneList(XmlPullParser xpp) throws XmlPullParserException, IOException {
        List<String> phones = new LinkedList<String>();
        int eventType = xpp.next();
        boolean isPhone = false;
        while (!"phonelist".equals(xpp.getName())) {
            if (eventType == XmlPullParser.START_TAG) {
                if ("phone".equals(xpp.getName())) {
                    isPhone = true;
                }
            } else if (eventType == XmlPullParser.TEXT && isPhone) {
                String content = xpp.getText();
                Log.d(TAG, "Phone: " + content);
                phones.add(content);
            } else if (eventType == XmlPullParser.END_TAG) {
                isPhone = false;
            }

            eventType = xpp.next();
        }
        return phones.toArray(new String[] {});
    }
}
