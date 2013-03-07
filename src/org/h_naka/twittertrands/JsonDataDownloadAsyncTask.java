package org.h_naka.twittertrands;

import java.io.InputStream;
import java.lang.IllegalArgumentException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.IllegalStateException;
import java.net.URLDecoder;

import android.os.AsyncTask;
import org.json.JSONArray;
import org.json.JSONException;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

public class JsonDataDownloadAsyncTask extends
		AsyncTask<String, Void, JSONArray> {

    private OnJsonDataDownloadListener m_listener = null;

    @Override
    protected JSONArray doInBackground(String... param) {
        return getJsonDataFromUrl(param[0]);
    }

    @Override
    protected void onPostExecute(JSONArray jArray) {
        if (m_listener != null) {
            m_listener.onFinishJsonDataDownload(jArray);
        }
    }

    public void setOnJsonDataDownloadListener(OnJsonDataDownloadListener listener) {
        m_listener = listener;
    }

    private JSONArray getJsonDataFromUrl(String url) {
        JSONArray m_jArray = null;

        InputStream is = null;
        String result = null;
        boolean isSuccess = true;
        
        // http get
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(url);
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
        } catch(IllegalArgumentException e) {
            e.printStackTrace();
            isSuccess = false;
        } catch (IOException e) {
            e.printStackTrace();
            isSuccess = false;
        } catch(IllegalStateException e) {
            e.printStackTrace();
            isSuccess = false;
        } catch(Exception e) {
            e.printStackTrace();
            isSuccess = false;
        }

        if (isSuccess == false) {
            return null;
        }
        
        // convert response to string
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
            StringBuilder builder = new StringBuilder();
            String line = null;
            while((line = reader.readLine()) != null) {
                builder.append(line + "\n");
            }
            result = builder.toString();
        } catch(IOException e) {
            e.printStackTrace();
            isSuccess = false;
        } catch(Exception e) {
            e.printStackTrace();
            isSuccess = false;
        } finally {
            try {
                is.close();
            } catch(IOException e) {
                e.printStackTrace();
                isSuccess = false;
            }
        }

        if (isSuccess == false) {
            return null;
        }
        
        // try parse the string to a JSON object
        try {
            m_jArray = new JSONArray(URLDecoder.decode(result,"utf-8"));
        } catch(JSONException e) {
            e.printStackTrace();
            isSuccess = false;
        } catch(UnsupportedEncodingException e) {
            e.printStackTrace();
            isSuccess = false;
        }

        if (isSuccess == true) {
            return m_jArray;
        } else {
            return null;
        }
    }
}

interface OnJsonDataDownloadListener {
    public void onFinishJsonDataDownload(JSONArray jArray);
}