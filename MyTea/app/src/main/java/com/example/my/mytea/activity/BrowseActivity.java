package com.example.my.mytea.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.my.mytea.R;
import com.example.my.mytea.utils.DBUtils;

import java.util.ArrayList;
import java.util.List;

public class BrowseActivity extends AppCompatActivity  implements View.OnClickListener{
    private ListView listView;
    private MyBrowseAdapter adapter;
    private DBUtils dbUtils;
    private Cursor cursor;
    private List<Integer> list_id = new ArrayList<Integer>();
    private List<String>  list_title = new ArrayList<String>();
    private List<String>  list_time = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_browse);
        listView = (ListView) findViewById(R.id.browse_listview);
        findViewById(R.id.browse_rl_iv_back).setOnClickListener(this);
        findViewById(R.id.browse_rl_iv_delete).setOnClickListener(this);
        getSQLData(); //获取数据库数据
        adapter = new MyBrowseAdapter();
        listView.setAdapter(adapter);
        listviewListener();
    }
    private void listviewListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(BrowseActivity.this, DetailsActivity.class);
                intent.putExtra("position", Long.valueOf(list_id.get(position)));
                startActivity(intent);
            }
        });
        //长按删除
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showPopupMenu(view, position);
                return false;
            }
        });
    }
    public void getSQLData(){
        dbUtils = new DBUtils(this);
        cursor = dbUtils.queryAll("browse");
        while(cursor.moveToNext()){
            int index = cursor.getColumnIndex("_id");
            int position = cursor.getInt(index);
            index = cursor.getColumnIndex("title");
            String title = cursor.getString(index);
            index = cursor.getColumnIndex("time");
            String time = cursor.getString(index);
            list_id.add(position);
            list_title.add(title);
            list_time.add(time);
        }
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.browse_rl_iv_back:
                Intent intent = new Intent(BrowseActivity.this, SettingActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.browse_rl_iv_delete:
                list_time.clear();
                list_title.clear();
                adapter.notifyDataSetChanged();
                DBUtils dbUtils = new DBUtils(BrowseActivity.this);
                dbUtils.delete("browse", null, null);
        }
    }

    class MyBrowseAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list_title.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(BrowseActivity.this).inflate(R.layout.collect_lv_content, null);
                holder = new ViewHolder();
                holder.tv_title = (TextView) convertView.findViewById(R.id.collect_lv_content_title);
                holder.tv_time = (TextView) convertView.findViewById(R.id.collect_lv_content_time);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv_title.setText(list_title.get(position));
            holder.tv_time.setText("发布时间 :" + list_time.get(position));
            return convertView;
        }

        public class  ViewHolder{
            private TextView tv_title,tv_time;
            private ImageView iv;
        }

    }
    private void showPopupMenu(View view, final int position) {
        final PopupMenu popup = new PopupMenu(this, view);
        popup.getMenuInflater().inflate(R.menu.main, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.remove:
                        list_title.remove(position);
                        list_time.remove(position);
                        DBUtils dbUtils = new DBUtils(BrowseActivity.this);
                        int id =list_id.get(position);
                        dbUtils.delete("browse" ,"_id=?",new String[]{id+""});
                        adapter.notifyDataSetChanged();
                        break;
                }
                return false;
            }
        });
        popup.show();
    }

}
