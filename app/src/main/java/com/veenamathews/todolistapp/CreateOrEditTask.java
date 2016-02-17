package com.veenamathews.todolistapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;


public class CreateOrEditTask extends AppCompatActivity {
    private static final String TAG = "To Do List App";

    int taskID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_edit_task);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
         if (bundle != null){
            taskID = bundle.getInt("KEY_EXTRA_CONTACT_ID");
         }
        Log.i(TAG, "The Task ID is: " + taskID);
        if (taskID > 0){
            overrideExistingTask();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_or_edit_task, menu);
        return true;
    }

    public void overrideExistingTask() {
        // Inflate the menu; this adds items to the action bar if it is present.
        Cursor rs = MainActivity.dataBase.getTask(taskID);
        rs.moveToFirst();
        String taskName = rs.getString(rs.getColumnIndex(DataBase.COLUMN_TITLE));
        String taskState = rs.getString(rs.getColumnIndex(DataBase.COLUMN_STATE));

        // Set Edit text field
        EditText taskNameField = (EditText)findViewById(R.id.editText);
        taskNameField.setText(taskName);

        // Set radio button fields
        RadioButton check_AButton = (RadioButton) findViewById(R.id.Active);
        RadioButton check_IPButton = (RadioButton) findViewById(R.id.In_Progress);
        RadioButton check_CButton = (RadioButton) findViewById(R.id.Completed);

        if (taskState.equals("Active")){
            check_AButton.setChecked(true);
            check_IPButton.setChecked(false);
            check_CButton.setChecked(false);
        }
        if (taskState.equals("In Progress")){
            check_AButton.setChecked(false);
            check_IPButton.setChecked(true);
            check_CButton.setChecked(false);
        }
        if (taskState.equals("Completed")){
            check_AButton.setChecked(false);
            check_IPButton.setChecked(false);
            check_CButton.setChecked(true);
        }

        if (!rs.isClosed()) {
            rs.close();
        }

    }

    public void onClickSaveButton(View view) {

        if (view.getId() == R.id.button3) {
            persistTask();
            this.finish();
        }
    }

    // The Delete Case
    public void onClickDeleteButton(View view) {

        if (view.getId() == R.id.button4) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("You are about to delete this task")
                    .setPositiveButton("Yes Delete", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            MainActivity.dataBase.deleteTask(taskID);
                            Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("Exit Delete", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            AlertDialog d = builder.create();
            d.setTitle("Delete Task?");
            d.show();
            return;
        }
    }

    protected void persistTask (){
        // Creating a new task
        EditText taskNameField = (EditText) findViewById(R.id.editText);
        if (taskID == 0 ) {

            if (MainActivity.getInstance().dataBase.insertList(taskNameField.getText().toString(), getActiveRadioButtonName())) {
                Toast.makeText(getApplicationContext(), "New Task Added", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Could not Insert person", Toast.LENGTH_LONG).show();
            }
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else{
            // Update Existing Task
            if (MainActivity.dataBase.updateTask(taskID,taskNameField.getText().toString(), getActiveRadioButtonName())) {
                Toast.makeText(getApplicationContext(), "Task Update Successful", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
            else {
                Toast.makeText(getApplicationContext(), "Task Update Failed", Toast.LENGTH_LONG).show();
            }
        }
    }

    // Get the state radio button name that is currently checked
    private String getActiveRadioButtonName(){
        String activeButton = "Completed";

        RadioButton check_AButton = (RadioButton) findViewById(R.id.Active);
        RadioButton check_IPButton = (RadioButton) findViewById(R.id.In_Progress);
        RadioButton check_CButton = (RadioButton) findViewById(R.id.Completed);

        if (check_AButton.isChecked()){
            activeButton = "Active";
        }
        if (check_IPButton.isChecked()){
            activeButton = "In Progress";
        }
        if(check_CButton.isChecked()){
            activeButton = "Completed";
        }
        return activeButton;
    }



    /**
     * When clicking the Active radio button
     * @param view
     */
    public void onClickActive(View view) {
        if (view.getId() == R.id.Active) {
            RadioButton active = (RadioButton)findViewById(R.id.Active);
            active.setChecked(true);
            RadioButton completed = (RadioButton)findViewById(R.id.In_Progress);
            completed.setChecked(false);
            RadioButton deleted = (RadioButton)findViewById(R.id.Completed);
            deleted.setChecked(false);
            Log.i(TAG, "You selected Active");
        }
    }

    /**
     * When clicking the Completed radio button
     * @param view
     */
    public void onClickInProgress(View view) {
        if (view.getId() == R.id.In_Progress) {
            RadioButton active = (RadioButton)findViewById(R.id.Active);
            active.setChecked(false);
            RadioButton completed = (RadioButton)findViewById(R.id.In_Progress);
            completed.setChecked(true);
            RadioButton deleted = (RadioButton)findViewById(R.id.Completed);
            deleted.setChecked(false);
            Log.i(TAG, "You selected In Progress");
        }
    }

    /**
     * When clicking the Deleted radio button
     * @param view
     */
    public void onClickCompleted(View view) {
        if (view.getId() == R.id.Completed) {
            RadioButton active = (RadioButton)findViewById(R.id.Active);
            active.setChecked(false);
            RadioButton completed = (RadioButton)findViewById(R.id.In_Progress);
            completed.setChecked(false);
            RadioButton deleted = (RadioButton)findViewById(R.id.Completed);
            deleted.setChecked(true);
            Log.i(TAG, "You selected Completed");
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
