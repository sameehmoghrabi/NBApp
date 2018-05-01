package sameeh.com.nbapp;

import android.app.Application;

import org.greenrobot.greendao.database.Database;

import sameeh.com.nbapp.Models.dao.DaoMaster;
import sameeh.com.nbapp.Models.dao.DaoSession;

/**
 * Created by samee on 4/1/2018.
 */

public class MyApp extends Application {
    private DaoSession daoSession;
    @Override
    public void onCreate() {
        super.onCreate();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "players-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}


