package com.dev.wacteam.taskmanager.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dev.wacteam.taskmanager.R;
import com.dev.wacteam.taskmanager.dialog.YesNoDialog;
import com.dev.wacteam.taskmanager.model.Task;
import com.dev.wacteam.taskmanager.system.CurrentUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by giuse96suoire on 10/29/2016.
 */

public class TaskAdapter extends ArrayAdapter<Task> {
    String mProjectId;
    ArrayList<Task> mListTask = new ArrayList<>();


    public TaskAdapter(Context context, int textViewResourceId, String projectId) {
        super(context, textViewResourceId);
    }

    public TaskAdapter(Context context, int resource, List<Task> items, String projectId) {
        super(context, resource, items);
        syncData();
        mProjectId = projectId;
    }

    private void syncData() {
        for (int i = 0; i < getCount(); i++) {
            mListTask.add(getItem(i));
        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.item_task, null);
        }

        Task t = getItem(position);

        if (t != null) {
            Button delete = (Button) v.findViewById(R.id.btn_delete);
            Button edit = (Button) v.findViewById(R.id.btn_edit);
            TextView name = (TextView) v.findViewById(R.id.tv_subject_name_detail);
            EditText editName = (EditText) v.findViewById(R.id.et_subject_name_detail);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //delete
                    YesNoDialog.mShow(getContext(), "Xóa " + t.getmTitle() + " ?", new YesNoDialog.OnClickListener() {
                        @Override
                        public void onYes(DialogInterface dialog, int which) {
                            //delete
                            remove(t);
//                            syncData();
                            notifyDataSetChanged();
//                            CurrentUser.updateTask(mProjectId, mListTask);
                        }

                        @Override
                        public void onNo(DialogInterface dialog, int which) {
                            //do nothing
                        }
                    });
                }
            });
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });
            name.setText(t.getmDeadline() == null ? t.getmTitle() : t.getmDeadline() + ": " + t.getmTitle());
//
//            if (t.getmTitle() == null || t.getmTitle().length() == 0) {
//                System.out.println("title null =============================>");
//                name.setVisibility(View.GONE);
//                editName.setVisibility(View.INVISIBLE);
//            } else {
//                name.setVisibility(View.INVISIBLE);
//                editName.setVisibility(View.GONE);
//            }
        }

        return v;
    }

}
