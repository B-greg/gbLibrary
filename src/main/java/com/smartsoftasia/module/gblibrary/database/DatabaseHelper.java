package com.smartsoftasia.module.gblibrary.database;



import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;



/**
 * @author Gregoire Barret
 * @file DatabaseHelper.java
 * @date 12 oct. 2013
 */
public class DatabaseHelper<T> extends OrmLiteSqliteOpenHelper  {


//	// name of the database file for your application -- change to something appropriate for your app
//	private static String DATABASE_NAME;
//	// any time you make changes to your database objects, you may have to increase the database version
//	private static int DATABASE_VERSION;

	// the DAO object we use to access the SimpleData table
	private Dao<T, Integer> simpleDao = null;
	private Dao<T, String> stringDao = null;
	private RuntimeExceptionDao<T, Integer> simpleRuntimeDao = null;
	private Class<T> TClass;
	private List<Object> mList;
    private Context mContext;
	
	public DatabaseHelper(Context context, Class<T> TClass, List<Object> mList, String databaseName, int databaseVersion) {
		//super(context, dbName, null, dbVersion);
		super(context, databaseName, null, databaseVersion);
		this.TClass = TClass;
		this.mList = mList;
        this.mContext = context;
	}

	/**
	 * This is called when the database is first created. Usually you should call createTable statements here to create
	 * the tables that will store your data.
	 */
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onCreate");
			//DisplayListClass listClass = new DisplayListClass();
			//this.mList = listClass.init(mList);
			for(Object mlistObject: this.mList){
				TableUtils.createTable(connectionSource, mlistObject.getClass());
			}
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
	 * the various data to match the new version number.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onUpgrade");
			for(Object mlistObject: this.mList){
				TableUtils.dropTable(connectionSource, mlistObject.getClass(), true);
			}
			// after we drop the old databases, we create the new ones
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Returns the Database Access Object (DAO) for our SimpleData class. It will create it or just give the cached
	 * value.
	 */
	public Dao<T, Integer> getDao() throws SQLException {
		if (simpleDao == null) {
			simpleDao = getDao(TClass);
		}
		return simpleDao;
	}
	
	/**
	 * Returns the Database Access Object (DAO) for our SimpleData class. It will create it or just give the cached
	 * value.
	 */
	public Dao<T, String> getDaoString() throws SQLException {
		if (stringDao == null) {
			stringDao = getDao(TClass);
		}
		return stringDao;
	}

	/**
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our SimpleData class. It will
	 * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
	 */
	public RuntimeExceptionDao<T, Integer> getSimpleDataDao() {
		if (simpleRuntimeDao == null) {
			simpleRuntimeDao = getRuntimeExceptionDao(TClass);
		}
		return simpleRuntimeDao;
	}

	/**
	 * Close the database connections and clear any cached DAOs.
	 */
	@Override
	public void close() {
		super.close();
		simpleRuntimeDao = null;
	}

    public Context getContext() {
        return mContext;
    }

    static class DisplayListClass{
		public DisplayListClass(){}
		public List<Object> init(List<Object> mList){
			List<Object> listClass = new ArrayList<Object>();
			for (Iterator iterator = mList.iterator(); iterator.hasNext();) {
				Object object = (Object) iterator.next();
				listClass.add(object);
			}
			return listClass;
		}
	}
}
