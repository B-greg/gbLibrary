package com.smartsoftasia.module.gblibrary.database;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.table.TableUtils;
import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import android.content.Context;


/**
 * The type Database manager.
 * @param <T>  the type parameter
 * @author Gregoire BARRET
 * @file MeetingDatabaseManager.java
 * @date 12 oct. 2013
 */
public class DatabaseManager<T extends DatabaseModel> implements IRepository<T> {

    private DatabaseHelper<T> helper;

    /**
     * Instantiates a new Database manager.
     *
     * @param context the context
     * @param TClass the t class
     * @param mList the m list
     * @param databaseName the database name
     * @param databaseVersion the database version
     */
    protected DatabaseManager(Context context, Class<T> TClass, List<Object> mList, String databaseName, int databaseVersion) {
        helper = new DatabaseHelper<T>(context, TClass, mList,databaseName,databaseVersion);
        //helper = new DatabaseHelper<T>(context, TClass, dbName, dbVersion);
    }

    /**
     * Gets helper.
     *
     * @return the helper
     */
    public DatabaseHelper<T> getHelper() {
        return helper;
    }

    @Override
    public List<T> GetAll() {
        List<T> items = null;
        try {
            items = getHelper().getDao().queryForEq(DatabaseModel.ENABLE, true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public T GetById(int id) {
        T items = null;
        try {
            items = getHelper().getDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
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
    public void CreateOrUpdate(T entite) {
        try {
            getHelper().getDao().createOrUpdate(entite);
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

    public T MaxCreatedAt(){
        T items = null;
        try{
            QueryBuilder<T,Integer> qBuilder = getHelper().getDao().queryBuilder();
            qBuilder.orderBy(DatabaseModel.UPDATED_AT, false);// false for descending order
            qBuilder.limit(1l);
            List<T> listOfOne = qBuilder.query();
            if(listOfOne!=null && listOfOne.size()>0){
                items = listOfOne.get(0);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return items;
    }


    /**
     * Save list.
     * Set ENABLE COLUMN to true before saving.
     * This method work on job.
     * @param list the list
     */
    public void saveList(final Collection<T> list){
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
                for(T item : list){
                    item.enable = true;
                    CreateOrUpdate(item);
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

    /**
     * Save void.
     * Set ENABLE COLUMN to true before saving.
     * This method work on job.
     *
     * @param item the item
     */
    public void save(final T item){
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
                item.enable = true;
                CreateOrUpdate(item);
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


    /**
     * Save void.
     * Set ENABLE COLUMN to true before saving.
     * This method work on job.
     *
     * @param id the id of the item
     */
    public void delete(final int id){
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
                T item = GetById(id);
                if(item != null){
                    Delete(item);
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

    /**
     * Gets all.
     * Only is ENALBE column is set to true.
     *This method work on job.
     * @param listener the listener
     */
    public void getAll(IDatabase.OnGetAll<T> listener) {
        class Task extends Job {
            public static final int PRIORITY = 2;
            private IDatabase.OnGetAll<T>  listener;

            protected Task(Params params, IDatabase.OnGetAll<T>  listener) {
                super(new Params(PRIORITY).groupBy(JobHandler.DATABASE_GROUP));
                this.listener = listener;
            }

            @Override
            public void onAdded() {

            }

            @Override
            public void onRun() throws Throwable {
                if(listener!=null) listener.onGetAll(DatabaseManager.this.GetAll());
            }

            @Override
            protected void onCancel() {

            }

            @Override
            protected boolean shouldReRunOnThrowable(Throwable throwable) {
                return false;
            }
        }
        JobHandler.getInstance(helper.getContext()).getJobManager().addJobInBackground(new Task(null,listener ));
    }

    /**
     * Gets by id.
     * Regardless ENEBLE column.
     *This method work on job.
     *
     * @param id the id
     * @param listener the listener
     */
    public void getById(int id, IDatabase.OnGetById<T> listener) {
        class Task extends Job {
            public static final int PRIORITY = 2;
            private IDatabase.OnGetById<T>  listener;
            private int id;

            protected Task(int id, IDatabase.OnGetById<T>  listener) {
                super(new Params(PRIORITY).groupBy(JobHandler.DATABASE_GROUP));
                this.listener = listener;
                this.id = id;
            }

            @Override
            public void onAdded() {

            }

            @Override
            public void onRun() throws Throwable {
                if(listener!=null) listener.onGetById(DatabaseManager.this.GetById(id));
            }

            @Override
            protected void onCancel() {

            }

            @Override
            protected boolean shouldReRunOnThrowable(Throwable throwable) {
                return false;
            }
        }
        JobHandler.getInstance(helper.getContext()).getJobManager().addJobInBackground(new Task(id, listener));
    }

    /**
     * Disable all.
     * Set the ENABLE column to false for all the table
     * This method work on job.
     *
     * @param listener the listener
     */
    public void disableAll(IDatabase.OnDisableAll listener) {
        class Task extends Job {
            public static final int PRIORITY = 2;
            private IDatabase.OnDisableAll  listener;

            protected Task(IDatabase.OnDisableAll  listener) {
                super(new Params(PRIORITY).groupBy(JobHandler.DATABASE_GROUP));
                this.listener = listener;
            }

            @Override
            public void onAdded() {

            }

            @Override
            public void onRun() throws Throwable {
                UpdateBuilder<T, Integer> updateBuilder = getHelper().getDao().updateBuilder();
                updateBuilder.where().ge(DatabaseModel.ID, 0);
                updateBuilder.updateColumnValue(DatabaseModel.ENABLE, false);
                updateBuilder.update();

            }

            @Override
            protected void onCancel() {

            }

            @Override
            protected boolean shouldReRunOnThrowable(Throwable throwable) {
                return false;
            }
        }
        JobHandler.getInstance(helper.getContext()).getJobManager().addJobInBackground(new Task(listener));
        if(listener!=null) listener.onDisableAll();
    }


    /**
     * The interface I database.
     */
    public interface IDatabase{
        /**
         * The interface On search.
         * @param <T>  the type parameter
         */
        public interface OnSearch<T>{
            /**
             * On search.
             *
             * @param results the results
             */
            public void onSearch(List<T>results);
        }

        /**
         * The interface On get all.
         * @param <T>  the type parameter
         */
        public interface OnGetAll<T>{
            /**
             * On get all.
             *
             * @param results the results
             */
            public void onGetAll(List<T>results);
        }

        /**
         * The interface On get by id.
         * @param <T>  the type parameter
         */
        public interface OnGetById<T>{
            /**
             * On get by id.
             *
             * @param result the result
             */
            public void onGetById(T result);
        }

        /**
         * The interface On disable all.
         */
        public interface OnDisableAll{
            /**
             * On disable all.
             */
            public void onDisableAll();
        }
    }

}
