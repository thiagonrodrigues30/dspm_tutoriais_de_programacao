package myapplication.sairamkrishna.example.com.myapplication;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView tv1=null;
    private SensorManager mSensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = (TextView) findViewById(R.id.textView2);
        tv1.setText("Sensores: \n");

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> mList= mSensorManager.getSensorList(Sensor.TYPE_ALL);

        for (int i = 1; i < mList.size(); i++) {
            tv1.setText("Tem sensores");
            tv1.append("\n" + mList.get(i).getName() + "\n" + mList.get(i).getVendor() + "\n" + mList.get(i).getVersion());
            Log.d("Response", "1");
        }
    }
}
