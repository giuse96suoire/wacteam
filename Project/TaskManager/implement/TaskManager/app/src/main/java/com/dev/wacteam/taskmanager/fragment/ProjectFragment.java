package com.dev.wacteam.taskmanager.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.wacteam.taskmanager.R;
import com.dev.wacteam.taskmanager.activity.MainActivity;
import com.dev.wacteam.taskmanager.adapter.ProjectAdapter;
import com.dev.wacteam.taskmanager.listener.OnChildEventListener;
import com.dev.wacteam.taskmanager.manager.NotificationsManager;
import com.dev.wacteam.taskmanager.model.Project;
import com.dev.wacteam.taskmanager.system.CurrentUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProjectFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProjectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProjectFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ProjectFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProjectFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProjectFragment newInstance(String param1, String param2) {
        ProjectFragment fragment = new ProjectFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_project, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) return;
        if (activity instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnFragmentInteractionListener ");
        }
    }

    private RecyclerView mLvListProject;
    private ProjectAdapter mAdapter;
    private ArrayList<Project> mListProject;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mListProject = new ArrayList<>();

        mAdapter = new ProjectAdapter(getContext(), getActivity(), mListProject);
//        mListProject = CurrentProject.getListProject(new CurrentProject.onDataChangeListener() {
//            @Override
//            public void onDateChange(ArrayList<Project> list) {
//                mListProject = list;
//                mAdapter.notifyDataSetChanged();
//            }
//        });

        mLvListProject = (RecyclerView) getView().findViewById(R.id.rv_project);

        mLvListProject.setHasFixedSize(true);
        mLvListProject.setLayoutManager(new LinearLayoutManager(getActivity()));
        mLvListProject.setAdapter(mAdapter);

        mGetAllProject();

    }

    private void mGetAllProject() {
        CurrentUser.getAllProject(new OnChildEventListener() {
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Project p = dataSnapshot.getValue(Project.class);
                boolean notFound = true;
//                mListProject.clear();
                if (mListProject.size() > 0) {
                    for (int i = 0; i < mListProject.size(); i++) {
                        if (mListProject.get(i).getmProjectId().equals(p.getmProjectId())) {
                            NotificationsManager.notifyProjectChange(mListProject.get(i), p, getContext());
//                            ((MainActivity) getActivity()).changeNotificationIcon(true);
                            mListProject.remove(i);
                            mListProject.add(i, p);
                            notFound = false;
                            break;
                        }
                    }
                }
                if (notFound) {
                    mListProject.add(p);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Project p = dataSnapshot.getValue(Project.class);
//                mListProject.clear();
                if (mListProject.size() > 0) {
                    for (int i = 0; i < mListProject.size(); i++) {
                        if (mListProject.get(i).getmProjectId().equals(p.getmProjectId())) {
                            NotificationsManager.notifyProjectChange(mListProject.get(i), p, getContext());
                            if (getActivity() != null) {
                                ((MainActivity) getActivity()).changeNotificationIcon(true);
                            }
                            mListProject.remove(i);
                            break;
                        }
                    }
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Project p = dataSnapshot.getValue(Project.class);
                boolean notFound = true;
//                mListProject.clear();
                if (mListProject.size() > 0) {
                    for (int i = 0; i < mListProject.size(); i++) {
                        if (mListProject.get(i).getmProjectId().equals(p.getmProjectId())) {
                            NotificationsManager.notifyProjectChange(mListProject.get(i), p, getContext());
                            ((MainActivity) getActivity()).changeNotificationIcon(true);
                            mListProject.remove(i);
                            mListProject.add(i, p);
                            notFound = false;
                            break;
                        }
                    }
                }
                if (notFound) {
                    mListProject.add(p);
                }
                mAdapter.notifyDataSetChanged();
            }
        }, getContext());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
