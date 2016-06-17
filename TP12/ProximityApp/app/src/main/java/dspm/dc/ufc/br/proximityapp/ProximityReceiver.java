package dspm.dc.ufc.br.proximityapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.widget.Toast;

/**
 * Created by Thiago on 14/06/2016.
 */
public class ProximityReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        boolean proximity_entering = intent.getBooleanExtra(LocationManager.KEY_PROXIMITY_ENTERING, false);

        CharSequence text;

        if(proximity_entering)
        {
            text = "Dispositivo DENTRO da área informada";
        }
        else
        {
            text = "Dispositivo SAINDO da área informada";
        }

        Toast.makeText(context, text , Toast.LENGTH_LONG).show();
    }
}
