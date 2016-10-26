package com.dev.wacteam.taskmanager.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
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
import com.dev.wacteam.taskmanager.model.Project;
import com.dev.wacteam.taskmanager.model.User;
import com.dev.wacteam.taskmanager.system.CurrentUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

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
        name.setText("#" + (position + 1) + ". " + p.getmTitle());
        complete.setText(p.getmComplete() + " %");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShowProjectInfo(p);
            }
        });
        //TODO: set status icon to red if project has changed


    }

    private void mShowProjectInfo(Project p) {
        LayoutInflater inflater = mActivity.getLayoutInflater();
        final View view = inflater.inflate(R.layout.project_dialog, null);

        TextView tv_complete, tv_leader, tv_memeber, tv_total_task, tv_task_complete, tv_task_failed;

        tv_complete = (TextView) view.findViewById(R.id.tv_project_dialog_complete);
        tv_leader = (TextView) view.findViewById(R.id.tv_project_dialog_leader);
        tv_memeber = (TextView) view.findViewById(R.id.tv_project_dialog_member);
        tv_total_task = (TextView) view.findViewById(R.id.tv_project_dialog_total_task);
        tv_task_complete = (TextView) view.findViewById(R.id.tv_project_dialog_total_task_complete);
        tv_task_failed = (TextView) view.findViewById(R.id.tv_project_dialog_total_task_failed);

        tv_complete.setText(p.getmComplete() + " %");
        tv_leader.setText(p.getmLeader() == null ? "No name" : p.getmLeader().getDisplayName());
        tv_memeber.setText((p.getmMembers() == null) ? "0" : p.getmMembers().size() + "");
        tv_total_task.setText((p.getmTasks() == null) ? "0" : p.getmTasks().size() + "");
        tv_task_complete.setText("0");
        tv_task_failed.setText("0");

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(view);
        builder
                .setNegativeButton("View detail", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Delete project", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNeutralButton("Share", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    ProgressDialog mProgressDialog;

    @Override
    public int getItemCount() {
        return mProjectList.size();
    }
}
