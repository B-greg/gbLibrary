package com.smartsoftasia.module.gblibrary.helper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONHelper {

	String userAgent = null;

	public JSONHelper(String userAgent) {
		this.userAgent = userAgent;
	}

	public JSONHelper() {

	}

	public JSONObject requestJSONObject(String url, String method, List<NameValuePair> params) {
		try {
			HttpResponse response = requestJSONEntity(url, method, params);
			int status = response.getStatusLine().getStatusCode();

			if (status >= 200 && status <= 299) {
				return jsonObjectFromInputStream(response.getEntity().getContent());
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();

			Log.e("JSON Parser", "Error parsing JSON Object");
			Log.e("JSON Parser", "\nURL : " + url);
			Log.e("JSON Parser", "\nparams : " + params.toString());
			Log.e("JSON Parser", "\nError " + e.toString());

			return null;
		}
	}

	public String requestJSONString(String url, String method, List<NameValuePair> params) {
		try {
			HttpResponse response = requestJSONEntity(url, method, params);
			int status = response.getStatusLine().getStatusCode();

			if (status >= 200 && status <= 299) {
				return jsonStringFromInputStream(response.getEntity().getContent());
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();

			Log.e("JSON Parser", "Error parsing JSON String");
			Log.e("JSON Parser", "\nURL : " + url);
			Log.e("JSON Parser", "\nparams : " + params.toString());
			Log.e("JSON Parser", "\nError " + e.toString());

			return null;
		}
	}

	public HttpResponse requestJSONEntity(String url, String method, List<NameValuePair> params) {
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpRequestBase httpMethod = null;

			if (method == "POST") {
				httpMethod = new HttpPost(url);
				if (params != null) {
					((HttpPost) httpMethod).setEntity(new UrlEncodedFormEntity(params));
				}
			} else if (method == "GET") {
				if (params != null) {
					String paramString = URLEncodedUtils.format(params, "UTF-8");
					url += "?" + paramString;
				}
				httpMethod = new HttpGet(url);
			} else {
				return null;
			}

			if (userAgent != null) {
				httpMethod.setHeader("USER-AGENT", userAgent);
			}

			return httpClient.execute(httpMethod);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String jsonStringFromInputStream(InputStream is) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			return sb.toString();
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
			return null;
		}
	}

	public static JSONObject jsonObjectFromInputStream(InputStream is) {
		try {
			return new JSONObject(jsonStringFromInputStream(is));
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

    public static String jsonStringFromJsonArray(JSONArray jsonArray){
        String displayMessage = "";
        try {
            for (int i = 0; i < jsonArray.length() ; i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                Iterator<String> keys = obj.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    String value = obj.getString(key);
                    displayMessage += key + " : " + value + "\n";
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            displayMessage = "Error parsing JSON String";
        }
        return  displayMessage;
    }
}
