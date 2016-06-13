package taskio.taskio.co.taskio;

import android.content.Intent;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import taskio.taskio.co.taskio.controller.TaskListController;
import taskio.taskio.co.taskio.model.TaskListModel;

public class AddTaskActivity extends AppCompatActivity {

    private EditText editTextDescr;
    private EditText editTextTitle;
    private Button buttonAddTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        editTextDescr = (EditText) findViewById(R.id.task_title_descr);
        editTextTitle = (EditText) findViewById(R.id.task_title_input);

        editTextTitle.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        editTextDescr.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewTask(view);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    public void addNewTask(View v) {

        String title = editTextTitle.getText().toString();
        String descr = editTextDescr.getText().toString();

        if (title.isEmpty() || descr.isEmpty()) { // more checks later

            Snackbar.make(v, "Please enter task name and description first", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        } else {
            TaskListModel taskListModel = new TaskListModel(this);
            taskListModel.addNewTask(new TaskListController(0, title, descr));
            Intent intent = new Intent();
            setResult(MainActivity.ADD_TASK_OK, intent);
            finish();
        }

    }
}
