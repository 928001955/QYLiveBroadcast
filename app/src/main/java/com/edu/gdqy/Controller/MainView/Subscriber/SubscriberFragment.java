package com.edu.gdqy.Controller.MainView.Subscriber;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.edu.gdqy.Controller.R;
import com.edu.gdqy.Tool.Publicmethod;


/**
 * Created by HXY on 2016/10/17.
 * 订阅的界面
 */

public class SubscriberFragment extends Fragment
        implements AdapterView.OnItemLongClickListener, View.OnClickListener {
    private ListView mSubscriberList;
    private SubscriberListAdapter adapter;
    private ImageView empty;
    private PopupWindow popupWindow;
    private int screenWidth;
    private int listPosition = -1;
    private int popupWidth, popupHeight;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subscriber, null);
        mSubscriberList = (ListView) view.findViewById(R.id.subscriber_list);
        empty = (ImageView) view.findViewById(android.R.id.empty);
        screenWidth = Publicmethod.getScreenHeightWidth(0,getContext());     // 屏幕宽度（像素）
        init();
        return view;
    }

    private void init() {
        mSubscriberList.setEmptyView(empty);
        adapter = new SubscriberListAdapter(getContext());
        mSubscriberList.setAdapter(adapter);
        mSubscriberList.setOnItemLongClickListener(this);

        WindowManager manager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display defaultDisplay = manager.getDefaultDisplay();
        Point size = new Point();
        defaultDisplay.getSize(size);
        int screenWidth = size.x;
        int screenHeight = size.y;
        popupWidth = screenWidth / 3;
        popupHeight = screenHeight  / 10;

        creatPopupwindow();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        listPosition = position;
        popupWindow.showAsDropDown(view, screenWidth / 6, -30);
        return true;
    }

    private void creatPopupwindow() {
        View popupView = getActivity().getLayoutInflater().
                inflate(R.layout.layout_subscriberhandle, null);
        popupWindow = new PopupWindow(
                popupView, popupWidth
                , popupHeight, true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.ogw));
        popupWindow.setAnimationStyle(R.style.mune_herald);

        TextView delete = (TextView) popupView.findViewById(R.id.subscriberHandle_delete);
        TextView stake = (TextView) popupView.findViewById(R.id.subscriberHandle_stick);
        delete.setOnClickListener(this);
        stake.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.subscriberHandle_delete:
                adapter.deleteItem(listPosition);
                popupWindow.dismiss();
                break;
            case R.id.subscriberHandle_stick:
                adapter.stickItem(listPosition);
                popupWindow.dismiss();
                break;
        }
    }
}
