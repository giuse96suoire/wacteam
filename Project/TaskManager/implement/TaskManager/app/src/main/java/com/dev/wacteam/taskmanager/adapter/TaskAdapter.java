package com.dev.wacteam.taskmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dev.wacteam.taskmanager.R;
import com.dev.wacteam.taskmanager.model.Task;

import java.util.List;

/**
 * Created by giuse96suoire on 10/29/2016.
 */

public class TaskAdapter extends ArrayAdapter<Task> {

    public TaskAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public TaskAdapter(Context context, int resource, List<Task> items) {
        super(context, resource, items);
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
            TextView name = (TextView) v.findViewById(R.id.tv_task_name_detail);
            name.setText(t.getmTitle());

        }

        return v;
    }

}
