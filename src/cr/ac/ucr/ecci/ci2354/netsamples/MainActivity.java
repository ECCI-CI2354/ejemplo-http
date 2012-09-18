package cr.ac.ucr.ecci.ci2354.netsamples;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
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
        ParseFileTask task = new ParseFileTask( new DomDataParser() );
        task.execute("ejemplo.xml");
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
            if (result != null){
                
            } else {
                Toast.makeText(MainActivity.this, "Unable to load user", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        
        

    }
}
