package dspm.dc.ufc.br.mynotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Thiago on 14/06/2016.
 */
public class NoteDAO extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyNotes.db";
    public static final int DATABASE_VERSION = 2;

    public NoteDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public NoteDAO(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuffer sql = new StringBuffer();
        sql.append("create table notes (");
        sql.append("id integer primary key autoincrement,");
        sql.append("title text,");
        sql.append("content text,");
        sql.append("date bigint)");
        db.execSQL(sql.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists notes");
        onCreate(db);
    }

    public void create(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", note.getTitle());
        contentValues.put("content", note.getContent());
        contentValues.put("date", note.getDateMillis());
        long id = db.insert("notes", null, contentValues);
        Log.v("SQLite", "create id = " + id);
    }

    public Note retrieve(Integer id) {
        String[] fieldValues = new String[1];
        fieldValues[0] = Integer.toString(id);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("select * from notes where id = ?", fieldValues);
        Note note = null;
        if (result != null && result.getCount() > 0) {
            note = new Note();
            note.setId(result.getInt(0));
            note.setTitle(result.getString(1));
            note.setContent(result.getString(2));

            GregorianCalendar gc = new GregorianCalendar();
            gc.setTimeInMillis(result.getLong(3));

            note.setDate(gc);
        }
        return note;
    }

    public void update(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", note.getTitle());
        contentValues.put("content", note.getContent());
        contentValues.put("date", note.getDateMillis());
        String[] fieldValues = new String[1];
        fieldValues[0] = Integer.toString(note.getId());
        db.update("notes", contentValues, " id = ? ", fieldValues);
    }

    public void delete(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("notes", " id = ? ", new String[]{Integer.toString(id)});
    }

    public List<Note> list() {
        List<Note> notes = new ArrayList<Note>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("select * from notes order by id", null);
        if (result != null && result.getCount() > 0) {
            result.moveToFirst();
            while (result.isAfterLast() == false) {
                Note note = new Note();
                note.setId(result.getInt(0));
                note.setTitle(result.getString(1));
                note.setContent(result.getString(2));

                GregorianCalendar gc = new GregorianCalendar();
                gc.setTimeInMillis(result.getLong(3));

                note.setDate(gc);
                notes.add(note);
                result.moveToNext();
            }
        }
        return notes;
    }

    public List<Note> listByInterval(long initialDate, long finalDate){
        List<Note> notes = new ArrayList<Note>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] fieldValues = new String[2];
        fieldValues[0] = Long.toString(initialDate);
        fieldValues[1] = Long.toString(finalDate);
        Cursor result = db.rawQuery("select * from notes where date > ? and date < ? order by date", fieldValues);
        if (result != null && result.getCount() > 0) {
            result.moveToFirst();
            while (result.isAfterLast() == false) {
                Note note = new Note();
                note.setId(result.getInt(0));
                note.setTitle(result.getString(1));
                note.setContent(result.getString(2));

                GregorianCalendar gc = new GregorianCalendar();
                gc.setTimeInMillis(result.getLong(3));

                note.setDate(gc);
                notes.add(note);
                result.moveToNext();
            }
        }
        return notes;
    }
}
