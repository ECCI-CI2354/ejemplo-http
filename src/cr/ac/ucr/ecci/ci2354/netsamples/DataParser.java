package cr.ac.ucr.ecci.ci2354.netsamples;

import java.io.InputStream;

/**
 * Defines methods to parse input stream into Java objects
 * 
 * @author Franklin Garcia
 *
 */
public interface DataParser {
    public static final String TAG = "DataParser";
    
    /**
     * Gets an user object from InputStream
     * @param in
     * @return
     */
    public User parseUser(InputStream in) throws DataParseException;
}
