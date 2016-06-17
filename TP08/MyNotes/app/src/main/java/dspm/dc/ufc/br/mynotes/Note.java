package dspm.dc.ufc.br.mynotes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Thiago on 14/06/2016.
 */
public class Note {

    private int id;
    private String title;
    private String content;
    private GregorianCalendar date;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(GregorianCalendar date) {
        this.date = date;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public String getDateString(){
        String formData = date.get(Calendar.DAY_OF_MONTH) + "/" + date.get(Calendar.MONTH) + "/" + date.get(Calendar.YEAR);
        return formData;
    }

    public long getDateMillis(){
        return date.getTimeInMillis();
    }

    public String toString() {
        return "(" + id + ") " + title + ": " + content + "\n Data: " + getDateString();
    }
}
