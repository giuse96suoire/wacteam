package com.dev.wacteam.taskmanager.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.wacteam.taskmanager.R;
import com.dev.wacteam.taskmanager.adapter.TaskAdapter;
import com.dev.wacteam.taskmanager.listener.OnChildEventListener;
import com.dev.wacteam.taskmanager.manager.EnumDefine;
import com.dev.wacteam.taskmanager.model.Project;
import com.dev.wacteam.taskmanager.model.Task;
import com.dev.wacteam.taskmanager.system.CurrentUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;


public class TabFragment extends Fragment {
    TextView tvTitle;
    Project mProject;
    int mProjectType, mTabPostition;
    String mProjectId;
    Button btnAddProject;
    //    RecyclerView mLvListTask;
    ListView mLvListTask;
    ArrayList<Task> mTaskDataList = new ArrayList<>();


    public static TabFragment newInstance(String title) {
        Bundle args = new Bundle();
        args.putString("title", title);
        TabFragment fragment = new TabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //
//    public static TabFragment newInstance(String title, String projectId, String projectType) {
//        Bundle args = new Bundle();
//        args.putString("title", title);
//        args.putString("projectId", projectId);
//        args.putString("projectType", projectType);
//        TabFragment fragment = new TabFragment();
//        fragment.setArguments(args);
//        return fragment;
//    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab_item, container, false);
//        tvTitle = (TextView) rootView.findViewById(R.id.tvTitle);
        btnAddProject = (Button) rootView.findViewById(R.id.btn_addtask);
        mLvListTask = (ListView) rootView.findViewById(R.id.lv_list_task);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String title = getArguments().getString("title");
        mProjectType = getArguments().getInt("projectType");
        mProjectId = getArguments().getString("projectId");
        mTabPostition = getArguments().getInt("tabPosition");
        Task t = new Task();
        t.setmTitle("test demo");
        mTaskDataList.add(t);
        mTaskDataList.add(t);
        mTaskDataList.add(t);
        mTaskDataList.add(t);
        TaskAdapter adapter = new TaskAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, mTaskDataList);
//        ArrayList<String> arr = new ArrayList<>();
//        arr.add("2323");
//        arr.add("2323");
//        arr.add("2323qwe");
//        arr.add("2323");
//        arr.add("2323qe");
//        arr.add("2323");
//        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, arr);

        mLvListTask.setAdapter(adapter);
//        mLvListTask.setAdapter(adapter);
//        tvTitle.setText(title);
        CurrentUser.getProjectById(mProjectId, new OnChildEventListener() {
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                mTaskDataList.clear();
                mTaskDataList.add(t);
                ArrayList<Task> list = dataSnapshot.getValue(Project.class).getmTasks();
                for (Task ta : list) {
                    mTaskDataList.add(ta);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                mTaskDataList = dataSnapshot.getValue(Project.class).getmTasks();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                mTaskDataList = dataSnapshot.getValue(Project.class).getmTasks();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mTaskDataList = dataSnapshot.getValue(Project.class).getmTasks();
                adapter.notifyDataSetChanged();
            }
        }, getContext());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Toast.makeText(getContext(), mProjectId, Toast.LENGTH_LONG).show();
        btnAddProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreateTaskDialog();
            }
        });
    }

    private void showCreateTaskDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        String title = "";
        if (mProjectType == EnumDefine.PROJECT_MANAGEMENT_TYPE || mProjectType == EnumDefine.TODO_TYPE) {
            title = getString(R.string.task_name_label);
        } else if (mProjectType == EnumDefine.SCHEDULE_TYPE) {
            title = getResources().getString(R.string.subject_name_label);
        }
        final View view = inflater.inflate(R.layout.create_task_dialog, null);
        builder.setTitle(title)
                .setView(view)
                .setPositiveButton(R.string.create_project, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton(R.string.cancel_create_project, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), R.string.cancel_create_project, Toast.LENGTH_LONG).show();

                    }
                });
        AlertDialog dialog = builder.create();

        dialog.show();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
