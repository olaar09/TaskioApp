package taskio.taskio.co.taskio;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.List;

import taskio.taskio.co.taskio.adapter.TaskListAdapter;
import taskio.taskio.co.taskio.controller.TaskListController;
import taskio.taskio.co.taskio.model.TaskListModel;

public class MainActivity extends AppCompatActivity {


    public static final String TASK_ID_PUT = "com.taskio.taskio.task_id_put";
    public static final String TASK_TITLE_PUT = "com.taskio.taskio.task_title_put";
    public static final String TASK_DESCR_PUT = "com.taskio.taskio.task_descr_put";
    public static final String TASK_COMPLETED_OR_NOT_PUT = "com.taskio.taskio.task_completedornnot_put";
    public static final int ADD_TASK_OK = 200;
    public static final int EDIT_TASK_OK = 300;
    private TaskListModel taskModel;
    private static List<TaskListController> getTaskList;
    private ListView taskListView;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // check if task exists..
        taskModel = new TaskListModel(this);
        getTaskList = taskModel.getAllTasks();
        int isTaskEmpty = getTaskList.size();

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adapter = new TaskListAdapter(this, getTaskList);
        taskListView = (ListView) findViewById(R.id.tasksListView);
        taskListView.setAdapter(adapter);
        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TaskListController item = getTaskList.get(position);
                Intent intent = new Intent(MainActivity.this, TaskDetail.class);
                intent.putExtra(TASK_ID_PUT, item.get_taskId());
                intent.putExtra(TASK_TITLE_PUT, item.get_taskTitle());
                intent.putExtra(TASK_COMPLETED_OR_NOT_PUT, item.get_completed());

                startActivityForResult(intent, EDIT_TASK_OK);
            }
        });

        if (isTaskEmpty > 0) {
            listViewIsNotEmptyNoMore();
        }
    }


    public void listViewIsNotEmptyNoMore() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.notasksyet);
        linearLayout.setVisibility(View.INVISIBLE);
        taskListView.setVisibility(View.VISIBLE);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appBar);
        appBarLayout.setVisibility(View.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddNewTask(view);
            }
        });
    }

    public void listViewIsEmpty() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.notasksyet);
        linearLayout.setVisibility(View.VISIBLE);
        taskListView.setVisibility(View.INVISIBLE);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appBar);
        appBarLayout.setVisibility(View.INVISIBLE);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.INVISIBLE);
    }

    private void dataChanged() {
        getTaskList.clear();
        getTaskList.addAll(taskModel.getAllTasks());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int reqCode, int resCode, Intent data) {
        super.onActivityResult(reqCode, resCode, data);

        if (reqCode == ADD_TASK_OK) {
            dataChanged();
            if (getTaskList.size() > 0) {
                listViewIsNotEmptyNoMore();
            }
        }

        if (reqCode == EDIT_TASK_OK) {
            dataChanged();
            if (getTaskList.size() < 1) {
                listViewIsEmpty();
            }

        }

    }

    public void onAddNewTask(View v) {
        Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
        startActivityForResult(intent, ADD_TASK_OK);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return false;
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
