package taskio.taskio.co.taskio.adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

import taskio.taskio.co.taskio.MainActivity;
import taskio.taskio.co.taskio.R;
import taskio.taskio.co.taskio.TaskDetail;
import taskio.taskio.co.taskio.controller.MilestoneController;
import taskio.taskio.co.taskio.controller.TaskListController;
import taskio.taskio.co.taskio.model.TaskListModel;

/**
 * Created by olaar on 6/14/16.
 */
public class MilestoneListAdapter extends ArrayAdapter<MilestoneController> {

    public Context context;
    private static int pos;
    private static MilestoneController milestoneController;
    private static int taskId;
    private static int taskCompleted;
    private static TaskListModel taskListModel;
    private TaskDetail.CustomDialogFrafment onEdit;

    public MilestoneListAdapter(Context c, List<MilestoneController> milestoneList, int taskId, int taskCompleted, TaskDetail.CustomDialogFrafment onEdit) {
        super(c, 0, milestoneList);
        this.context = c;
        this.taskId = taskId;
        this.taskCompleted = taskCompleted;
        taskListModel = new TaskListModel(context);
        this.onEdit = onEdit;
    }

    public class ViewHolder {
        public CheckBox checkBox;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View milestoneListView = convertView;
        pos = position;
        if (milestoneListView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            milestoneListView = layoutInflater.inflate(R.layout.milestone_list_view, parent, false);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.checkBox = (CheckBox) milestoneListView.findViewById(R.id.showcheck_milestone);

            milestoneListView.setTag(viewHolder);

        }

        ViewHolder viewholder = (ViewHolder) milestoneListView.getTag();
        milestoneController = getItem(position);


        viewholder.checkBox.setText(milestoneController.get_mileStone());
        viewholder.checkBox.setPaintFlags(0);
        if (milestoneController.get_mileStoneComleted() == 1) {
            viewholder.checkBox.setPaintFlags(viewholder.checkBox.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        viewholder.checkBox.setChecked(milestoneController.get_mileStoneComleted() == 1);
        viewholder.checkBox.setTag(milestoneController.get_mileStoneId());
        viewholder.checkBox.setEnabled(true);
        List<TaskListController> getTask = taskListModel.getTask(new TaskListController(taskId, null, 0));
        if (getTask.get(0).get_completed() == 1) {
            viewholder.checkBox.setEnabled(false);
        }

        viewholder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;

                if (checkBox.isChecked()) {
                    taskListModel.mileStoneCompleted((int) checkBox.getTag(), 1);
                    int allMileStonesize = taskListModel.getAllMilestone(new MilestoneController(0, null, 0, taskId)).size();
                    int allCompletedMilestoneSize = taskListModel.getCompletedMilestones(new MilestoneController(0, null, 0, taskId)).size();

                    if (allCompletedMilestoneSize > 0 && allMileStonesize > 0 && allMileStonesize == allCompletedMilestoneSize) {
                        taskListModel.taskCompleted(new TaskListController(taskId, null, 0),1,0);
                        // Log.d("ddd=>", "dd" + taskId);
                    }
                    checkBox.setPaintFlags(checkBox.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                } else {

                    taskListModel.mileStoneCompleted((int) checkBox.getTag(), 0);
                    taskListModel.taskCompleted(new TaskListController(taskId, null, 0), 0, 1);
                    checkBox.setPaintFlags(0);
                }
            }
        });

        viewholder.checkBox.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                //FragmentManager fragmentManager
                //boolean d = onEdit;
                //taskDetail.onEditMileStone();
                onEdit.onEditMileStone();

                if (checkBox.isChecked()) {
                    Log.d("Longpressed => ", "CHECKED");
                } else {
                    Log.d("Longpressed => ", "Not CHECKED");
                }

                return true;
            }
        });

        // todo : comeback later to add strikethrough if milestone  has been complete

        return milestoneListView;
    }
}
