package dspm.dc.ufc.br.mynotes;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Thiago on 15/06/2016.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private TextView tvDate;
    private GregorianCalendar gCalendar = null;
    //private Calendar calendarMain;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int year  = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, final int year, final int month, final int day){
        this.tvDate.post(new Runnable() {
            @Override
            public void run() {
                tvDate.setText(day + "/" + (month + 1) + "/"  + year);
            }
        });

        gCalendar = new GregorianCalendar(year, month + 1, day);
    }

    public void setTvDate(TextView dateView) {
        this.tvDate = dateView;
    }

    public GregorianCalendar getCalendar() {
        return gCalendar;
    }
}
