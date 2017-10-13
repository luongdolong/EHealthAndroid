package vn.gov.ehealth.dkonline.ehealth.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import vn.gov.ehealth.dkonline.ehealth.R;
import vn.gov.ehealth.dkonline.ehealth.common.Constant;

/**
 * Created by luongdolong on 9/19/2017.
 */

public class MenuActivity extends Activity {
    private Button btMain;
    private Button btReg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        btMain = (Button) findViewById(R.id.btMain);
        btReg = (Button) findViewById(R.id.btReg);

        btMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, WebActivity.class);
                intent.putExtra(Constant.EXTRA_URL, getString(R.string.url_main));
                startActivity(intent);
            }
        });
        btReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, WebActivity.class);
                intent.putExtra(Constant.EXTRA_URL, getString(R.string.url_reg));
                startActivity(intent);
            }
        });
    }
}
