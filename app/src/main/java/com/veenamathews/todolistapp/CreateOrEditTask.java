package com.veenamathews.todolistapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class CreateOrEditTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_edit_task);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_or_edit_task, menu);
        return true;
    }

    public void onClickSaveButton(View view) {
        Button save = (Button)findViewById(R.id.radioButton);

        if (view.getId() == R.id.button3) {
            persistTask();
            this.finish();
        }
    }


    public void onClickDeleteButton(View view) {
        Button delete = (Button)findViewById(R.id.radioButton);

        if (view.getId() == R.id.button4) {
            Toast.makeText(getApplicationContext(), "Clicked Delete Button", Toast.LENGTH_SHORT).show();

            // persistTask(0);
            //this.finish();
        }
    }

    protected void persistTask (){
        // Creating a new task
        EditText taskNameField = (EditText)findViewById(R.id.editText);
        Toast.makeText(getApplicationContext(), "Clicked Save Button", Toast.LENGTH_SHORT).show();


        if (MainActivity.getInstance().dataBase.insertList(taskNameField.getText().toString(),"Active")) {
            Toast.makeText(getApplicationContext(), "New Task Added", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Could not Insert person", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
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
