package taskio.taskio.co.taskio.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import taskio.taskio.co.taskio.R;

/**
 * Created by olaar on 6/15/16.
 */
public class EditMileStoneDialogFragment extends DialogFragment {

    public EditMileStoneDialogFragment() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle instanceBundle) {

        View view = inflater.inflate(R.layout.edit_milestone_dialog_fragment, container);
        getDialog().setTitle("Edit Todo");
        return view;

    }

}
