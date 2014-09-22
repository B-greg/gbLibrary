package com.smartsoftasia.module.gblibrary.database;

import java.util.List;

/**
 * Created by gregoire on 9/22/14.
 */
public interface IDatabaseModel {
    public interface IFromDatabase<T>{
        public void onSuccess(List<T> item);
        public void onFail();
    }
}
