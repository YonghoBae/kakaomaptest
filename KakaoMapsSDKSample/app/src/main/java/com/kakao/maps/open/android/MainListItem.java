package com.kakao.maps.open.android;

import android.app.Activity;

public class MainListItem {
    private String title;
    private String desc;
    private Class<? extends Activity> activity;

    public MainListItem(String title, String desc, Class<? extends Activity> activity) {
        this.title = title;
        this.desc = desc;
        this.activity = activity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Class<? extends Activity> getActivity() {
        return activity;
    }
}
