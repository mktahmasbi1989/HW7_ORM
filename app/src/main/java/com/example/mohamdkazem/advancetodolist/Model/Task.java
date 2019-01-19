package com.example.mohamdkazem.advancetodolist.Model;


import org.greenrobot.greendao.annotation.Entity;

import java.util.Date;
import java.util.UUID;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class Task   {
    private String mTitle;
    private String mDetail;
    private Date mDate;
    private long mTime;
    private boolean mDone;

    @Id(autoincrement = true)
    private Long userId;


    public Task(String title, String detail, Date myDate) {

    }


    @Generated(hash = 1469785507)
    public Task(String mTitle, String mDetail, Date mDate, long mTime,
            boolean mDone, Long userId) {
        this.mTitle = mTitle;
        this.mDetail = mDetail;
        this.mDate = mDate;
        this.mTime = mTime;
        this.mDone = mDone;
        this.userId = userId;
    }


    @Generated(hash = 733837707)
    public Task() {
    }

    public Task(String title, String detail, UUID uuid) {

    }


    public String getMTitle() {
        return this.mTitle;
    }


    public void setMTitle(String mTitle) {
        this.mTitle = mTitle;
    }


    public String getMDetail() {
        return this.mDetail;
    }


    public void setMDetail(String mDetail) {
        this.mDetail = mDetail;
    }


    public Date getMDate() {
        return this.mDate;
    }


    public void setMDate(Date mDate) {
        this.mDate = mDate;
    }


    public long getMTime() {
        return this.mTime;
    }


    public void setMTime(long mTime) {
        this.mTime = mTime;
    }


    public boolean getMDone() {
        return this.mDone;
    }


    public void setMDone(boolean mDone) {
        this.mDone = mDone;
    }


    public Long getUserId() {
        return this.userId;
    }


    public void setUserId(Long userId) {
        this.userId = userId;
    }


}
