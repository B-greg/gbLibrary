package com.smartsoftasia.module.gblibrary.database;

import android.content.Context;
import android.util.Log;

import com.path.android.jobqueue.JobManager;
import com.path.android.jobqueue.config.Configuration;
import com.path.android.jobqueue.log.CustomLogger;

/**
 * Created by gregoire on 9/24/14.
 */
public class JobHandler {

    public final static String DATABASE_GROUP = "database";
    private static JobHandler instance;
    private JobManager jobManager;

    private JobHandler(Context context) {
        configureJobManager(context);
    }

    private void configureJobManager(Context context) {
        Configuration configuration = new Configuration.Builder(context)
                .customLogger(new CustomLogger() {
                    private static final String TAG = "JOBS";
                    @Override
                    public boolean isDebugEnabled() {
                        return true;
                    }
                    @Override
                    public void d(String text, Object... args) {
                        Log.d(TAG, String.format(text, args));
                    }
                    @Override
                    public void e(Throwable t, String text, Object... args) {
                        Log.e(TAG, String.format(text, args), t);
                    }
                    @Override
                    public void e(String text, Object... args) {
                        Log.e(TAG, String.format(text, args));
                    }
                })
                .minConsumerCount(1)//always keep at least one consumer alive
                .maxConsumerCount(3)//up to 3 consumers at a time
                .loadFactor(3)//3 jobs per consumer
                .consumerKeepAlive(120)//wait 2 minute
                .build();
        jobManager = new JobManager(context, configuration);
    }

    public JobManager getJobManager() {
        return jobManager;
    }
    public static JobHandler getInstance(Context context) {
        if(instance == null ){
            instance = new JobHandler(context);
        }
        return instance;
    }
}
