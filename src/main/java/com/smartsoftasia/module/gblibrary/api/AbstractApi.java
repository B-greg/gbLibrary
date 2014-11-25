package com.smartsoftasia.module.gblibrary.api;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.smartsoftasia.module.gblibrary.helper.StringHelper;
import com.smartsoftasia.module.gblibrary.helper.Validator;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Locale;

/**
 * Created by gregoire on 8/29/14.
 */
public class AbstractApi implements IHttpMethodes {

    public interface IAbstractApi{
        public interface IIndex{
            public void onSuccess(int statusCode, JSONObject content);
            public void onFail(int statusCode);
        }
        public interface IShow{
            public void onSuccess(int statusCode ,JSONObject content);
            public void onFail(int statusCode);
        }
        public interface ICreate{
            public void onSuccess(int statusCode ,JSONObject content);
            public void onFail(int statusCode);
        }
        public interface IUpdate{
            public void onSuccess(int statusCode, JSONObject content);
            public void onFail(int statusCode);
        }
        public interface IDelete{
            public void onSuccess(int statusCode, JSONObject content);
            public void onFail(int statusCode);
        }
    }


    private final static String TAG = "ApiAbstract";
    public final static int INDEX = 1;
    public final static int SHOW = 2;
    public final static int CREATE = 3;
    public final static int UPDATE = 4;
    public final static int DELETE = 5;
    public final static int LASTPAGE = 0;
    public final static int UNIQUEPAGE = -1;

    public static AsyncHttpClient client = new AsyncHttpClient();
    protected JsonHttpResponse jsonHttpResponse;
    protected AsyncHttpResponse asyncHttpResponse;
    public Boolean isExecute;
    protected int methode;

    protected IAbstractApi.IIndex indexListener;
    protected IAbstractApi.ICreate createListener;
    protected IAbstractApi.IDelete deleteListener;
    protected IAbstractApi.IShow showListener;
    protected IAbstractApi.IUpdate updateListener;


    public AbstractApi(){
        this.isExecute = false;
        this.methode = 0;
        this.jsonHttpResponse = new JsonHttpResponse();
        this.asyncHttpResponse = new AsyncHttpResponse();
        AbstractApi.client.setMaxRetriesAndTimeout(3,20000);
        AbstractApi.client.setTimeout(60000);
    };

    public void postExecute(int statusCode, String content){};
    public void postExecute(int statusCode, JSONObject content){};
    public void postExecute(int statusCode, JSONArray content){};

    @Override
    public void index(){
        isExecute = true;
        methode = INDEX;
    }
    @Override
    public void show(){
        isExecute = true;
        methode = SHOW;
    }
    @Override
    public void create(){
        isExecute = true;
        methode = CREATE;
    }
    @Override
    public void update(){
        isExecute = true;
        methode = UPDATE;
    }
    @Override
    public void delete(){
        isExecute = true;
        methode = DELETE;
    }

    public void setToken(RequestParams params, String token){
        if(null != token){
        }
    }

    public static int calculatePage(Boolean isFirst, int page){
        final int pageinate;
        if(isFirst && page == AbstractApi.LASTPAGE)pageinate = AbstractApi.UNIQUEPAGE;
        else pageinate = page;
        return pageinate;
    }

    public int getNextPage(JSONObject content){
        return 0;
    }

    public Boolean isEndOfList(JSONObject content){
        return false;
    }

    public void sendBroadcast(Context context, String filter){
   //     Intent intent = new Intent(filter);
   //     LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static RequestParams newRequestParams(String token){
        RequestParams params = new RequestParams();
        params.add("app_token", Validator.validate(token));
        params.add("locale", Validator.validate(Locale.getDefault().getLanguage()));
        return params;
    }

    public class JsonHttpResponse extends JsonHttpResponseHandler {

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable e) {
            super.onFailure(statusCode, headers, responseBody, e);
            JSONObject content = null;
            isExecute = false;
            postExecute(statusCode, content);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable,
                              JSONArray errorResponse) {
            super.onFailure(statusCode, headers, throwable, errorResponse);
            JSONObject content = null;
            isExecute = false;
            postExecute(statusCode, content);

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable,
                              JSONObject errorResponse) {
            super.onFailure(statusCode, headers, throwable, errorResponse);
            JSONObject content = null;
            isExecute = false;
            postExecute(statusCode, content);
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
            super.onSuccess(statusCode, headers, response);
            isExecute = false;
            postExecute(statusCode, response);
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            super.onSuccess(statusCode, headers, response);
            isExecute = false;
            postExecute(statusCode, response);
        }


        @Override
        public void onSuccess(int statusCode, Header[] headers, String responseBody) {
            super.onSuccess(statusCode, headers, responseBody);
            isExecute = false;
            postExecute(statusCode, responseBody);
        }
    }

    public class AsyncHttpResponse extends AsyncHttpResponseHandler {



        @Override
        public void onCancel() {
            super.onCancel();
        }


        @Override
        public void onFinish() {
            super.onFinish();
        }


        @Override
        public void onStart() {
            super.onStart();
        }


        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] response) {
            String content = StringHelper.byteArrayToString(response);
            isExecute = false;
            postExecute(statusCode, content);
        }


        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            String content = StringHelper.byteArrayToString(responseBody);
            isExecute = false;
            postExecute(statusCode, content);
        }



    }
}
