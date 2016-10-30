package com.dev.wacteam.taskmanager.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.dev.wacteam.taskmanager.R;
import com.dev.wacteam.taskmanager.activity.MainActivity;
import com.dev.wacteam.taskmanager.dialog.YesNoDialog;
import com.dev.wacteam.taskmanager.fragment.ProjectDetailFragment;
import com.dev.wacteam.taskmanager.model.Profile;
import com.dev.wacteam.taskmanager.model.Project;
import com.dev.wacteam.taskmanager.model.User;
import com.dev.wacteam.taskmanager.system.CurrentUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder> {
    ArrayList<Project> mProjectList;
    Context mContext;
    Activity mActivity;

    public ProjectAdapter(Context mContext, Activity activity, ArrayList<Project> mProjectList) {
        this.mContext = mContext;
        this.mProjectList = mProjectList;
        this.mActivity = activity;
    }

    public Context getmContext() {
        return mContext;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView tv_projectName;
        public ImageView iv_delete, iv_share;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            tv_projectName = (TextView) itemView.findViewById(R.id.tv_item_project_name);
            iv_delete = (ImageView) itemView.findViewById(R.id.iv_ic_delete);
            iv_share = (ImageView) itemView.findViewById(R.id.iv_ic_share);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater li = LayoutInflater.from(context);
        View itemProject = li.inflate(R.layout.item_project, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemProject);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        System.out.println("bind data p viewholder =>>>>>>>>>>>>>>>>...");

        Project p = mProjectList.get(position);
        TextView name = holder.tv_projectName;
        ImageView delete = holder.iv_delete;
        ImageView share = holder.iv_share;
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YesNoDialog.mShow(getmContext(), "Bạn có chắc chắn muốn xóa ?", new YesNoDialog.OnClickListener() {
                    @Override
                    public void onYes(DialogInterface dialog, int which) {
                        //delete
                        CurrentUser.deleteProjectById(p.getmProjectId(), mContext);
                        mProjectList.remove(p);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onNo(DialogInterface dialog, int which) {

                    }
                });
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //share
                ArrayList<String> emailFriend = new ArrayList<>();
                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                LayoutInflater inflater = mActivity.getLayoutInflater();
                final View view = inflater.inflate(R.layout.share_friend_dialog, null);
                ListView lvFriendToShare = (ListView) view.findViewById(R.id.lv_list_friend_to_share);
//                ArrayList<User> friend = CurrentFriend.getmListFriend();
                ArrayList<User> friend = new ArrayList<User>();
                User u = new User();
                Profile p0 = new Profile();
                p0.setDisplayName(mContext.getString(R.string.mother_name));
                p0.setEmail("me@me.com");
                p0.setUid("ezVdoJYc1mhPpOAnrwlirAkxnrq2");
                u.setProfile(p0);
                User u1 = new User();

                Profile p1 = new Profile();
                p1.setDisplayName(mContext.getString(R.string.teacher_name));
                p1.setEmail("thay@thay.com");
                p1.setUid("ZUJHpXmgFNhY7fhHJKgm8iCvS0H2");
                u1.setProfile(p1);

                User u2 = new User();

                Profile p2 = new Profile();
                p2.setDisplayName("Con trai");
                p2.setEmail("con@con.com");
                p2.setUid("QmHEuy8NA8NmyWL3NOu4CvUXwXk2");

                u2.setProfile(p2);
                friend.add(u);
                friend.add(u1);
                friend.add(u2);

                ArrayList<String> demo = new ArrayList<String>();
                demo.add("ezVdoJYc1mhPpOAnrwlirAkxnrq2");

//                ShareAdapter shareAdapter = new ShareAdapter(getmContext(), android.R.layout.simple_list_item_checked, friend);
                ShareAdapter shareAdapter = new ShareAdapter(getmContext(), android.support.v7.appcompat.R.layout.abc_list_menu_item_checkbox, friend);
                lvFriendToShare.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
                lvFriendToShare.setAdapter(shareAdapter);
                lvFriendToShare.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                        emailFriend.add(lvFriendToShare.getItemAtPosition(position));

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                builder.setTitle(R.string.share_label)
                        .setView(view)
                        .setPositiveButton(R.string.share_label, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseDatabase.getInstance()
                                        .getReference("projects/list")
                                        .child(p.getmProjectId())
                                        .child("mMembers")
                                        .setValue(demo);

                            }
                        })
                        .setNegativeButton(R.string.cancel_create_project, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                AlertDialog dialog = builder.create();

                dialog.show();

            }
        });
        name.setText("#" + (position + 1) + ". " + p.getmTitle());
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to detail
                Bundle args = new Bundle();
                args.putString("projectId", p.getmProjectId());
                ProjectDetailFragment f = new ProjectDetailFragment();
                f.setArguments(args);
                ((MainActivity) mActivity).callFragment(f);
            }
        });
        //TODO: set status icon to red if project has changed


    }


    @Override
    public int getItemCount() {
        return mProjectList.size();
    }
}
