package com.edu.gdqy.Controller.MainView.My;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.edu.gdqy.Controller.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HXY on 2016/10/14.
 * 更多设置Listview的Adapter
 */

public class SetListAdapter extends BaseAdapter {
    private List<String> names;
    private int[] icon=new int[7];
    private LayoutInflater inflater;

    public SetListAdapter(Context context) {
        icon[0]=R.drawable.dateset;
        icon[1]=R.drawable.blacklist;
        icon[2]=R.drawable.mute;
        icon[3]=R.drawable.mute;
        icon[4]=R.drawable.share;
        icon[5]=R.drawable.aboutsoft;
        icon[6]=R.drawable.exit;

        names = new ArrayList();
        names.add("资料修改");
        names.add("黑名单");
        names.add("不接受陌生人消息");
        names.add("不接收消息提示");
        names.add("分享软件");
        names.add("关于本软件");
        names.add("退出登录");
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return names.size();
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
        if (convertView==null){
            convertView= inflater.inflate(R.layout.item_moreset, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mIcon.setImageResource(icon[position]);
        String name =names.get(position);
        holder.mName.setText(name);
        if (name.equals("不接受陌生人消息")|| name.equals("不接收消息提示")){
            holder.mNext.setVisibility(View.GONE);
            holder.mSwitch.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.item_Setlist_Icon)
        ImageView mIcon;
        @BindView(R.id.item_Setlist_Name)
        TextView mName;
        @BindView(R.id.item_Setlist_Next)
        TextView mNext;
        @BindView(R.id.item_Setlist_Switch)
        Switch mSwitch;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
