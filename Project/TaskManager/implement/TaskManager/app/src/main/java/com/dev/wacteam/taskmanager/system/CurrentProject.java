package com.dev.wacteam.taskmanager.system;

import android.content.Context;

import com.dev.wacteam.taskmanager.activity.MainActivity;
import com.dev.wacteam.taskmanager.adapter.ProjectAdapter;
import com.dev.wacteam.taskmanager.listener.OnGetDataListener;
import com.dev.wacteam.taskmanager.manager.NotificationsManager;
import com.dev.wacteam.taskmanager.model.Project;

import java.util.ArrayList;


//can remove this class
public class CurrentProject {
    private static ArrayList<Project> listProject = new ArrayList<>();
    private static onDataChangeListener mListener;
    public static void setListProect(ArrayList<Project> listProject) {
        CurrentProject.listProject = listProject;
    }

    public static void addProject(Project p) {
        boolean isFound = false;
        int index = 0;
        for (int i = 0; i < listProject.size(); i++) {
            if (listProject.get(i).getmProjectId().equals(p.getmProjectId())) {
                isFound = true;
                index = i;
                break;
            }
        }
        if (isFound) {
//            NotificationsManager.notifyProjectChange(listProject.get(index), p, context);
//            activity.changeNotificationIcon(true);
            listProject.remove(index);
            listProject.add(index, p);
        } else {
            listProject.add(p);
        }
//        if(mListener!=null){
//            mListener.onDateChange(listProject);
//        }
    }

    public static Project getProjectById(String id) {
        for (int i = 0; i < listProject.size(); i++) {
            if (listProject.get(i).getmProjectId().equals(id)) {
                return listProject.get(i);
            }
        }
        return null;
    }

    public static ArrayList<Project> getListProject() {
        return listProject;
    }

    public interface onDataChangeListener{
        public void onDateChange(ArrayList<Project> list);
    }
}
