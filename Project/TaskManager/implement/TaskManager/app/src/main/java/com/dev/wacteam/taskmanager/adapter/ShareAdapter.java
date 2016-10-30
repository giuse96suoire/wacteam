package com.dev.wacteam.taskmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dev.wacteam.taskmanager.R;
import com.dev.wacteam.taskmanager.model.Task;
import com.dev.wacteam.taskmanager.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by giuse96suoire on 10/30/2016.
 */

public class ShareAdapter extends ArrayAdapter<User> {
    String mProjectId;
    ArrayList<Task> mListTask = new ArrayList<>();


    public ShareAdapter(Context context, int textViewResourceId, String projectId) {
        super(context, textViewResourceId);
    }

    public ShareAdapter(Context context, int resource, List<User> items) {
        super(context, resource, items);

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.item_friend_checkbox, null);
        }

        User u = getItem(position);

        if (u != null) {
            TextView name = (TextView) v.findViewById(R.id.tv_friend_name);
            TextView email = (TextView) v.findViewById(R.id.tv_friend_email);
            name.setText(u.getProfile().getDisplayName());
            email.setText(u.getProfile().getEmail());

        }

        return v;
    }

}