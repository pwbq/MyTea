package com.example.my.mytea.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my.httplib.BitmapRequest;
import com.example.my.httplib.HttpHelper;
import com.example.my.httplib.Request;
import com.example.my.httplib.StringRequest;
import com.example.my.mytea.R;
import com.example.my.mytea.utils.DBUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tv_title,tv_keywords,tv_time,tv_msg,tv_desc;
    private ImageView iv;
    private Long position_details;
    private String str_title=null,str_time=null,imagename;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_details);
        initView();
        Intent intent = getIntent();
        position_details = intent.getLongExtra("position", 0);
        System.out.println("id:----->"+position_details);
        imagename = intent.getStringExtra("image");
        System.out.println("imagename:" + imagename);
        loadImage(iv, "http://tnfs.tngou.net/image" + imagename);
        setContent(position_details);


    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.details_rlayout1_title);
        tv_keywords = (TextView) findViewById(R.id.details_rlayout1_keywords);
        tv_time = (TextView) findViewById(R.id.details_rlayout1_time);
        tv_desc = (TextView) findViewById(R.id.details_rlayout1_description);
        tv_msg = (TextView) findViewById(R.id.details_rlayout1_msg);
        iv = (ImageView) findViewById(R.id.details_iv);
        findViewById(R.id.details_rl_iv_collect).setOnClickListener(this);
        findViewById(R.id.details_rl_iv_share).setOnClickListener(this);
        ShareSDK.initSDK(this);

    }
    public void setDataToSQL(String tableName){
        if(str_title!=null && str_time!=null){
            DBUtils dbUtils = new DBUtils(this);
            ContentValues values = new ContentValues();
            values.put("_id", position_details);
            values.put("title", str_title);
            values.put("time", str_time);
            dbUtils.insert(tableName, values);
        }
    }
    String url = null;
    //设置详情页的内容
    public void setContent(Long position){
         url = "http://www.tngou.net/api/info/show?id="+position;
        StringRequest request = new StringRequest(url, Request.Method.GET, new Request.Callback<String>() {
            @Override
            public void onEror(Exception e) {
            }
            @Override
            public void onResonse(String response)  {
                try {
                    final  JSONObject jsonObject = new JSONObject(response);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                tv_title.setText(jsonObject.getString("title"));
                                tv_keywords.setText("关键字 : " + jsonObject.getString("keywords"));
                                long time = jsonObject.getLong("time");
                                tv_time.setText("发布时间 : " + new SimpleDateFormat("yyyy-MM-dd   hh:mm:ss").format(time));
                                str_title = jsonObject.getString("title");
                                str_time = new SimpleDateFormat("yyyy-MM-dd   hh:mm:ss").format(time);
                                System.out.println("前:str_title: " + str_title + "===str_time : " + str_time);
                                setDataToSQL("browse");
                                tv_desc.setText("   导语 : " + jsonObject.getString("description"));
                                Toast.makeText(DetailsActivity.this,"已浏览",Toast.LENGTH_SHORT).show();
                                tv_msg.setText(Html.fromHtml(jsonObject.getString("message")).toString());


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        HttpHelper.addRequest(request);
    }
    public void loadImage(final ImageView iv, final String url){

        BitmapRequest br = new BitmapRequest(url, Request.Method.GET, new Request.Callback<Bitmap>(){
            @Override
            public void onEror(Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onResonse(final Bitmap response) {
                if (iv != null && response != null ){

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            iv.setImageBitmap(response);
                        }
                    });

                }
            }
        } );
        HttpHelper.addRequest(br);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            //收藏
            case R.id.details_rl_iv_collect:
                setDataToSQL("collect");
                Toast.makeText(this,"收藏成功",Toast.LENGTH_SHORT).show();
                break;
            //分享
            case R.id.details_rl_iv_share:
                showShare();
                break;
        }
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        //oks.setTitle(getString(R.string.share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(url);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImageUrl(url);//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }
}
