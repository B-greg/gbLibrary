package com.smartsoftasia.module.gblibrary.database;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.j256.ormlite.field.DatabaseField;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

/**
 * Created by gregoire on 9/22/14.
 */
public class DatabaseModel<T> {

    public final static String ID = "id";

    @DatabaseField(id = true, columnName = ID)
    @JsonProperty(ID)
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


    public static <T> T toObject(JSONObject json, Class<T> type) throws IOException, JSONException {
        T item;
        ObjectMapper mapper = new ObjectMapper();
        item = mapper.readValue(json.toString(), type);
        return item;
    }

    public static <E> List<E> toObject(JSONArray json, Class<E> type) throws IOException, JSONException {
        List<E> items;
        ObjectMapper mapper = new ObjectMapper();
        items = mapper.readValue(json.toString(),  TypeFactory.defaultInstance().constructCollectionType(List.class,
                type));
        return items;
    }

}
