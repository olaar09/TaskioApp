package taskio.taskio.co.taskio.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import taskio.taskio.co.taskio.R;
import taskio.taskio.co.taskio.controller.MilestoneController;
import taskio.taskio.co.taskio.controller.TaskListController;
import taskio.taskio.co.taskio.model.TaskListModel;

/**
 * Created by olaar on 6/10/16.
 */
public class TaskListAdapter extends ArrayAdapter<TaskListController> {

    public Context context;

    public TaskListAdapter(Context context, List<TaskListController> taskList) {
        super(context, 0, taskList);
        this.context = context;
    }

    public class ViewHolder {
        public TextView title;
        public TextView descr;
        public ImageView completeOrNot;
    }

    public View getView(int pos, View convertView, ViewGroup parent) {

        View tasklistView = convertView;

        if (tasklistView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            tasklistView = layoutInflater.inflate(R.layout.task_list_view,parent,false);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.title = (TextView) tasklistView.findViewById(R.id.tasktitle);
            viewHolder.descr = (TextView) tasklistView.findViewById(R.id.task_descr);
            viewHolder.completeOrNot = (ImageView) tasklistView.findViewById(R.id.completedornot);

            tasklistView.setTag(viewHolder);
        }


        ViewHolder viewHolder = (ViewHolder) tasklistView.getTag();
        TaskListController taskListController = getItem(pos);

        TaskListModel taskListModel = new TaskListModel(context);
        List<MilestoneController> milestoneList  = taskListModel.getAllMilestone(new MilestoneController(0, "", 0, taskListController.get_taskId()));
        List<MilestoneController> completedMilestone = taskListModel.getCompletedMilestones(new MilestoneController(0, "", 0, taskListController.get_taskId()));

        viewHolder.title.setText(taskListController.get_taskTitle());
        viewHolder.descr.setText(milestoneList.size() + " Todos, "+ completedMilestone.size()+" Completed");


        if (taskListController.get_completed() == 1){
            viewHolder.completeOrNot.setImageResource(R.drawable.ic_check_circle_black);
        }else {
            viewHolder.completeOrNot.setImageResource(R.drawable.ic_check_circle);
        }

        return tasklistView;
    }


}