package com.edu.gdqy.Controller.MainView.HomePage;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.gdqy.Controller.R;
import com.edu.gdqy.bean.SubscriberBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HXY on 2016/10/21.
 * mene预告ListView的Adapter
 */

public class AnnounceListAdapter extends BaseAdapter {
    private List<String> liveNames;
    private List<String> anchorNames;
    private List<String> starTimes;
    private LayoutInflater inflater;
    private Context context;

    public AnnounceListAdapter(Context context, List<String> liveName,
                               List<String> anchorName, List<String> starTime) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.liveNames = liveName;
        this.anchorNames = anchorName;
        this.starTimes = starTime;

        this.liveNames = new ArrayList<>();
        this.anchorNames = new ArrayList<>();
        this.starTimes = new ArrayList<>();
        this.anchorNames.add("huang");
        this.liveNames.add("ddada");
        this.starTimes.add("2016.12.11 11:00");
    }

    @Override
    public int getCount() {
        return liveNames.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_announce, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mLiveName.setText(liveNames.get(position));
        holder.mAnchor.setText(anchorNames.get(position));
        holder.mStart.setText(starTimes.get(position));
        holder.mFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                follow(position);
            }
        });

        return convertView;
    }

    private void follow(int position) {
        SubscriberBean bean = new SubscriberBean();
        bean.setLiveName(liveNames.get(position));
        bean.setAnchorName(anchorNames.get(position));
        bean.setStartTime(starTimes.get(position));
    }

    static class ViewHolder {
        @BindView(R.id.item_announce_liveName)
        TextView mLiveName;
        @BindView(R.id.item_announce_follow)
        Button mFollow;
        @BindView(R.id.item_announce_anchor)
        TextView mAnchor;
        @BindView(R.id.item_announce_start)
        TextView mStart;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
