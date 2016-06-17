package dspm.dc.ufc.br.webdownloader;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ConnectivityManager connManager;

    TextView tvResults;
    EditText etURL;

    boolean conected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResults = (TextView) findViewById(R.id.tv_results);
        etURL = (EditText) findViewById(R.id.et_url);

        conected = checkConnection();
    }

    public boolean checkConnection() {
        connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected())
        {
            Toast.makeText(this, "Conectado a Internet", Toast.LENGTH_SHORT).show();
            return true;
        }
        else
        {
            Toast.makeText(this, "Não foi possível conectar a internet", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void onClickDownload(View view){
        if(conected)
        {
            String url = etURL.getText().toString();
            new DownloaderTask(tvResults).execute(url);
        }

    }
}
