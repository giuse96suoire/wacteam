package com.dev.wacteam.taskmanager.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.wacteam.taskmanager.R;
import com.dev.wacteam.taskmanager.listener.OnGetDataListener;
import com.dev.wacteam.taskmanager.model.User;
import com.dev.wacteam.taskmanager.system.CurrentUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

/**
 * Created by giuse96suoire on 10/23/2016.
 */

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {

    ArrayList<User> mUserList;
    Context mContext;
    Activity mActivity;

    public FriendAdapter(Context mContext, Activity activity, ArrayList<User> mUserList) {
        this.mContext = mContext;
        this.mUserList = mUserList;
        this.mActivity = activity;
    }

    public Context getmContext() {
        return mContext;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView tv_friendName;
        public TextView tv_friendEmail;
        public ImageView iv_addFriend;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            tv_friendName = (TextView) itemView.findViewById(R.id.tv_friend_name);
            tv_friendEmail = (TextView) itemView.findViewById(R.id.tv_friend_email);
            iv_addFriend = (ImageView) itemView.findViewById(R.id.iv_more);
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

        User u = mUserList.get(position);
        TextView name = holder.tv_friendName;
        TextView email = holder.tv_friendEmail;
        email.setText(u.getEmail());
        name.setText(u.getDisplayName());

        ImageView more = holder.iv_addFriend;
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShowFriendInfo(u);
            }
        });

    }

    boolean isFound = false;

    private void mShowFriendInfo(User u) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = mActivity.getLayoutInflater();
        final View view = inflater.inflate(R.layout.friend_dialog, null);
        TextView name = (TextView) view.findViewById(R.id.friendName);
        TextView email = (TextView) view.findViewById(R.id.friendEmail);
        name.setText(u.getDisplayName());
        email.setText(u.getEmail());
//         boolean isFound = false;
        CurrentUser.getAllFriend(new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot data) {
                if (data.getValue(User.class).getUid() == u.getUid()) {
                    isFound = true;
                }
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        }, mContext);
        if (isFound) {
            builder.setNegativeButton("Remove Friend", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        } else {
            builder.setNegativeButton("Add Friend", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        }
        builder.setView(view)
                .setNeutralButton("Chat", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Add to project", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }
}
