package com.dev.wacteam.taskmanager.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.wacteam.taskmanager.R;
import com.dev.wacteam.taskmanager.activity.MainActivity;
import com.dev.wacteam.taskmanager.dialog.YesNoDialog;
import com.dev.wacteam.taskmanager.fragment.ProjectDetailFragment;
import com.dev.wacteam.taskmanager.model.Project;
import com.dev.wacteam.taskmanager.system.CurrentUser;

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
