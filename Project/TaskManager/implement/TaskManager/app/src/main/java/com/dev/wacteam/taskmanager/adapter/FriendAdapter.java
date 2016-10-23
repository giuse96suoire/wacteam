package com.dev.wacteam.taskmanager.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dev.wacteam.taskmanager.R;
import com.dev.wacteam.taskmanager.model.User;

import java.util.ArrayList;

/**
 * Created by giuse96suoire on 10/23/2016.
 */

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {

    ArrayList<User> mUserList;
    Context mContext;

    public FriendAdapter(Context mContext, ArrayList<User> mUserList) {
        this.mContext = mContext;
        this.mUserList = mUserList;
    }

    public Context getmContext() {
        return mContext;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView tv_friendName;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            tv_friendName = (TextView) itemView.findViewById(R.id.tv_friend_name);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater li = LayoutInflater.from(context);
        View itemFriend = li.inflate(R.layout.item_friends, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemFriend);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        View v = convertView;
//        if (v == null) {
//            LayoutInflater li;
//            li = LayoutInflater.from(getContext());
//            v = li.inflate(R.layout.item_friends, null);
//        }
        User u = mUserList.get(position);
        TextView name = holder.tv_friendName;
        name.setText(u.getDisplayName());
//        if (u != null) {
//            TextView name = (TextView) v.findViewById(R.id.tv_friend_name);
//            name.setText(u.getDisplayName());
//        }
//        return v;

    }


    @Override
    public int getItemCount() {
        return mUserList.size();
    }
}
