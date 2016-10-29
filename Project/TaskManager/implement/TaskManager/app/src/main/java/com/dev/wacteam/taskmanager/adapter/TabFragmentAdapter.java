package com.dev.wacteam.taskmanager.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.dev.wacteam.taskmanager.fragment.TabFragment;


public class TabFragmentAdapter extends FragmentPagerAdapter {
    private int count;
    private String mProjectId;
    private int mProjectType;
    
    public void setmProjectType(int mProjectType) {
        this.mProjectType = mProjectType;
    }

    public String getmProjectId() {
        return mProjectId;
    }


    public TabFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Bundle args = new Bundle();
        args.putString("projectId", getProjectId());
        args.putInt("projectType", getmProjectType());
        args.putInt("tabPosition", position);
        TabFragment f = new TabFragment();
        f.setArguments(args);
        return f;
    }

    public void setmProjectId(String mProjectId) {
        this.mProjectId = mProjectId;
    }

    public int getmProjectType() {
        return mProjectType;
    }

    public void setProjectId(String projectId) {
        this.mProjectId = projectId;
    }

    public String getProjectId() {
        return mProjectId;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return String.valueOf(position);
    }
}
