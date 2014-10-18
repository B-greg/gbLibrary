package com.smartsoftasia.module.gblibrary.database;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
    public final static String ENABLE = "enable";

    @DatabaseField(id = true, columnName = ID)
    @JsonProperty(ID)
    public int id;
    @DatabaseField(columnName = ENABLE)
    public Boolean enable;

    public DatabaseModel() {
    }

    public DatabaseModel(int id) {
        this.id = id;
    }

    public String getId() {
        return String.valueOf(this.id);
    }

    public void setId(String id) {
        this.id = Integer.valueOf(id);
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
