package com.smartsoftasia.module.gblibrary.database;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.table.TableUtils;

import android.content.Context;
import android.util.Log;


/**
 * @author Gregoire BARRET
 * @file MeetingDatabaseManager.java
 * @date 12 oct. 2013
 * @todo 
 */
public class DatabaseManager<T> implements IRepository<T> {

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

}
