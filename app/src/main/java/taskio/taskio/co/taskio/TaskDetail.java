package taskio.taskio.co.taskio;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import taskio.taskio.co.taskio.controller.TaskListController;
import taskio.taskio.co.taskio.model.TaskListModel;

public class TaskDetail extends AppCompatActivity {

    private EditText detail_txttitle;
    private EditText detail_txtdescr;
    private static int task_id;
    private static int task_completed;
    private Menu menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onUpdateTask(view);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        task_id = intent.getIntExtra(MainActivity.TASK_ID_PUT, -1);
        task_completed = intent.getIntExtra(MainActivity.TASK_COMPLETED_OR_NOT_PUT,-1);
        String title = intent.getStringExtra(MainActivity.TASK_TITLE_PUT);
        String descr = intent.getStringExtra(MainActivity.TASK_DESCR_PUT);

        detail_txttitle = (EditText) findViewById(R.id.detail_task_title);
        detail_txtdescr = (EditText) findViewById(R.id.detail_task_descr);

        detail_txttitle.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        detail_txtdescr.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        detail_txttitle.setText(title);
        detail_txtdescr.setText(descr);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.task_detail_menu, menu);
        MenuItem item = (MenuItem) findViewById(R.id.completed_task_menu_option);

        if (task_completed == 1){
            this.menu = menu;
            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_action_redo));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.delete_task_menu_option) {
            onDeleteTask();
        }

        if (id == R.id.completed_task_menu_option) {
            onCompleteTask();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onUpdateTask(View v) {
        // get new data
        String title = detail_txttitle.getText().toString();
        String descr = detail_txtdescr.getText().toString();


        TaskListModel taskListModel = new TaskListModel(this);
        TaskListController taskListController = new TaskListController(task_id, title, descr);
        taskListModel.updateTask(taskListController);
        Snackbar.make(v, "Task has been updated ", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
        setResult(MainActivity.EDIT_TASK_OK);
    }

    public void deleteTask() {
        TaskListModel taskListModel = new TaskListModel(this);
        TaskListController taskListController = new TaskListController(getIntent().getIntExtra(MainActivity.TASK_ID_PUT, -1), "", "");
        taskListModel.deleteTask(taskListController);
        setResult(MainActivity.EDIT_TASK_OK);
        finish();
    }

    public void taskComplete() {
        TaskListModel model = new TaskListModel(this);
        TaskListController taskListController = new TaskListController(getIntent().getIntExtra(MainActivity.TASK_ID_PUT, -1), "", "");
        model.taskCompleted(taskListController);
        setResult(MainActivity.EDIT_TASK_OK);
        finish();
    }

    public void redoCompletedTask() {
        TaskListModel model = new TaskListModel(this);
        TaskListController taskListController = new TaskListController(getIntent().getIntExtra(MainActivity.TASK_ID_PUT, -1), "", "");
        model.redoCompletedTask(taskListController);
        setResult(MainActivity.EDIT_TASK_OK);
    }

    public void onDeleteTask() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Careful now! do you really want to delete this task ?");
        builder.setPositiveButton("Yes Please", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteTask();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void onCompleteTask() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if (task_completed == 1){
            builder.setMessage("Do you want to redo this task ?");
        }else{
            builder.setMessage("Hey, You finished this task yea ?");
        }

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (task_completed == 1){
                    redoCompletedTask();
                }else{
                    taskComplete();
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}