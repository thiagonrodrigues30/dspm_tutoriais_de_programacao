package dspm.dc.ufc.br.mynotes;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class EditNoteActivity extends AppCompatActivity {

    private NoteDAO noteDAO;

    TextView tvDate;
    DialogFragment newDateFrangment = null;

    EditText titleText;
    EditText contentText;

    private int id;
    private String title;
    private String content;
    private GregorianCalendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        titleText = (EditText) findViewById(R.id.editTextTitle);
        contentText = (EditText) findViewById(R.id.editTextContent);

        Intent intent = getIntent();

        id = intent.getIntExtra("ID", 0);
        title = intent.getStringExtra("TITLE");
        content = intent.getStringExtra("CONTENT");

        calendar = new GregorianCalendar();
        calendar.setTimeInMillis(intent.getLongExtra("DATE", 0));

        tvDate = (TextView) findViewById(R.id.tv_date);

        noteDAO = new NoteDAO(this);

        montarDados();
    }

    private void montarDados(){
        titleText.setText(title);
        contentText.setText(content);

        String formData = calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.YEAR);
        tvDate.setText(formData);
    }

    public void onClickUpdate(View view) {

        if(newDateFrangment != null)
        {
            calendar = ((DatePickerFragment)newDateFrangment).getCalendar();
        }


        Note note = new Note();
        note.setId(id);
        note.setTitle(titleText.getText().toString());
        note.setContent(contentText.getText().toString());
        note.setDate(calendar);
        //noteDAO.create(note);
        noteDAO.update(note);

        finish();
    }

    public void onClickDelete(View view){
        noteDAO.delete(id);
        finish();
    }

    public void onClickVoltar(View view){
        finish();
    }

    public void showDatePickerDialog(View view){
        newDateFrangment = new DatePickerFragment();
        ((DatePickerFragment)newDateFrangment).setTvDate(tvDate);
        newDateFrangment.show(getFragmentManager(), "datePicker");
    }
}
