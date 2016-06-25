package com.example.my.mytea.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.my.mytea.R;

public class CheckFreebackMsgActivity extends AppCompatActivity {
    private TextView tv_title,tv_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_check_freeback_msg);
        Intent intent = getIntent();
        intent.getStringExtra("content");
        tv_title = (TextView) findViewById(R.id.check_freeback_ed_title);
        tv_content = (TextView) findViewById(R.id.check_freeback_ed_content);
        tv_title.setText(intent.getStringExtra("title"));
        tv_content.setText(intent.getStringExtra("content"));
        findViewById(R.id.check_freeback_rl_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckFreebackMsgActivity.this, SettingActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
