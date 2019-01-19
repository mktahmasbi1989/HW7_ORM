package com.example.mohamdkazem.advancetodolist;

import android.app.Application;
import com.example.mohamdkazem.advancetodolist.Model.DaoMaster;
import com.example.mohamdkazem.advancetodolist.Model.DaoSession;
import com.example.mohamdkazem.advancetodolist.dataBase.MyDevHelper;
import org.greenrobot.greendao.database.Database;

public class App extends Application {

    private static App app;
    DaoSession daoSession;

    public static  App getApp() {
        return app;
    }
    public DaoSession getDaoSession() {
        return daoSession;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        MyDevHelper myDevHelper=new MyDevHelper(this,"tasks");
        Database database=myDevHelper.getWritableDb();

        daoSession=new DaoMaster(database).newSession();

        app=this;

    }
}
