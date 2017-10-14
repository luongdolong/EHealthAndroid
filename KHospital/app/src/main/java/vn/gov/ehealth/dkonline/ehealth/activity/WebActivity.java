package vn.gov.ehealth.dkonline.ehealth.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
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
    private Button btReload;
    private ProgressDialog progDailog;
    private String mainUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        btWebBack = (Button) findViewById(R.id.btWebBack);
        wvBrowser = (WebView) findViewById(R.id.wvBrowser);
        btReload = (Button) findViewById(R.id.btReload);
        mainUrl = getIntent().getStringExtra(Constant.EXTRA_URL);
        if (CommonUtils.isEmpty(mainUrl)) {
            mainUrl = getString(R.string.url_main);
        }
        btWebBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wvBrowser.canGoBack()) {
                    wvBrowser.goBack();
                }
            }
        });
        btReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btReload.setVisibility(View.GONE);
                //wvBrowser.setVisibility(View.VISIBLE);
                wvBrowser.reload();
            }
        });

        progDailog = ProgressDialog.show(this, "Đang tải","Vui lòng chờ đợi...", true);
        progDailog.setCancelable(false);

        wvBrowser.getSettings().setJavaScriptEnabled(true);
        wvBrowser.getSettings().setLoadWithOverviewMode(true);
        wvBrowser.getSettings().setUseWideViewPort(true);

        wvBrowser.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (!progDailog.isShowing()) {
                    progDailog.show();
                }
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (!progDailog.isShowing()) {
                    progDailog.show();
                }
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageFinished(WebView view, final String url) {
                wvBrowser.setVisibility(View.VISIBLE);
                progDailog.dismiss();
                if (!isPageError(view.getTitle())) {
                    btReload.setVisibility(View.GONE);
                } else {
                    wvBrowser.setVisibility(View.INVISIBLE);
                    showMsg(getString(R.string.error001));
                    btReload.setVisibility(View.VISIBLE);
                }
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                wvBrowser.setVisibility(View.INVISIBLE);
                progDailog.dismiss();
                showMsg(getString(R.string.error001));
                btReload.setVisibility(View.VISIBLE);
                super.onReceivedError(view, request, error);
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                wvBrowser.setVisibility(View.INVISIBLE);
                progDailog.dismiss();
                showMsg(getString(R.string.error001));
                btReload.setVisibility(View.VISIBLE);
                super.onReceivedHttpError(view, request, errorResponse);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                wvBrowser.setVisibility(View.INVISIBLE);
                progDailog.dismiss();
                showMsg(getString(R.string.error001));
                btReload.setVisibility(View.VISIBLE);
                super.onReceivedSslError(view, handler, error);
            }
        });
        wvBrowser.loadUrl(mainUrl);
    }

    private void showMsg(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msg);
        builder.setTitle("Lỗi");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private boolean isPageError(String title) {
        if (title.contains(getString(R.string.domain))) {
            return false;
        }
        if (title.contains("Identification Management System")) {
            return false;
        }
        return true;
    }
}
