package com.edu.gdqy.Controller.MainView.Subscriber;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.edu.gdqy.Controller.R;
import com.edu.gdqy.bean.SubscriberBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HXY on 2016/10/17.
 * 订阅ListView的adapter
 */

public class SubscriberListAdapter extends BaseAdapter {
    private List<SubscriberBean> subscriberBeens;
    private LayoutInflater inflater;

    public SubscriberListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        subscriberBeens =new ArrayList<>();

        for (int i = 0; i <3 ; i++) {
            SubscriberBean bean = new SubscriberBean();
            bean.setLiveName("野外钓鱼"+i);
            bean.setAnchorName("小小小雏菊"+i);
            bean.setLiveState("正在播放");
            bean.setStartTime("12月31,9:"+(10+i));
            subscriberBeens.add(bean);
        }
    }

    @Override
    public int getCount() {
        return subscriberBeens.size();
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
            convertView = inflater.inflate(R.layout.item_subscriber, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        SubscriberBean bean = subscriberBeens.get(position);
        holder.mLiveName.setText(bean.getLiveName());
        holder.mAnchorName.setText(bean.getAnchorName());
        holder.mLiveState.setText(bean.getLiveState());
        holder.mStartTime.setText(bean.getStartTime());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.Item_subscriberlist_LiveName)
        TextView mLiveName;
        @BindView(R.id.Item_subscriberlist_AnchorName)
        TextView mAnchorName;
        @BindView(R.id.Item_subscriberlist_LiveState)
        TextView mLiveState;
        @BindView(R.id.Item_subscriberlist_StartTime)
        TextView mStartTime;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public void deleteItem(int position){
        subscriberBeens.remove(position);
        this.notifyDataSetChanged();
    }

    public void stickItem(int position){
        SubscriberBean first = subscriberBeens.get(0);
        subscriberBeens.set(0,subscriberBeens.get(position));
        subscriberBeens.set(position,first);
        this.notifyDataSetChanged();
    }
}
