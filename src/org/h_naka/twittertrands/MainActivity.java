package org.h_naka.twittertrands;

import android.os.Bundle;
import android.app.Activity;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    private Button m_getButton;
    private TextView m_textView;
    private JsonDataProcess m_jsonDataProcess;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

        initInstance();
        setInterface();
    }

    private void initInstance() {
        m_getButton = (Button)findViewById(R.id.getButton);
        m_textView  = (TextView)findViewById(R.id.textView);
        m_jsonDataProcess = new JsonDataProcess(this);
    }

    private void setInterface() {
        m_getButton.setOnClickListener(m_jsonDataProcess);
    }

    public TextView getTextView() {
        return m_textView;
    }

}
