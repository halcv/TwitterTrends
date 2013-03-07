package org.h_naka.twittertrands;

import android.view.View;
import android.view.View.OnClickListener;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class JsonDataProcess
    implements OnClickListener, OnJsonDataDownloadListener {

    private MainActivity m_activity;

    public JsonDataProcess(MainActivity activity) {
        m_activity = activity;
    }
    
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
        case R.id.getButton:
            getJsonData();
            break;
        }
	}

    @Override
    public void onFinishJsonDataDownload(JSONArray jArray) {
        if (jArray == null) {
            dispTwitterTrendInformation(m_activity.getResources().getString(R.string.getError));
            return;
        }
        
        JSONObject json;
        try {
            StringBuilder builder = new StringBuilder();
            json = jArray.getJSONObject(0);
            JSONArray trends = json.getJSONArray("trends");
            for (int i = 0;i < trends.length();i++) {
                JSONObject e = trends.getJSONObject(i);
                builder.append(e.getString("name"));
                builder.append("\n");
            }
            dispTwitterTrendInformation(builder.toString());
        } catch(JSONException e) {
            e.printStackTrace();
            dispTwitterTrendInformation(m_activity.getResources().getString(R.string.getError));
        }
    }
    
    private void getJsonData() {
        m_activity.getTextView().setText("");
        JsonDataDownloadAsyncTask task = new JsonDataDownloadAsyncTask();
        task.setOnJsonDataDownloadListener(this);
        task.execute(m_activity.getResources().getString(R.string.url));
    }

    private void dispTwitterTrendInformation(String out) {
        m_activity.getTextView().setText(out);
    }
}
