package com.dev.wacteam.taskmanager.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
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
import com.dev.wacteam.taskmanager.system.CurrentFriend;
import com.dev.wacteam.taskmanager.system.CurrentUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;



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

    boolean isYourFriend = false;

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        User u = mUserList.get(position);
        TextView name = holder.tv_friendName;
        TextView email = holder.tv_friendEmail;
        email.setText(u.getProfile().getEmail());
        name.setText(u.getProfile().getDisplayName());
        ImageView more = holder.iv_addFriend;

        CurrentUser.getAllFriend(new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot data) {
//                for (DataSnapshot value : data.getChildren()) {
                User user = data.getValue(User.class);
                if (user.getProfile().getUid().equals(u.getProfile().getUid())) {
                    isYourFriend = true;
                }

                if (isYourFriend) {
                    more.setImageResource(R.drawable.ic_finish_task);
                    isYourFriend = false;
                } else {
                    more.setImageResource(R.drawable.ic_add);
                }
//                }

            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        }, mContext);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShowFriendInfo(u, more);
            }
        });

    }

    ProgressDialog mProgressDialog;

    private void mShowFriendInfo(User u, ImageView icon) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = mActivity.getLayoutInflater();
        final View view = inflater.inflate(R.layout.friend_dialog, null);
        TextView name = (TextView) view.findViewById(R.id.friendName);
        TextView email = (TextView) view.findViewById(R.id.friendEmail);
        name.setText(u.getProfile().getDisplayName());
        email.setText(u.getProfile().getEmail());

        builder.setView(view)
                .setNeutralButton(R.string.chat, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        boolean isFriend = false;
        for (User friend : CurrentFriend.getmListFriend()) {
            if (friend.getProfile().getUid().equals(u.getProfile().getUid())) {
                isFriend = true;
                break;
            }
        }
        if (isFriend) {
            builder.setNegativeButton(R.string.remove_friend, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        } else {
            builder.setNegativeButton(R.string.add_friend, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    CurrentUser.addFriend(u,getmContext());
                }
            });

        }

        AlertDialog dialog = builder.create();
        dialog.show();


//


    }

    private void mCheckFriendIsAdded(ImageView icon) {

    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }
}
