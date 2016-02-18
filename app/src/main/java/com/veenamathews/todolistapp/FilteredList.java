package com.veenamathews.todolistapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class FilteredList extends AppCompatActivity {

    private ListView listView;
    int buttonSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtered_list);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null){
            buttonSelected = bundle.getInt("buttonClicked");
        }

        final Cursor cursor;

        if (buttonSelected == 2) { cursor = MainActivity.dataBase.getAllInProgressTasks();} // Get all in progress tasks
        else if (buttonSelected == 3){ cursor = MainActivity.dataBase.getAllCompletedTasks();} // Get all competed tasks
        else { cursor = MainActivity.dataBase.getAllActiveTasks();} //or by default show all Active tasks

            String[] columns = new String[]{
                    DataBase.COLUMN_ID,
                    DataBase.COLUMN_TITLE
            };
            int[] widgets = new int[]{
                    R.id.list_TaskID,
                    R.id.list_TaskName
            };

            SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this, R.layout.listitem2,
                    cursor, columns, widgets, 0);

            listView = (ListView) findViewById(R.id.filteredListView);
            listView.setAdapter(cursorAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_filtered_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
