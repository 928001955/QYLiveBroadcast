package com.edu.gdqy.Tool;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.gdqy.Controller.R;
import com.edu.gdqy.Model.Imageload;
import com.edu.gdqy.bean.VedioBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HXY on 2016/10/14.
 * 首页 热门和编程GridView的adapter,所有需要用到显示视频的GridView的adapter
 */

public class VedioGridAdapter extends BaseAdapter {
    private List<VedioBean> vedioBeanList;
    private LayoutInflater inflater;
    private Activity activity;
    private Imageload imageload;

    public VedioGridAdapter(Activity activity,Context context, List<VedioBean> list) {
        this.activity=activity;
        this.vedioBeanList = list;
        imageload = new Imageload();
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return vedioBeanList.size();
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_vedio, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mThumbnail.setImageResource(R.drawable.default_image);
        VedioBean vedioBean = vedioBeanList.get(position);
        imageload.downloadImage(activity,vedioBean.getImageUrl(),holder.mThumbnail);
        holder.mHotTitle.setText(vedioBean.getVedioName());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.item_vedio_Thumbnail)
        ImageView mThumbnail;
        @BindView(R.id.item_vedio_Title)
        TextView mHotTitle;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
