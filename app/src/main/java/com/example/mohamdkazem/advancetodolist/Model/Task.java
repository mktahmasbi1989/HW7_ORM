package com.example.mohamdkazem.advancetodolist.Model;


import org.greenrobot.greendao.annotation.Entity;

import java.security.acl.LastOwnerException;
import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;

@Entity
public class Task   {
    private String mTitle;
    private String mDetail;
    private Date mDate;
    private long mTime;
    private boolean mDone;
    private String imageUri;
    @Id(autoincrement = true)
    private Long taskId;
    private Long userId;
    @ToOne(joinProperty = "userId")
    private Users users;
    private String imgUri;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1469429066)
    private transient TaskDao myDao;
    @Generated(hash = 73025128)
    private transient Long users__resolvedKey;

    public Task(String title, String detail, Date myDate, Long userId) {
        this.mTitle=title;
        this.mDetail=detail;
        this.mDate=myDate;
        this.userId=userId;

    }



    @Generated(hash = 1575117249)
    public Task(String mTitle, String mDetail, Date mDate, long mTime, boolean mDone,
            String imageUri, Long taskId, Long userId, String imgUri) {
        this.mTitle = mTitle;
        this.mDetail = mDetail;
        this.mDate = mDate;
        this.mTime = mTime;
        this.mDone = mDone;
        this.imageUri = imageUri;
        this.taskId = taskId;
        this.userId = userId;
        this.imgUri = imgUri;
    }








    @Generated(hash = 733837707)
    public Task() {
    }








    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
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
    public Long getTaskId() {
        return this.taskId;
    }
    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }


    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1828909447)
    public Users getUsers() {
        Long __key = this.userId;
        if (users__resolvedKey == null || !users__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UsersDao targetDao = daoSession.getUsersDao();
            Users usersNew = targetDao.load(__key);
            synchronized (this) {
                users = usersNew;
                users__resolvedKey = __key;
            }
        }
        return users;
    }


    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 368590353)
    public void setUsers(Users users) {
        synchronized (this) {
            this.users = users;
            userId = users == null ? null : users.getUserId();
            users__resolvedKey = userId;
        }
    }


    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }


    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }


    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }


    public String getPhotoName() {
        String userId= String.valueOf(getUserId());
        String taskId= String.valueOf(getTaskId());
        return "IMG_" + userId+taskId + ".jpg";
    }


    public String getImgUri() {
        return this.imgUri;
    }








    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }








    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1442741304)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTaskDao() : null;
    }



}
