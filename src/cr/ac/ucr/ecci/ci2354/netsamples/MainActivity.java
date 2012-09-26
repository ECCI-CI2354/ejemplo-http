package cr.ac.ucr.ecci.ci2354.netsamples;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.app.DownloadManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

	public static final String TAG = MainActivity.class.getName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void parseDom(View view) {
		// ParseFileTask task = new ParseFileTask(new DomDataParser());
		// task.execute("ejemplo.xml");
		ParseFileTask task = new ParseFileTask(new GsonUserParser());
		task.execute("ejemplo.json");
	}

	public void parseXPP(View view) {
		ParseFileTask task = new ParseFileTask(new XPPDataParser());
		task.execute("ejemplo.xml");
	}

	public void loadImage(View view) {
		ImageView imageView = (ImageView) findViewById(R.id.image);
		new DownloadImageTask(imageView)
				.execute("http://bleyer.org/pacoblaze/duke.gif");
	}

	public void downloadManager(View view) {
		DownloadManager manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

		Uri uri = Uri
				.parse("http://www.nacion.com/Generales/RSS/EdicionRss.aspx?section=elpais");
		DownloadManager.Request request = new DownloadManager.Request(uri);
		long id = manager.enqueue(request);

	}

	class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView view;

		public DownloadImageTask(ImageView view) {
			this.view = view;
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			File f;
			try {
				f = HTTPClientFactory.getImage(MainActivity.this, params[0]);
				return BitmapFactory.decodeFile(f.getAbsolutePath());
			} catch (IOException e) {
				Log.e("", e.getMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			if (result != null) {
				view.setImageBitmap(result);
			}
		}

	}

	class ParseFileTask extends AsyncTask<String, Void, User> {

		DataParser parser;

		public ParseFileTask(DataParser parser) {
			super();
			this.parser = parser;
		}

		@Override
		protected User doInBackground(String... params) {
			String filename = params[0];

			try {
				InputStream in = getAssets().open(filename);
				return parser.parseUser(in);
			} catch (IOException e) {
				Log.e(TAG, e.getMessage(), e);
			} catch (DataParseException e) {
				Log.e(TAG, e.getMessage(), e);
			}
			return null;
		}

		@Override
		protected void onPostExecute(User result) {
			if (result != null) {
				Log.d(TAG, "User: " + result.toString());
			} else {
				Toast.makeText(MainActivity.this, "Unable to load user",
						Toast.LENGTH_SHORT).show();
			}
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

	}
}
