package com.dev.wacteam.taskmanager.model;

import java.util.ArrayList;

public class User {

    public User() {
        profile = new Profile();
        setting = new Setting();
    }

    private ArrayList<Project> projects;
    private ArrayList<String> friends; //list friend id
    private Profile profile;
    private Setting setting;


    public void setSetting(Setting setting) {
        this.setting = setting;
    }

    public Setting getSetting() {
        return setting;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setFriends(ArrayList<String> friends) {
        this.friends = friends;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public ArrayList<Project> getProjects() {
        return projects;
    }

    public void setProjects(ArrayList<Project> projects) {
        this.projects = projects;
    }
}
