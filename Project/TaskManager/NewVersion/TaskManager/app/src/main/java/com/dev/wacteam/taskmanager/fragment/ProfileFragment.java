package com.dev.wacteam.taskmanager.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dev.wacteam.taskmanager.R;
import com.dev.wacteam.taskmanager.activity.MainActivity;
import com.dev.wacteam.taskmanager.dialog.DialogDateTimePicker;
import com.dev.wacteam.taskmanager.dialog.YesNoDialog;
import com.dev.wacteam.taskmanager.model.User;
import com.dev.wacteam.taskmanager.system.CurrentUser;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }

    private EditText mEtFullName, mEtAddress, mEtPhoneNumber, mEtDob;
    private TextView mEtEmail;
    private AutoCompleteTextView mAcJob;
    private Button mBtnSave;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        User user = new User();
        user = CurrentUser.getUserProfileFromLocal(getContext());
        mEtEmail.setText(user.getProfile().getEmail());
        mEtAddress.setText(user.getProfile().getAddress());
        mEtFullName.setText(user.getProfile().getDisplayName());
        mEtPhoneNumber.setText(user.getProfile().getPhoneNumber());
        mEtDob.setText(user.getProfile().getDob());
        String[] job_arr = getResources().getStringArray(R.array.job_array);
//        mAcJob.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, job_arr));

        mEtDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogDateTimePicker.showDatePicker(1950, 2008, getActivity(), new DialogDateTimePicker.OnGetDateTimeListener() {
                    @Override
                    public void onChange(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        mEtDob.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                });
            }
        });

        mBtnSave = (Button) getView().findViewById(R.id.btn_saveInfo);
        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YesNoDialog.mShow(getContext(), getString(R.string.dialog_update_information), new YesNoDialog.OnClickListener() {
                    @Override
                    public void onYes(DialogInterface dialog, int which) {
                        User user = new User();
                        user.getProfile().setEmail(mEtEmail.getText().toString());
                        user.getProfile().setUid(CurrentUser.getUserProfileFromLocal(getContext()).getProfile().getUid());
                        user.getProfile().setDisplayName(mEtFullName.getText().toString());
                        user.getProfile().setAddress(mEtAddress.getText().toString());
                        user.getProfile().setPhoneNumber(mEtPhoneNumber.getText().toString());
                        user.getProfile().setDob(mEtDob.getText().toString());
                        mUpdateInfo(user);
                        ((MainActivity) getActivity()).mUpdateUserUI(user);
                    }

                    @Override
                    public void onNo(DialogInterface dialog, int which) {
                        //do nothing
                    }
                });

            }
        });

    }

    private void init() {
        mEtFullName = (EditText) getView().findViewById(R.id.et_fullName);
        mEtEmail = (TextView) getView().findViewById(R.id.et_email);
        mEtAddress = (EditText) getView().findViewById(R.id.et_address);
        mEtPhoneNumber = (EditText) getView().findViewById(R.id.et_phoneNumber);
//        mAcJob = (AutoCompleteTextView) getView().findViewById(R.id.et_job);
        mEtDob = (EditText) getView().findViewById(R.id.et_dob);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        return inflater.inflate(R.layout.fragment_profile, container, false);
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

    private void mUpdateInfo(User u) {
        CurrentUser.setUserProfileAndSettingToLocal(u, getContext());
        CurrentUser.setUserProfileToServer(getContext(),u);
    }
}
