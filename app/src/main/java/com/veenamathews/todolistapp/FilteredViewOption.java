package com.veenamathews.todolistapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TabHost;

public class FilteredViewOption extends AppCompatActivity {

    private ListView activeListView, inProgressListView, completedListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtered_view_option);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_filtered_view_option, menu);
        return true;
    }

    /*
     * When  the Active list button is clicked
     */
    public void onClickActiveList(View view){
        if (view.getId() == R.id.activeListButton ) {
            Intent intent = new Intent(this, FilteredList.class);
            intent.putExtra("buttonClicked",1);
            startActivity(intent);
        }
    }

    /*
     * When  the In Progress list button is clicked
     */
    public void onClickInProgressList(View view){
        if (view.getId() == R.id.In_Progress_List_Button ) {
            Intent intent = new Intent(this, FilteredList.class);
            intent.putExtra("buttonClicked",2);
            startActivity(intent);
        }
    }

    /*
     * When  the Completed list button is clicked
     */
    public void onClickCompletedList(View view){
        if (view.getId() == R.id.Completed_List_Button ) {
            Intent intent = new Intent(this, FilteredList.class);
            intent.putExtra("buttonClicked",3);
            startActivity(intent);
        }
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
