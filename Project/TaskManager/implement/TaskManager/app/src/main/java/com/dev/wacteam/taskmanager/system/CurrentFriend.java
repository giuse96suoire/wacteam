package com.dev.wacteam.taskmanager.system;

import com.dev.wacteam.taskmanager.model.Project;
import com.dev.wacteam.taskmanager.model.User;

import java.util.ArrayList;



public class CurrentFriend {
    private static ArrayList<User> mListFriend = new ArrayList<>();

    public static void setmListFriend(ArrayList<User> listFriend) {
        mListFriend = listFriend;
    }

    public static void addFriend(User u) {
        boolean isFound = false;
        int index = 0;
        for (int i = 0; i < mListFriend.size(); i++) {
            if (mListFriend.get(i).getProfile().getUid().equals(u.getProfile().getUid())) {
                isFound = true;
                index = i;
                break;
            }
        }
        if (isFound) {
//            NotificationsManager.notifyProjectChange(mListFriend.get(index), p, context);
//            activity.changeNotificationIcon(true);
            mListFriend.remove(index);
            mListFriend.add(index, u);
        } else {
            mListFriend.add(u);
        }
//        if(mListener!=null){
//            mListener.onDateChange(mListFriend);
//        }
    }

    public static User getFriendById(String id) {
        for (int i = 0; i < mListFriend.size(); i++) {
            if (mListFriend.get(i).getProfile().getUid().equals(id)) {
                return mListFriend.get(i);
            }
        }
        return null;
    }

    public static ArrayList<User> getmListFriend() {
        return mListFriend;
    }
    public static int getFriendCount(){
        return getmListFriend().size();
    }
    public interface onDataChangeListener {
        public void onDateChange(ArrayList<Project> list);
    }
}
