package com.dev.wacteam.taskmanager.model;

import java.util.ArrayList;

/**
 * Created by giuse96suoire on 10/12/2016.
 */
public class User extends UserAbstract{
    public User(){

    }
    @Override
    ArrayList<Project> mGetAllProject(String userId) {
        return null;
    }

    @Override
    ArrayList<Project> mGetAProject(String userId, String projectId) {
        return null;
    }
}
