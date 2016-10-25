package com.dev.wacteam.taskmanager.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.wacteam.taskmanager.R;
import com.dev.wacteam.taskmanager.model.Project;

import java.util.ArrayList;

/**
 * Created by huynh.mh on 10/25/2016.
 */
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
        public TextView tv_projectDeadline;
        public TextView tv_projectCreateDate;
        public TextView tv_projectComplete;
        public ImageView iv_iconStatus;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            tv_projectCreateDate = (TextView) itemView.findViewById(R.id.tv_item_project_create_date);
            tv_projectName = (TextView) itemView.findViewById(R.id.tv_item_project_name);
            tv_projectComplete = (TextView) itemView.findViewById(R.id.tv_item_project_complete);
            tv_projectDeadline = (TextView) itemView.findViewById(R.id.tv_item_project_deadline);
            iv_iconStatus = (ImageView) itemView.findViewById(R.id.iv_item_project_status);
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

        Project p = mProjectList.get(position);
        TextView name = holder.tv_projectName;
        TextView deadline = holder.tv_projectDeadline;
        TextView complete = holder.tv_projectComplete;
        TextView create = holder.tv_projectCreateDate;
        ImageView status = holder.iv_iconStatus;

        deadline.setText("Deadline: " + p.getmDeadline());
        create.setText(" - Create: " + p.getmCreateDate());
        name.setText("#"+(position + 1) + ". " + p.getmTitle());
        complete.setText(p.getmComplete() + " %");
        //TODO: set status icon to red if project has changed


    }

    ProgressDialog mProgressDialog;

    @Override
    public int getItemCount() {
        return mProjectList.size();
    }
}
