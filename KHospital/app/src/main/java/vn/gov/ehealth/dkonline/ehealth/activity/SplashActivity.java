package vn.gov.ehealth.dkonline.ehealth.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.Menu;

import vn.gov.ehealth.dkonline.ehealth.R;

/**
 * Created by luongdolong on 9/19/2017.
 */

public class SplashActivity extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashActivity.this, WebActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, 3000);
    }
}
