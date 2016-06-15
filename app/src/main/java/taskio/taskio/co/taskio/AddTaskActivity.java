package taskio.taskio.co.taskio;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import taskio.taskio.co.taskio.controller.MilestoneController;
import taskio.taskio.co.taskio.controller.TaskListController;
import taskio.taskio.co.taskio.fragment.EditMileStoneDialogFragment;
import taskio.taskio.co.taskio.model.TaskListModel;

public class AddTaskActivity extends AppCompatActivity {

    private EditText editTextMilestone;
    private EditText editTextTitle;
    private Button buttonAddTask;
    private static List formList = new ArrayList();
    private static int eid = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        editTextMilestone = (EditText) findViewById(R.id.task_title_descr);
        editTextTitle = (EditText) findViewById(R.id.task_title_input);

        formList.clear();
        formList.add(editTextMilestone.getId());

        editTextTitle.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        editTextMilestone.getBackground().mutate().setColorFilter(getResources().getColor(R.color.gray), PorterDuff.Mode.SRC_ATOP);

        editTextMilestone.setOnFocusChangeListener(myEditTextFocus);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewTask(view);
            }
        });*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.add_tasks_menu, menu);
       /* MenuItem item = (MenuItem) findViewById(R.id.completed_task_menu_option);

        if (task_completed == 1){
            this.menu = menu;
            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_action_redo));
        }*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_task_menu_option) {
            addNewTask(getCurrentFocus());
        }

        if (id == R.id.reminder_task_menu_option) {
            // onCompleteTask();
        }
        if (id ==R.id.reminder_task_menu_option){
            onComingSoonFeature();
        }
        return super.onOptionsItemSelected(item);
    }

    public void addNewTask(View v) {

        String title = editTextTitle.getText().toString();

        // validate form list
        int i;
        int formListSize = formList.size();
        boolean isFormOk = true;
        for (i = 0; i < formListSize; i++) {
            Log.d("test => ", " " + i + " =>" + formListSize);
            isFormOk = true;
            if (i == (formListSize - 1)) {
                isFormOk = true;
                break;
            }
            int id = (int) formList.get(i);
            EditText editText = (EditText) findViewById(id);
            if (editText.getText().toString().isEmpty()) {
                isFormOk = false;
                break;
            }
        }

        if (title.isEmpty() || !isFormOk) { // more checks later

            Snackbar.make(v, "Please enter task name and Todos first", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        } else {

            TaskListModel taskListModel = new TaskListModel(this);
            long lastInsert = taskListModel.addNewTask(new TaskListController(0, title));

            // for each mile stone .. enter all milestone excluding the last 1

            for (i = 0; i < formListSize; i++) {
//                Log.d("test => ", " " + i + " =>" + formListSize);
                isFormOk = true;
                if (i == (formListSize - 1)) {
                    isFormOk = true;
                    break;
                }
                // get edit text, extract the text and save to db
                int id = (int) formList.get(i);
                EditText editText = (EditText) findViewById(id);
                taskListModel.addMileStone(new MilestoneController(0, editText.getText().toString(), 0,lastInsert));
            }

            // for each mile stone .. enter all milestone excluding the last 1

            Intent intent = new Intent();
            setResult(MainActivity.ADD_TASK_OK, intent);
            finish();
        }

    }

    public void addControl() {
        eid = eid + 1;
        formList.add((eid));
        LinearLayout relativeLayout = (LinearLayout) findViewById(R.id.add_task_layout);
        EditText et = new EditText(AddTaskActivity.this);
        LinearLayout.LayoutParams editLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        editLayoutParams.setMargins(0, 30, 0, 0);
        et.setHint("Add Todo");
        et.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT);
        et.setLayoutParams(editLayoutParams);
        et.setId(eid);
        et.getBackground().mutate().setColorFilter(getResources().getColor(R.color.gray), PorterDuff.Mode.SRC_ATOP);
        et.setOnFocusChangeListener(myEditTextFocus);
        relativeLayout.addView(et);

    }

    public void removeControl(int id) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.add_task_layout);
        EditText et = (EditText) findViewById(id);
        et.setVisibility(View.GONE);
        formList.remove(formList.indexOf(id));
    }

    private View.OnFocusChangeListener myEditTextFocus = new View.OnFocusChangeListener() {
        public void onFocusChange(View view, boolean gainFocus) {
            //onFocus
            if (gainFocus) {
                //if focus is on d last element in d list add control
                if ((formList.size() - 1) == formList.indexOf(view.getId())) {
                    addControl();
                } else {
                    int id = view.getId();
                    Log.d("Echo => ", "" + (formList.size()) + " echo => " + view.getId());
                }

            }
            //onBlur
            else {
                EditText editText = (EditText) findViewById(view.getId());
                if (editText.getText().toString().isEmpty()) {
                    removeControl(view.getId());
                } else {
                    int id = view.getId();
                    Log.d("Echo => ", "" + (formList.size()) + " echo => " + view.getId());
                }
            }
        }

        ;
    };

    public void  onComingSoonFeature(){
        FragmentManager manager = getFragmentManager();
        Fragment frag = manager.findFragmentByTag("fragment_edit_name");
        //  manager.beginTransaction().remove(frag).commit();
        EditMileStoneDialogFragment mileStoneDialogFragment = new EditMileStoneDialogFragment();
        mileStoneDialogFragment.show(manager, "fragment_edit_name");
    }
}
