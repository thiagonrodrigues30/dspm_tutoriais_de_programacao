package dspm.dc.ufc.br.proximityapp;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager;

    /*
    PendingIntent pIntent1 = null;
    PendingIntent pIntent2 = null;
    PendingIntent pIntent3 = null;
    PendingIntent pIntent4 = null;

    TextView tvBtn;
    TextView tvP;
    TextView tvPonto;
    */

    EditText etLatitude;
    EditText etLongitude;

    PendingIntent pIntentProx = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        tvBtn = (TextView) findViewById(R.id.tv_btn);
        tvP = (TextView) findViewById(R.id.tv_pintent);
        tvPonto = (TextView) findViewById(R.id.tv_ponto);
        */

        etLatitude = (EditText) findViewById(R.id.et_latitude);
        etLongitude = (EditText) findViewById(R.id.et_longitude);

        montarLocationService();
    }

    private void montarLocationService() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        // check if enabled and if not send user to the GSP settings
        // Better solution would be to display a dialog and suggesting to
        // go to the settings
        if (!enabled) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }

        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, false);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        Location location = locationManager.getLastKnownLocation(provider);

    }

    private void registrarPonto() {
        Double[] getLatLong = getLatLgn();

        Double latitude = getLatLong[0];
        Double longitude = getLatLong[1];

        //PendingIntent pIntentTemp = null;

        /*
        if (ponto == 1)
        {
            pIntentTemp = pIntent1;
            locationManager.removeProximityAlert(pIntent1);
            tvP.setText("pIntent 1");
        }
        else if (ponto == 2)
        {
            pIntentTemp = pIntent2;
            locationManager.removeProximityAlert(pIntent2);
            tvP.setText("pIntent 2");
        }
        else if (ponto == 3)
        {
            pIntentTemp = pIntent3;
            locationManager.removeProximityAlert(pIntent3);
            tvP.setText("pIntent 3");
        }
        else if (ponto == 4)
        {
            pIntentTemp = pIntent4;
            locationManager.removeProximityAlert(pIntent4);
            tvP.setText("pIntent 4");
        }
        */

        // Permission Check
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        // This intent will call the activity ProximityReceiver
        Intent proximityIntent = new Intent();
        proximityIntent.setAction("br.ufc.dc.dspm.proximityapp.PROXIMITY_ACTION");
        //String stringPonto = String.valueOf(ponto);
        //proximityIntent.putExtra("PONTO", stringPonto);
        //tvPonto.setText(stringPonto);

        // Creating a pending intent which will be invoked by LocationManager when the specified region is
        // entered or exited
        if (pIntentProx != null) {
            locationManager.removeProximityAlert(pIntentProx);
        }

        //pIntentTemp = PendingIntent.getActivity(getBaseContext(), 0, proximityIntent, Intent.FILL_IN_ACTION);
        pIntentProx = PendingIntent.getBroadcast(this, 0, proximityIntent, 0);

        // Setting proximity alert
        // The pending intent will be invoked when the device enters or exits the region 20 meters
        // away from the marked point
        // The -1 indicates that, the monitor will not be expired
        locationManager.addProximityAlert(latitude, longitude, 20, -1, pIntentProx);

    }

    private Double[] getLatLgn() {
        Double[] res = new Double[2];

       // res[0] = -3.731781;
        //res[1] =  -38.588463;

        res[0] = Double.parseDouble(etLatitude.getText().toString());
        res[0] = Double.parseDouble(etLongitude.getText().toString());

        return res;
    }

    public void onClickRegistrar(View view) {
        registrarPonto();
    }
}
