package com.smartsoftasia.module.gblibrary.database;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.table.TableUtils;
import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.JobManager;
import com.path.android.jobqueue.Params;
import com.path.android.jobqueue.config.Configuration;
import com.path.android.jobqueue.log.CustomLogger;

import android.content.Context;
import android.provider.ContactsContract;
import android.renderscript.RenderScript;
import android.util.Log;


/**
 * @author Gregoire BARRET
 * @file MeetingDatabaseManager.java
 * @date 12 oct. 2013
 * @todo 
 */
public class DatabaseManager<T extends DatabaseModel> implements IRepository<T> {

    private DatabaseHelper<T> helper;
    protected DatabaseManager(Context context, Class<T> TClass, List<Object> mList, String databaseName, int databaseVersion) {
        helper = new DatabaseHelper<T>(context, TClass, mList,databaseName,databaseVersion);
        //helper = new DatabaseHelper<T>(context, TClass, dbName, dbVersion);
    }

    protected DatabaseHelper<T> getHelper() {
        return helper;
    }

    @Override
    public List<T> GetAll() {
        List<T> meeting = null;
        try {
            meeting = getHelper().getDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return meeting;
    }

    @Override
    public T GetById(int id) {
        T meeting = null;
        try {
            meeting = getHelper().getDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return meeting;
    }

    @Override
    public void Update(T entite) {
        try {
            getHelper().getDao().update(entite);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Delete(T entite) {
        try {
            getHelper().getDao().delete(entite);
        } catch (SQLException e) {
            e.printStackTrace();
        }	
    }

    @Override
    public void DeleteAll() {
        try {
           // getHelper().getDao().delete(this.GetAll());
            TableUtils.clearTable(getHelper().getDao().getConnectionSource(), getHelper().getDao().getDataClass());
        } catch (SQLException e) {
            e.printStackTrace();
        }   
    }

    @Override
    public void refresh(T entite) {
        try {
            getHelper().getDao().refresh(entite);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Add(T entite) {
        try {
            getHelper().getDao().create(entite);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void saveList(final List<T>list){
        class SaveTask extends Job {
            public static final int PRIORITY = 2;

            protected SaveTask(Params params) {
                super(new Params(PRIORITY).groupBy(JobHandler.DATABASE_GROUP));
            }

            @Override
            public void onAdded() {
            }


            @Override
            public void onRun() throws Throwable {
                if(list == null) return;
                for(int i=0;i<list.size();i++){
                    if(GetById((list.get(i)).getId()) != null)
                        Update(list.get(i));
                    else
                        Add(list.get(i));
                }
            }

            @Override
            protected void onCancel() {

            }

            @Override
            protected boolean shouldReRunOnThrowable(Throwable throwable) {
                return false;
            }
        }
        JobHandler.getInstance(helper.getContext()).getJobManager().addJobInBackground(new SaveTask(null));

    }

    public void save(final T item){
        class SaveTask extends Job {
            public static final int PRIORITY = 1;

            protected SaveTask(Params params) {
                super(new Params(PRIORITY).groupBy(JobHandler.DATABASE_GROUP));
            }

            @Override
            public void onAdded() {

            }

            @Override
            public void onRun() throws Throwable {
                if(GetById((item).getId()) != null)
                    Update(item);
                else
                    Add(item);
            }

            @Override
            protected void onCancel() {

            }

            @Override
            protected boolean shouldReRunOnThrowable(Throwable throwable) {
                return false;
            }
        }
        JobHandler.getInstance(helper.getContext()).getJobManager().addJobInBackground(new SaveTask(null));
    }


    public interface IDatabase{
        public interface OnSearch<T>{
            public void onSearch(List<T>results);
        }
    }

}
