package dspm.dc.ufc.br.mynotes;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

/**
 * Created by Thiago on 16/06/2016.
 */
public class NotesListView {

    ListView listview;
    NoteDAO noteDAO;
    Context context;

    public NotesListView(ListView listview, Context view, NoteDAO noteDAO) {
        this.listview = listview;
        this.noteDAO = noteDAO;
        this.context = view;

    }

    public void setListView(){
        setListView(0, 0, 0);
    }

    public void setListView(int op, long begin, long end){

        List<Note> vector;

        if(op == 0)
        {
            vector = noteDAO.list();
        }
        else
        {
            vector = noteDAO.listByInterval(begin, end);
        }

        final ArrayAdapter<Note> adapter = new ArrayAdapter<Note>(context,
                R.layout.rowlayout, R.id.label, vector);
        listview.setAdapter(adapter);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final Note item = (Note) parent.getItemAtPosition(position);

                chamarEditPostActivity(view.getContext(), item);
            }

        });
    }


    private void chamarEditPostActivity(Context view, Note item){
        Intent intent = new Intent();
        intent.setAction("br.ufc.dc.dspm.action.EDITNOTE");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setComponent(null);
        intent.addCategory("br.ufc.dc.dspm.category.CATEGORIA");

        intent.putExtra("ID", item.getId());
        intent.putExtra("TITLE", item.getTitle());
        intent.putExtra("CONTENT", item.getContent());
        intent.putExtra("DATE", item.getDateMillis());

        view.startActivity(intent);
    }


}
