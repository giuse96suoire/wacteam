package com.dev.wacteam.taskmanager.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.wacteam.taskmanager.R;
import com.dev.wacteam.taskmanager.dialog.DialogDateTimePicker;
import com.dev.wacteam.taskmanager.dialog.YesNoDialog;
import com.dev.wacteam.taskmanager.model.Project;
import com.dev.wacteam.taskmanager.model.User;
import com.dev.wacteam.taskmanager.system.CurrentFriend;
import com.dev.wacteam.taskmanager.system.CurrentUser;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateProject.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateProject#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateProject extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private EditText mEtProjectName, mEtProjectDescription;
    private Spinner mSpProjectType, mSpProjectLeader;
    private ListView mLvProjectMember;
    private TextView mTvProjectDeadline, mTvProjectCreateAt, mTvMember;
    private Button mBtnSaveProject, mBtnResetProject;
    private ArrayList<String> mList_member;
    private ArrayAdapter mMemberAdapter;
    private ArrayList<String> mListFriendName;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mEtProjectName = (EditText) getView().findViewById(R.id.et_project_name);
        Bundle args = getArguments();
        mEtProjectName.setText(args.getString("project_name"));
//        mLvProjectMember = (ListView) getView().findViewById(R.id.lv_project_member);
//        mSpProjectLeader = (Spinner) getView().findViewById(R.id.sp_project_leader);
//        mSpProjectType = (Spinner) getView().findViewById(R.id.sp_project_type);
        String[] projecTypeData = getResources().getStringArray(R.array.project_type_array);
        mSpProjectType.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, projecTypeData));

//        ArrayList<User> listFriend;
        mListFriendName = new ArrayList<>();
//        listFriend = CurrentFriend.getmListFriend();
//        for (User u : listFriend) {
//            mListFriendName.add(u.getProfile().getDisplayName());
//        }


//        mSpProjectLeader.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, mListFriendName));
        mBtnSaveProject = (Button) getView().findViewById(R.id.btn_save);
        mBtnResetProject = (Button) getView().findViewById(R.id.btn_reset);
//        mTvMember = (TextView) getView().findViewById(R.id.tv_member);
//        mTvProjectDeadline = (TextView) getView().findViewById(R.id.tv_project_deadline);
//        mTvProjectCreateAt = (TextView) getView().findViewById(R.id.tv_create_time);
        mEtProjectDescription = (EditText) getView().findViewById(R.id.et_project_description);
        mBtnSaveProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YesNoDialog.mShow(getContext(), "Are you sure to create this project?", new YesNoDialog.OnClickListener() {
                    @Override
                    public void onYes(DialogInterface dialog, int which) {
                        mCreateProject();
                    }

                    @Override
                    public void onNo(DialogInterface dialog, int which) {

                    }
                });
            }
        });

        mBtnResetProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YesNoDialog.mShow(getContext(), getString(R.string.confirm_reset_project), new YesNoDialog.OnClickListener() {
                    @Override
                    public void onYes(DialogInterface dialog, int which) {
                        mResetProjectForm();
                    }

                    @Override
                    public void onNo(DialogInterface dialog, int which) {

                    }
                });
            }
        });
        mTvProjectDeadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogDateTimePicker.showDatePicker(2016, 2050, getActivity(), new DialogDateTimePicker.OnGetDateTimeListener() {
                    @Override
                    public void onChange(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        mTvProjectDeadline.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                });
            }
        });
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = format.format(new Date());
        mTvProjectCreateAt.setText(currentDate);
        mList_member = new ArrayList<>();
        mList_member.add(getString(R.string.add_more));
        mTvMember.setText(getResources().getString(R.string.member_label) + " (" + (mList_member.size() - 1) + ")");
        mMemberAdapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, mListFriendName);
        mLvProjectMember.setAdapter(mMemberAdapter);
        mLvProjectMember.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    mShowListFriendDialog();
                    if (mAdapterFriendDialog != null) {
                        for (int i = 0; i < mAdapterFriendDialog.getCount(); i++) {
                            for (String s : mList_member) {
                                if (mAdapterFriendDialog.getItem(i).equals(s)) {
                                    lv_friend.setItemChecked(i, true);
                                }
                            }
                        }
                    }

                }
            }
        });
    }

    private ArrayAdapter<String> mAdapterFriendDialog;
    private ListView lv_friend;

    private void mShowListFriendDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.list_friend_dialog, null);

        builder.setTitle(R.string.choose_member)
                .setView(view)
                .setPositiveButton(R.string.add_member, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        mMemberAdapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, mList_member);
//                        mLvProjectMember.setAdapter(mMemberAdapter);
                        mMemberAdapter.notifyDataSetChanged();

                    }
                })
                .setNegativeButton(R.string.cancel_add_member, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        AlertDialog dialog = builder.create();
        lv_friend = (ListView) view.findViewById(R.id.lv_friend);

        mAdapterFriendDialog = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_checked, mListFriendName);
        lv_friend.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        lv_friend.setAdapter(mAdapterFriendDialog);

        lv_friend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value = lv_friend.getItemAtPosition(position).toString();
                if (!lv_friend.isItemChecked(position)) {
                    Toast.makeText(getContext(), getString(R.string.Remove_member) + value, Toast.LENGTH_LONG).show();
                    mList_member.remove(value);
                } else {
                    Toast.makeText(getContext(), getString(R.string.add_member) + value, Toast.LENGTH_LONG).show();
                    mList_member.add(value);
                }
            }
        });
        dialog.show();
    }

    private void mResetProjectForm() {
        mEtProjectName.setText("");
        mEtProjectDescription.setText("");


    }

    private void mCreateProject() {
        Project project = new Project();
        project.setmTitle((mEtProjectName.getText().toString().length() == 0) ? getString(R.string.Create_project) : mEtProjectName.getText().toString());
        project.setmCreateDate(mTvProjectCreateAt.getText().toString());
        project.setmDeadline(mTvProjectDeadline.getText().toString());
        project.setmDescription(mEtProjectDescription.getText().toString());
        project.setmComplete(0);
        ArrayList<String> members = new ArrayList<>();
        String uid = CurrentUser.getInstance().getUserProfileFromLocal(getContext()).getProfile().getUid();
        members.add(uid);
        project.setmMembers(members);
        project.setmLeaderId(uid);

        CurrentUser.createProject(project, getContext());
    }

    public CreateProject() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateProject.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateProject newInstance(String param1, String param2) {
        CreateProject fragment = new CreateProject();
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
        return inflater.inflate(R.layout.fragment_create_project, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
