package com.salatech.taawuncarinsurance;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {

    private WebView webData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webData = findViewById(R.id.web_view_insurance_plans);
        WebSettings webset = webData.getSettings();
        webset.setUseWideViewPort(false);


        webset.setJavaScriptEnabled(true);


        webData.addJavascriptInterface(new WebAppInterface(this), "Android");

        webData.setWebChromeClient(new WebChromeClient());

        Bundle extra = getIntent().getExtras();
        String url = extra.getString("HtmlPage");
        webData.loadUrl(url);
    }


    public class WebAppInterface {
        Context mContext;

        WebAppInterface(Context context) {
            mContext = context;
        }

        @JavascriptInterface
        public void openDesiredOptionScreen() {

            Intent intent = new Intent(mContext, Desired_Option_Screen.class);
            startActivity(intent);
        }
    }
}
