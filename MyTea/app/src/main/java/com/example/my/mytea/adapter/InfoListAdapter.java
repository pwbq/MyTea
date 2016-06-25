package com.example.my.mytea.adapter;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.my.httplib.BitmapRequest;
import com.example.my.httplib.HttpHelper;
import com.example.my.httplib.Request;
import com.example.my.mytea.R;
import com.example.my.mytea.beans.Info;

import java.util.List;


/**
 * Created by wukai on 16/6/21.
 */
public class InfoListAdapter extends BaseAdapter {

	private static final String TAG = InfoListAdapter.class.getSimpleName();
	private List<Info> infoList;


	public InfoListAdapter(List<Info> infoList) {
		this.infoList = infoList;

	}


	@Override
	public int getCount() {
		return infoList == null?0:infoList.size();
	}

	@Override
	public Info getItem(int position) {
		return infoList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View cv, ViewGroup parent) {
		ViewHolder vh;
		if (cv == null){
			cv=View.inflate(parent.getContext(), R.layout.content_lv_item,null);
			vh = new ViewHolder();
			vh.iv_icon = (ImageView) cv.findViewById(R.id.item_iv);
			vh.tv_desc = (TextView) cv.findViewById(R.id.item_tv_desc);
			vh.tv_rcount =(TextView)cv.findViewById(R.id.item_tv_rc);
			vh.tv_time =(TextView)cv.findViewById(R.id.item_tv_time);
			cv.setTag(vh);

		}

		Info info = getItem(position);
		vh = (ViewHolder) cv.getTag();


		vh.tv_time.setText("时间: "+info.getTime());
		vh.tv_desc.setText(info.getDescription());
		vh.tv_rcount.setText("阅读数: "+info.getRcount());
		vh.iv_icon.setImageResource(R.mipmap.ic_launcher1);
		loadImage(vh.iv_icon,"http://tnfs.tngou.net/image"+info.getImg()+"_100x100");
		return cv;
	}


	public class ViewHolder{

		public TextView tv_desc;
		public TextView tv_time;
		public TextView tv_rcount;

		public ImageView iv_icon;

	}
	public void loadImage(final ImageView iv, final String url){

		Log.d(TAG, "loadImage() returned: url=" +url);


		iv.setTag(url);
		BitmapRequest br = new BitmapRequest(url, Request.Method.GET, new Request.Callback< Bitmap>(){
			@Override
			public void onEror(Exception e) {
				e.printStackTrace();
			}

			@Override
			public void onResonse(final Bitmap response) {
				if (iv != null && response != null && ((String)iv.getTag()).equals(url)){

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
}
