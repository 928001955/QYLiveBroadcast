package com.edu.gdqy.Controller.MainView.HomePage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.edu.gdqy.Controller.AnchorAudienceView.Live.LiveActivity;
import com.edu.gdqy.Controller.AnchorAudienceView.VideoOnDemandActivity;
import com.edu.gdqy.Controller.R;
import com.edu.gdqy.Tool.PublicVariable;
import com.edu.gdqy.Tool.Publicmethod;
import com.edu.gdqy.Tool.VedioGridAdapter;
import com.edu.gdqy.bean.VedioBean;
import com.jude.rollviewpager.RollPagerView;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * Created by HXY on 2016/10/7.
 * 主页界面
 */

public class HomePageFragment extends Fragment {
    @BindView(R.id.homepage_carousel)
    RollPagerView mCarousel;
    @BindView(R.id.homepage_hotGrid)
    GridView mHotGrid;
    @BindView(R.id.homepage_programming)
    GridView mProgrammingGrid;
    @BindView(R.id.homePage_layout)
    LinearLayout mLayout;

    private List<VedioBean> hotBeans;
    private List<VedioBean> programmeBeans;
    private BaseAdapter hotAdapter, programmingAdapter;

    private int popupWidth, popupHeight;
    private int xOff;
    private PopupWindow popupWindow;
    private BroadcastReceiver receiver;
    private boolean isShow=false;         //是否popupwindow显示
    private boolean first = true; //是不是第一次进入该页面


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage, null);
        ButterKnife.bind(this, view);

        int screemWidth = Publicmethod.getScreenHeightWidth(1, getContext());
        int screemHeight = Publicmethod.getScreenHeightWidth(2, getContext());
        popupWidth = screemWidth * 3 / 4;
        popupHeight = screemHeight / 2;
        xOff = screemWidth - popupWidth;

        init();
        registerBroadrecevicer();
        return view;
    }

    private void init() {
        //RollPagerView初始化
        mCarousel.setPlayDelay(2000);
        mCarousel.setAnimationDurtion(500);
        mCarousel.setAdapter(new CarouselAdapter());

        initPopupWindow();

        String[] url = new String[]{"http://img.mukewang.com/53bf89100001684e06000338-240-135.jpg"
                , "http://img.mukewang.com/57075af80001574b06000338-240-135.jpg"
                , "http://img.mukewang.com/541698a40001d1c306000338-240-135.jpg"
                , "http://img.mukewang.com/57075b1a000178ad06000338-240-135.jpg"};
        String[] title = new String[]{"安卓常用组件", "ListView的使用", "安卓常犯错误", "安卓总结"};
        hotBeans = new ArrayList<>();
        for (int i = 0; i < url.length; i++) {
            VedioBean bean = new VedioBean();
            bean.setImageUrl(url[i]);
            bean.setVedioName(title[i]);
            hotBeans.add(bean);
        }
        hotAdapter = new VedioGridAdapter(getActivity(), getContext(), hotBeans);
        mHotGrid.setAdapter(hotAdapter);

        programmeBeans = new ArrayList<>();
        String[] url1 = new String[]{"http://img.mukewang.com/545c862c0001e13e06000338-240-135.jpg"
                , "http://img.mukewang.com/55d6ca740001f2ad06000338-240-135.jpg"
                , "http://img.mukewang.com/5707598e0001ec7c06000338-240-135.jpg"
                , "http://img.mukewang.com/55666c0a0001d6b506000338-240-135.jpg"};
        for (int i = 0; i < url1.length; i++) {
            VedioBean bean = new VedioBean();
            bean.setImageUrl(url1[i]);
            bean.setVedioName(title[i]);
            programmeBeans.add(bean);
        }
        programmingAdapter = new VedioGridAdapter(getActivity(), getContext(), programmeBeans);
        mProgrammingGrid.setAdapter(programmingAdapter);
    }

    private void registerBroadrecevicer() {
        receiver = new IntenterBoradCastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        getActivity().registerReceiver(receiver, filter);
    }

    @OnItemClick({R.id.homepage_hotGrid, R.id.homepage_programming})
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        Intent intent;
        switch (parent.getId()) {
            case R.id.homepage_hotGrid:
                intent = new Intent(getActivity(), VideoOnDemandActivity.class);
                startActivity(intent);
                break;
            case R.id.homepage_programming:
                intent = new Intent(getActivity(), LiveActivity.class);
                intent.putExtra("live", PublicVariable.WATCH);
                startActivity(intent);
                break;
        }
    }


    @OnClick({R.id.homePage_herald, R.id.homePage_QRCode,
            R.id.homePage_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.homePage_herald:
                if (!isShow) {
                    popupWindow.showAsDropDown(mLayout, xOff, 0);
                    isShow=true;
                }else {
                    popupWindow.dismiss();
                    isShow=false;
                }
                break;
            case R.id.homePage_QRCode:
                startActivityForResult(new Intent(getActivity(), CaptureActivity.class), 0);
                break;
            case R.id.homePage_search:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void initPopupWindow() {
        View popupView = getActivity().getLayoutInflater().inflate(R.layout.layout_heraldlist, null);
        popupWindow = new PopupWindow(popupView, popupWidth, popupHeight);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.announced_bg));
        popupWindow.setAnimationStyle(R.style.mune_herald);
        ListView announceList = (ListView) popupView.findViewById(R.id.LY_herald_list);
        announceList.setAdapter(new AnnounceListAdapter(getContext(), null, null, null));
    }

    //扫描二维码返回
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            Bundle bundle = data.getExtras();
            String result = bundle.getString("result");
            Toast.makeText(getContext(), "扫描结果:" + result, Toast.LENGTH_LONG).show();
        }
    }

    //从无网变成有网监听，刷新主页
    private class IntenterBoradCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Publicmethod.checkNetwork(context)) {
                if (first) {
                    first = false;
                    return;
                }
                hotAdapter.notifyDataSetChanged();
                programmingAdapter.notifyDataSetChanged();
                Toast.makeText(context, "网络已连接，帮您更新了首页", Toast.LENGTH_SHORT).show();
            }
            ;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(receiver);
    }
}
