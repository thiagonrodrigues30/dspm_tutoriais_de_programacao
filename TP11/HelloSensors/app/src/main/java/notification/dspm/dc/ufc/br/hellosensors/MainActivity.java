package notification.dspm.dc.ufc.br.hellosensors;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sMgr;

    TextView tvTemp;
    TextView tvLum;
    TextView tvPressao;
    TextView tvUmid;
    TextView tvStatus;

    Sensor temperaturaSensor;
    Sensor luminosidadeSensor;
    Sensor pressaoSensor;
    Sensor umidadeSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStatus = (TextView) findViewById(R.id.tv_status);

        tvTemp = (TextView) findViewById(R.id.tv_temperatura);
        tvLum = (TextView) findViewById(R.id.tv_luminosidade);
        tvPressao = (TextView) findViewById(R.id.tv_pressao);
        tvUmid = (TextView) findViewById(R.id.tv_umidade);

        montarSensores();
    }

    private void montarSensores(){
        sMgr = (SensorManager) getSystemService(SENSOR_SERVICE);

        temperaturaSensor = sMgr.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        luminosidadeSensor = sMgr.getDefaultSensor(Sensor.TYPE_LIGHT);
        pressaoSensor = sMgr.getDefaultSensor(Sensor.TYPE_PRESSURE);
        umidadeSensor = sMgr.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);

        final String label = "Sensor Ausente";

        if (sMgr.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) == null)
        {
            tvTemp.post(new Runnable() {
                @Override
                public void run() {
                    tvTemp.setText(label);
                }
            });
        }

        if (sMgr.getDefaultSensor(Sensor.TYPE_LIGHT) == null)
        {
            tvLum.post(new Runnable() {
                @Override
                public void run() {
                    tvLum.setText(label);
                }
            });
        }

        if (sMgr.getDefaultSensor(Sensor.TYPE_PRESSURE) == null)
        {
            tvPressao.post(new Runnable() {
                @Override
                public void run() {
                    tvPressao.setText(label);
                }
            });
        }

        if (sMgr.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY) == null)
        {
            tvUmid.post(new Runnable() {
                @Override
                public void run() {
                    tvUmid.setText(label);
                }
            });
        }


    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }
    public void onSensorChanged(SensorEvent event) {

        if(event.sensor == temperaturaSensor)
        {
            final float temp = event.values[0];
            tvTemp.post(new Runnable() {
                @Override
                public void run() {
                    tvTemp.setText(String.valueOf(temp) + " ºC");
                }
            });
        }
        else if(event.sensor == luminosidadeSensor)
        {
            final float temp = event.values[0];
            tvLum.post(new Runnable() {
                @Override
                public void run() {
                    tvLum.setText(String.valueOf(temp) + " lux");
                }
            });
        }
        else if(event.sensor == pressaoSensor)
        {
            final float temp = event.values[0];
            tvPressao.post(new Runnable() {
                @Override
                public void run() {
                    tvPressao.setText(String.valueOf(temp) + " hPa");
                }
            });
        }
        else if(event.sensor == umidadeSensor)
        {
            final float temp = event.values[0];
            tvUmid.post(new Runnable() {
                @Override
                public void run() {
                    tvUmid.setText(String.valueOf(temp) + " %");
                }
            });
        }
    }

    public void onClickLer(View view){
        tvStatus.post(new Runnable() {
            @Override
            public void run() {
                tvStatus.setText("Lendo Sensores...");
                tvStatus.setTextColor(Color.parseColor("#B22222"));
            }
        });

        sMgr.registerListener(this, temperaturaSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sMgr.registerListener(this, luminosidadeSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sMgr.registerListener(this, pressaoSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sMgr.registerListener(this, umidadeSensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    public void onClickParar(View view){
        tvStatus.post(new Runnable() {
            @Override
            public void run() {
                tvStatus.setText("Aguardando");
                tvStatus.setTextColor(Color.parseColor("#4682B4"));
            }
        });

        sMgr.unregisterListener(this);
    }
}
