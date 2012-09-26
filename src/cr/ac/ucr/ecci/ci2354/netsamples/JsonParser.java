package cr.ac.ucr.ecci.ci2354.netsamples;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class JsonParser implements DataParser {

	@Override
	public User parseUser(InputStream in) throws DataParseException {
		User user = null;
		StringBuilder builder = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		char[] buffer = new char[8 * 1024];
		try {
			user = new User();
			while (reader.ready()) {
				reader.read(buffer);
				builder.append(buffer);
			}
			JSONObject jsonObject = new JSONObject(builder.toString());

			user.setId(jsonObject.getInt("id"));

			user.setFirstname(jsonObject.getString("firstname"));
			user.setLastname(jsonObject.getString("lastname"));

			JSONArray phoneList = jsonObject.getJSONArray("phonelist");
			String[] phones = new String[phoneList.length()];
			for (int i = 0; i < phoneList.length(); i++) {
				phones[i] = phoneList.getString(i);
			}
			user.setPhonelist(phones);
			
			JSONObject location = jsonObject.getJSONObject("location");
			

		} catch (IOException e) {
			Log.e("", e.getMessage());
			throw new DataParseException(e);
		} catch (JSONException e) {
			Log.e("", e.getMessage());
			throw new DataParseException(e);
		}

		return user;
	}

}
