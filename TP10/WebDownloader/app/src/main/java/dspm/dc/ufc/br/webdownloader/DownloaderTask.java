package dspm.dc.ufc.br.webdownloader;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Thiago on 14/06/2016.
 */
public class DownloaderTask extends AsyncTask<String, String, String> {
    private TextView textView;

    public DownloaderTask(TextView textView) {
        this.textView = textView;
    }

    protected String doInBackground(String... params) {
        try {
            String link = (String) params[0];
            URL url = new URL(link);

            // perform connection and read content
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();

            InputStream is = conn.getInputStream();
            BufferedReader reader;
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String data = null;
            String content = "";
            while ((data = reader.readLine()) != null) {
                content += data + "\n";
            }


            return content;
        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        }
    }

    protected void onPostExecute(String result) {
        this.textView.setText(result);
    }
}
