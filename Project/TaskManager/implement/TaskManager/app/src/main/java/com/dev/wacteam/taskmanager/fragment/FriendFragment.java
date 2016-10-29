package com.dev.wacteam.taskmanager.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.dev.wacteam.taskmanager.R;
import com.dev.wacteam.taskmanager.adapter.FriendAdapter;
import com.dev.wacteam.taskmanager.listener.OnGetDataListener;
import com.dev.wacteam.taskmanager.model.User;
import com.dev.wacteam.taskmanager.system.CurrentUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FriendFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FriendFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FriendFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FriendFragment() {
        // Required empty public constructor
    }

    private RecyclerView mRvListFriend;
    private ArrayList<User> mListFriend;
    private FriendAdapter mFriendAdapter;
    private EditText mEtSearchFriend;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRvListFriend = (RecyclerView) getView().findViewById(R.id.rv_friends);
        mRvListFriend.setHasFixedSize(true);
        mRvListFriend.setLayoutManager(new LinearLayoutManager(getActivity()));
        mListFriend = new ArrayList<>();
        mEtSearchFriend = (EditText) getView().findViewById(R.id.et_searchFriend);
        mEtSearchFriend.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    mListFriend.clear();
                    mGetAllFriend();
                } else {
                    mSearchFriend(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mFriendAdapter = new FriendAdapter(getContext(), getActivity(), mListFriend);
        mRvListFriend.setAdapter(mFriendAdapter);
        //TODO: get list friend from current user
        mGetAllFriend();
    }

    ProgressDialog mProgressDialog;

    private void mGetAllFriend() {
//        mListFriend = CurrentFriend.getmListFriend();
//        mFriendAdapter.notifyDataSetChanged();
        CurrentUser.getAllFriend(new OnGetDataListener() {
            @Override
            public void onStart() {
                mProgressDialog = new ProgressDialog(getActivity(),
                        R.style.AppTheme_Dark_Dialog);
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setMessage(getString(R.string.dialog_get_friend));
                mProgressDialog.show();
            }

            @Override
            public void onSuccess(DataSnapshot data) {
//                mListFriend.clear();

                if (data.getChildrenCount() == 0 || data == null) {
                    mProgressDialog.dismiss();
                } else {
//                    for (DataSnapshot value : data.getChildren()) {
                    User user = data.getValue(User.class);
                    mListFriend.add(user);
//                    }
                    mFriendAdapter.notifyDataSetChanged();
                    mProgressDialog.dismiss();
                }
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        }, getContext());
    }


    private void mSearchFriend(String emailOrName) {
        CurrentUser.searchFriend(getContext(), emailOrName, new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot data) {
                boolean isExist = false;
                User user = data.getValue(User.class);
                for (int i = 0; i < mListFriend.size(); i++) {
                    if (!(mListFriend.get(i).getProfile().getEmail().contains(emailOrName)) && !(mListFriend.get(i).getProfile().getDisplayName().contains(emailOrName))) {
                        mListFriend.remove(i);
                        mFriendAdapter.notifyDataSetChanged();
                    }
//                    System.out.println(mListFriend.get(i).getEmail() +"email ==========>");

                }
                for (int i = 0; i < mListFriend.size(); i++) {
                    if ((mListFriend.get(i).getProfile().getEmail().equals(user.getProfile().getEmail()))) {
                        isExist = true;
                    }
                }
                if (!isExist) {
                    mListFriend.add(user);
                    mFriendAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FriendFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FriendFragment newInstance(String param1, String param2) {
        FriendFragment fragment = new FriendFragment();
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
        return inflater.inflate(R.layout.fragment_friend, container, false);
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

    // thiet lap font chu friends:

}
