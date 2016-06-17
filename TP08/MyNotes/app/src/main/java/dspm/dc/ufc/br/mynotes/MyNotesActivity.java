package dspm.dc.ufc.br.mynotes;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

public class MyNotesActivity extends Activity {
    private NoteDAO noteDAO;

    TextView tvDateFilterBeg;
    TextView tvDateFilterEnd;
    DialogFragment newDateFrangmentBeg = null;
    DialogFragment newDateFrangmentEnd = null;
    LinearLayout llFiltro;

    ListView listView;
    NotesListView notesListView;

    boolean filter = false;
    long dateBeg;
    long dateEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notes);

        llFiltro = (LinearLayout) findViewById(R.id.filtro);
        llFiltro.setVisibility(View.GONE);

        tvDateFilterBeg = (TextView) findViewById(R.id.tv_date_inicio);
        tvDateFilterEnd = (TextView) findViewById(R.id.tv_date_fim);

        listView = (ListView) findViewById(R.id.listview);

        noteDAO = new NoteDAO(this);

        notesListView = new NotesListView(listView, this, noteDAO);
    }

    public void onClickNovaNota(View view){
        Intent intent = new Intent(this, NewNoteActivity.class);
        startActivity(intent);
    }

    public void onClickComFiltro(View view){
        llFiltro.setVisibility(View.VISIBLE);
    }

    public void onClickSemFiltro(View view){
        llFiltro.setVisibility(View.GONE);
        filter = false;
        updateListView();
    }

    public void showDatePickerDialogFilterBeg(View view){
        newDateFrangmentBeg = new DatePickerFragment();
        ((DatePickerFragment)newDateFrangmentBeg).setTvDate(tvDateFilterBeg);
        newDateFrangmentBeg.show(getFragmentManager(), "datePicker");
    }

    public void showDatePickerDialogFilterEnd(View view){
        newDateFrangmentEnd = new DatePickerFragment();
        ((DatePickerFragment)newDateFrangmentEnd).setTvDate(tvDateFilterEnd);
        newDateFrangmentEnd.show(getFragmentManager(), "datePicker");
    }

    public void onClickFilter(View view){

        if((newDateFrangmentBeg == null) || (newDateFrangmentEnd == null))
        {
            Toast.makeText(this, "Selecione as data antes de continuar", Toast.LENGTH_SHORT).show();
        }
        else
        {
            dateBeg = ((DatePickerFragment)newDateFrangmentBeg).getCalendar().getTimeInMillis();
            dateEnd = ((DatePickerFragment)newDateFrangmentEnd).getCalendar().getTimeInMillis();

            filter = true;

            updateListView();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateListView();
    }

    private void updateListView(){

        if(filter)
        {
            listView.post(new Runnable() {
                @Override
                public void run() {
                    notesListView.setListView(1, dateBeg, dateEnd);
                }
            });
        }
        else
        {
            listView.post(new Runnable() {
                @Override
                public void run() {
                    notesListView.setListView();
                }
            });
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        updateListView();
    }
}
