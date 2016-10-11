package com.dev.wacteam.taskmanager.Model;

import android.content.Context;

/**
 * Created by giuse96suoire on 10/11/2016.
 */
public abstract class DialogObject {
    private String mTitle;
    private String mMessage;
    private String mNegButtonLabel;
    private String mPosButtonLabel;
    private Context mContext;

    public DialogObject(Context context){
        this.mContext = context;
    }
    public String getmTitle() {
        return mTitle;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public String getmMessage() {
        return mMessage;
    }

    public String getmNegButtonLabel() {
        return mNegButtonLabel;
    }

    public String getmPosButtonLabel() {
        return mPosButtonLabel;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public void setmNegButtonLabel(String mNegButtonLabel) {
        this.mNegButtonLabel = mNegButtonLabel;
    }

    public void setmPosButtonLabel(String mPosButtonLabel) {
        this.mPosButtonLabel = mPosButtonLabel;
    }

    public abstract void mOnNegButtonClick();
    public abstract void mOnPosButtonClick();
}
