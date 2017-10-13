package vn.gov.ehealth.dkonline.ehealth.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import vn.gov.ehealth.dkonline.ehealth.R;
import vn.gov.ehealth.dkonline.ehealth.common.CommonUtils;
import vn.gov.ehealth.dkonline.ehealth.common.Constant;

/**
 * Created by luongdolong on 9/19/2017.
 */

public class WebActivity extends Activity {
    private Button btWebBack;
    private WebView wvBrowser;
    private ProgressDialog progDailog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        btWebBack = (Button) findViewById(R.id.btWebBack);
        wvBrowser = (WebView) findViewById(R.id.wvBrowser);
        String url = getIntent().getStringExtra(Constant.EXTRA_URL);
        if (CommonUtils.isEmpty(url)) {
            url = getString(R.string.url_main);
        }
        btWebBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wvBrowser.canGoBack()) {
                    wvBrowser.goBack();
                }
            }
        });

        progDailog = ProgressDialog.show(this, "Loading","Please wait...", true);
        progDailog.setCancelable(false);

        wvBrowser.getSettings().setJavaScriptEnabled(true);
        wvBrowser.getSettings().setLoadWithOverviewMode(true);
        wvBrowser.getSettings().setUseWideViewPort(true);

        wvBrowser.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                progDailog.show();
                return super.shouldOverrideUrlLoading(view, request);
            }
            @Override
            public void onPageFinished(WebView view, final String url) {
                progDailog.dismiss();
            }
        });
        wvBrowser.loadUrl(url);
    }
}
