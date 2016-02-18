package com.veenamathews.todolistapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {
    public final static String KEY_EXTRA_CONTACT_ID = "KEY_EXTRA_CONTACT_ID";

   public static MainActivity singleton = new MainActivity();


    private ListView listView;
    public static DataBase dataBase;


    public static MainActivity getInstance(){
        return singleton;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addTask = (Button) findViewById(R.id.button);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateOrEditTask.class);
                intent.putExtra(KEY_EXTRA_CONTACT_ID, 0);
                startActivity(intent);
            }
        });

        dataBase = new DataBase(this);

        final Cursor cursor = dataBase.getAllTasks();
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

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(cursorAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView listView, View view,
                                    int position, long id) {
                Cursor itemCursor = (Cursor) MainActivity.this.listView.getItemAtPosition(position);
                int taskID = itemCursor.getInt(itemCursor.getColumnIndex(DataBase.COLUMN_ID));
                Intent intent = new Intent(getApplicationContext(), CreateOrEditTask.class);
                intent.putExtra(KEY_EXTRA_CONTACT_ID, taskID);
                startActivity(intent);
            }
        });
    }

    public void onClickFIlteredView(View view){
        Button filteredView = (Button) findViewById(R.id.button);
        if (view.getId() == R.id.button2 ) {
            Intent intent2 = new Intent(MainActivity.this, FilteredViewOption.class);
            startActivity(intent2);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
