package com.smartsoftasia.module.gblibrary.api;

import org.json.JSONObject;

/**
 * Created by gregoire on 9/22/14.
 */
public interface IHttpModel {
    public void postToBackend();
    public void getFromBackend(String id);
    public void getAllFromBackend();

}
