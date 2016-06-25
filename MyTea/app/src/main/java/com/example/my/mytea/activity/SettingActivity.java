package com.example.my.mytea.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import com.example.my.mytea.R;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setting);
        initView();

    }

    private void initView() {
        findViewById(R.id.setting_collect).setOnClickListener(this);
        findViewById(R.id.setting_browse).setOnClickListener(this);
        findViewById(R.id.setting_feedback).setOnClickListener(this);
        findViewById(R.id.setting_versions).setOnClickListener(this);
        findViewById(R.id.setting_rl_iv).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent  =  new Intent();
        switch (v.getId()){
            //收藏夹
            case R.id.setting_collect:
                intent.setClass(this,CollectActivity.class);
                break;
            //浏览记录
            case R.id.setting_browse:
                intent.setClass(this,BrowseActivity.class);
                break;
            case R.id.setting_versions:
                intent.setClass(this,VersionActivity.class);
                break;
            case R.id.setting_feedback:
                intent.setClass(this,FreeBackActivity.class);
                break;
            case R.id.setting_rl_iv:
                intent.setClass(this,HomeActivity.class);
                break;
        }
        startActivity(intent);
        finish();
    }
}
