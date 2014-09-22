package com.smartsoftasia.module.gblibrary.database;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by gregoire on 9/22/14.
 */
public class DatabaseModel {

    public final static String ID = "id";

    @DatabaseField(id = true, columnName = ID)
    public String id;

    public DatabaseModel() {
    }

    public DatabaseModel(String id) {
        this.id = id;
    }

    public int getId() {
        return Integer.valueOf(this.id);
    }

    public void setId(int id) {
        this.id = String.valueOf(id);
    }


}
