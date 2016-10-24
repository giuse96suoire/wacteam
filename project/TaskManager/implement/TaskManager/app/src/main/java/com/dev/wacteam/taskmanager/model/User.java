package com.dev.wacteam.taskmanager.model;

import android.net.Uri;

import java.util.ArrayList;

/**
 * Created by giuse96suoire on 10/12/2016.
 */
public class User {

    public User() {

    }

    private String uid;
    private String displayName;
    private String dob;
    private String email;
    private boolean emailVerified;
    private Uri photoUrl;
    private ArrayList<Project> listProject;
    private ArrayList<User> listFriend;
    private Setting setting;
    private String providerId;
    private String phoneNumber;
    private String address;
    private boolean gender;
    public void setGender(boolean gender) { // true is man, false is woman
        this.gender = gender;
    }

    public boolean getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public void setListFriend(ArrayList<User> listFriend) {
        this.listFriend = listFriend;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public ArrayList<User> getListFriend() {
        return listFriend;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setSetting(Setting setting) {
        this.setting = setting;
    }

    public Setting getSetting() {
        return setting;
    }

    public ArrayList<Project> getListProject() {
        return listProject;
    }

    public void setListProject(ArrayList<Project> listProject) {
        this.listProject = listProject;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhotoUrl(Uri photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDob() {
        return dob;
    }

    public String getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public Uri getPhotoUrl() {
        return photoUrl;
    }
}
