package dspm.dc.ufc.br.mynotes;

import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

public class NewNoteActivity extends AppCompatActivity {

    private NoteDAO noteDAO;

    TextView tvDate;
    DialogFragment newDateFrangment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        tvDate = (TextView) findViewById(R.id.tv_date);

        noteDAO = new NoteDAO(this);
    }

    public void addNote(View view) {

        if(newDateFrangment != null)
        {
            GregorianCalendar gCalendar = ((DatePickerFragment)newDateFrangment).getCalendar();
            EditText titleText = (EditText) findViewById(R.id.editTextTitle);
            EditText contentText = (EditText) findViewById(R.id.editTextContent);
            Note note = new Note();
            note.setTitle(titleText.getText().toString());
            note.setContent(contentText.getText().toString());
            note.setDate(gCalendar);
            noteDAO.create(note);


            finish();
        }
        else
        {
            Toast.makeText(this, "Selecione uma data antes de continuar", Toast.LENGTH_SHORT).show();
        }

    }

    public void showDatePickerDialog(View view){
        newDateFrangment = new DatePickerFragment();
        ((DatePickerFragment)newDateFrangment).setTvDate(tvDate);
        newDateFrangment.show(getFragmentManager(), "datePicker");
    }
}
