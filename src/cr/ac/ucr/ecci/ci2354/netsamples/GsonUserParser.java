package cr.ac.ucr.ecci.ci2354.netsamples;

import java.io.InputStream;
import java.io.InputStreamReader;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class GsonUserParser implements DataParser {

	@Override
	public User parseUser(InputStream in) throws DataParseException {
		Gson gson = new Gson();
		User user = null;
		try {
			user = gson.fromJson(new InputStreamReader(in), User.class);
		} catch (JsonSyntaxException e) {
			Log.e("", e.getMessage());
			throw new DataParseException(e);
		} catch (JsonIOException e) {
			Log.e("", e.getMessage());
			throw new DataParseException(e);
		}
		return user;
	}

}
