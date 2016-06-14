package taskio.taskio.co.taskio.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import taskio.taskio.co.taskio.R;
import taskio.taskio.co.taskio.controller.MilestoneController;

/**
 * Created by olaar on 6/14/16.
 */
public class MilestoneListAdapter extends ArrayAdapter<MilestoneController> {

    public Context context;

    public MilestoneListAdapter(Context c, List<MilestoneController> milestoneList) {
        super(c, 0, milestoneList);
        this.context = c ;
    }

    public class ViewHolder {
        public TextView textView;
        public CheckBox checkBox;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View milestoneListView = convertView;

        if (milestoneListView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            milestoneListView = layoutInflater.inflate(R.layout.milestone_list_view, parent, false);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.checkBox = (CheckBox) milestoneListView.findViewById(R.id.showcheck_milestone);
            viewHolder.textView = (TextView) milestoneListView.findViewById(R.id.showtext_milestone);

            milestoneListView.setTag(viewHolder);

        }

        ViewHolder viewholder = (ViewHolder) milestoneListView.getTag();
        MilestoneController  milestoneController = getItem(position);

        // todo : comeback later to add strikethrough if milestone  has been completed
        viewholder.textView.setText(milestoneController.get_mileStone());


        return milestoneListView;
    }
}
