package com.example.my.mytea.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.example.my.mytea.R;

public class FreeBackActivity extends AppCompatActivity {
    private EditText editText_title, editText_content;
    private NotificationManager notificationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_free_back);
        editText_content = (EditText) findViewById(R.id.freeback_ed_content);
        editText_title = (EditText) findViewById(R.id.freeback_ed_title);
        findViewById(R.id.freeback_rl_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText_title.getText().length()==0 || editText_content.getText().length()==0){
                    Toast.makeText(FreeBackActivity.this,"标题与内容不能为空",Toast.LENGTH_SHORT).show();
                }else{
                    setnotify(editText_title.getText().toString(),editText_content.getText().toString());
                    editText_content.getText().clear();
                    editText_title.getText().clear();
                    Toast.makeText(FreeBackActivity.this,"提交成功",Toast.LENGTH_SHORT).show();
                    finish();

                }
            }


        });
        findViewById(R.id.freeback_rl_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FreeBackActivity.this,SettingActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setnotify(String title,String content) {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder_intent = new NotificationCompat.Builder(this);
        builder_intent.setSmallIcon(R.mipmap.ic_launcher);
        builder_intent.setContentTitle(title);
        builder_intent.setContentText("点击查看");
        builder_intent.setTicker("有新的意见反馈,请查看");
        builder_intent.setAutoCancel(true);
        Intent intent = new Intent(this,CheckFreebackMsgActivity.class);
        intent.putExtra("content",content);
        intent.putExtra("title",title);
        PendingIntent pending_intent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);
        builder_intent.setContentIntent(pending_intent);
        Notification notification_intent = builder_intent.build();
        notificationManager.notify(4,notification_intent);
    }
}
