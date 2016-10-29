package com.dev.wacteam.taskmanager.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.wacteam.taskmanager.R;
import com.dev.wacteam.taskmanager.adapter.TabFragmentAdapter;
import com.dev.wacteam.taskmanager.listener.OnChildEventListener;
import com.dev.wacteam.taskmanager.model.Project;
import com.dev.wacteam.taskmanager.system.CurrentUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProjectDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProjectDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProjectDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mProjectId;
    private String mParam2;
    private Project mProject;
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;


    private OnFragmentInteractionListener mListener;

    public ProjectDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProjectDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProjectDetailFragment newInstance(String param1, String param2) {
        ProjectDetailFragment fragment = new ProjectDetailFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
            mProjectId = getArguments().getString("projectId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_tab_layout, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    private void getProjectDetail(String projectId) {
        CurrentUser.getProjectById(projectId, new OnChildEventListener() {
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                mProject = dataSnapshot.getValue(Project.class);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                mProject = dataSnapshot.getValue(Project.class);

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                mProject = dataSnapshot.getValue(Project.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mProject = dataSnapshot.getValue(Project.class);

            }
        }, getContext());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewPager = (ViewPager) getView().findViewById(R.id.viewPager);
        TabFragmentAdapter tabAdapter = new TabFragmentAdapter(getFragmentManager());
        tabLayout = (TabLayout) getView().findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
        String[] title;

        title = getResources().getStringArray(R.array.tab_schedule_title_array);
        tabAdapter.setCount(7);


        Bundle args = new Bundle();
        System.out.println("Project id =>>>>>>>>>>.. "+mProjectId);
        tabAdapter.setProjectId(mProjectId);
        viewPager.setAdapter(tabAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabAdapter.getItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        if (title != null) {
            for (int i = 0; i < tabLayout.getTabCount(); i++) {
                tabLayout.getTabAt(i).setText(title[i]);
            }
        } else {
            System.out.println("title is null! ================================>");
        }

//        viewPager.setAdapter(tabAdapter);

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

    public OnFragmentInteractionListener getmListener() {
        return mListener;
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
