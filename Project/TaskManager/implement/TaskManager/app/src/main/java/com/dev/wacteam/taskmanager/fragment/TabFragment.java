package com.dev.wacteam.taskmanager.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.wacteam.taskmanager.R;
import com.dev.wacteam.taskmanager.adapter.TaskAdapter;
import com.dev.wacteam.taskmanager.dialog.DialogDateTimePicker;
import com.dev.wacteam.taskmanager.listener.OnChildEventListener;
import com.dev.wacteam.taskmanager.model.Project;
import com.dev.wacteam.taskmanager.model.Task;
import com.dev.wacteam.taskmanager.system.CurrentUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;

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
    ArrayList<Task> mAllTaskWithNoFillter = new ArrayList<>();


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

    TaskAdapter adapter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String title = getArguments().getString("title");
        mProjectId = getArguments().getString("projectId");
        mTabPostition = getArguments().getInt("tabPosition");

        adapter = new TaskAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, mTaskDataList, mProjectId);

        mLvListTask.setAdapter(adapter);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Toast.makeText(getContext(), mProjectId, Toast.LENGTH_LONG).show();
        btnAddProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Task t = new Task();
//                t.setmTitle("");
//                mTaskDataList.add(t);
//                adapter.notifyDataSetChanged();
                showCreateTaskDialog();
            }
        });
        CurrentUser.getProjectById(mProjectId, new OnChildEventListener() {
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                mTaskDataList.clear();
                mAllTaskWithNoFillter.clear();
                Project p = dataSnapshot.getValue(Project.class);
                ArrayList<Task> list = new ArrayList<Task>();
                if (p != null) {
                    list = p.getmTasks();
                }
                if (list != null)
                    for (Task ta : list) {
                        mAllTaskWithNoFillter.add(ta);
                        if (ta.getmDayOfWeek() == mTabPostition)
                            mTaskDataList.add(ta);

                    }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                mTaskDataList.clear();
                ArrayList<Task> list = dataSnapshot.getValue(Project.class).getmTasks();
                for (Task ta : list) {
                    mTaskDataList.add(ta);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                mTaskDataList.clear();
                ArrayList<Task> list = dataSnapshot.getValue(Project.class).getmTasks();
                for (Task ta : list) {
                    mTaskDataList.add(ta);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mTaskDataList.clear();
                ArrayList<Task> list = dataSnapshot.getValue(Project.class).getmTasks();
                for (Task ta : list) {
                    mTaskDataList.add(ta);

                }
                adapter.notifyDataSetChanged();
            }
        }, getContext());
    }

    private void showCreateTaskDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        String title = "";

        title = getResources().getString(R.string.subject_name_label);

        final View view = inflater.inflate(R.layout.create_task_dialog, null);
        TextView time = (TextView) view.findViewById(R.id.tv_task_time);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogDateTimePicker.showTimePicker(getActivity(), new DialogDateTimePicker.OnGetTimeListener() {
                    @Override
                    public void onChange(RadialPickerLayout view, int hourOfDay, int minute, int second) {
                        String timeStr = hourOfDay + "h" + minute + "p";
                        time.setText(timeStr);
                    }
                });
            }
        });
        AutoCompleteTextView name = (AutoCompleteTextView) view.findViewById(R.id.ac_subject_name);
        String[] listSubject = getResources().getStringArray(R.array.subject_array);
        name.setAdapter(
                new ArrayAdapter<String>(
                        getContext(),
                        android.R.layout.simple_list_item_1, listSubject
                )
        );
        builder.setTitle(title)
                .setView(view)
                .setPositiveButton(R.string.create_project, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Task t = new Task();
                        t.setmTitle(name.getText().toString());
                        t.setmDayOfWeek(mTabPostition);
                        t.setmDeadline(time.getText().toString());
                        mAllTaskWithNoFillter.add(t);
                        CurrentUser.updateTask(mProjectId, mAllTaskWithNoFillter);
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
