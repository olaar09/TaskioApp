package taskio.taskio.co.taskio.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import taskio.taskio.co.taskio.R;

/**
 * Created by olaar on 6/15/16.
 */
public class EditMileStoneDialogFragment extends DialogFragment  {

    private EditText todoInput;

    public EditMileStoneDialogFragment() {
        //setArguments();
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle instanceBundle) {

        final View view = inflater.inflate(R.layout.edit_milestone_dialog_fragment, container);
        getDialog().setTitle("Edit Todo");

        todoInput = (EditText)  view.findViewById(R.id.edit_milestone_input);
        todoInput.setText("");

        ImageButton button = (ImageButton) view.findViewById(R.id.on_do_edit_milestone);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Lol =>", todoInput.getText().toString());
            }
        });
        return view;
    }


}
