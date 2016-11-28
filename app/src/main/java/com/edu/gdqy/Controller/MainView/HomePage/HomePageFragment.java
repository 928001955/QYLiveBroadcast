package com.edu.gdqy.Controller.MainView.HomePage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
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
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.edu.gdqy.Controller.AnchorAudienceView.Live.LiveActivity;
import com.edu.gdqy.Controller.AnchorAudienceView.VideoOnDemandActivity;
import com.edu.gdqy.Controller.R;
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

    private int popupWidth, popupHeight;
    private int xOff;
    private PopupWindow popupWindow;
    private boolean isShowPopup = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage, null);
        ButterKnife.bind(this, view);

        WindowManager manager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display defaultDisplay = manager.getDefaultDisplay();
        Point size = new Point();
        defaultDisplay.getSize(size);
        popupWidth = size.x * 3 / 4;
        popupHeight = size.y / 2;
        xOff = size.x - popupWidth;

        init();
        return view;
    }

    private void init() {
        //设置播放时间间隔
        mCarousel.setPlayDelay(2000);
        //设置透明度
        mCarousel.setAnimationDurtion(500);
        //设置适配器
        mCarousel.setAdapter(new CarouselAdapter());

        initPopupWindow();

        String[] url = new String[]{"http://img.mukewang.com/53bf89100001684e06000338-240-135.jpg"
                ,"http://img.mukewang.com/57075af80001574b06000338-240-135.jpg"
                ,"http://img.mukewang.com/541698a40001d1c306000338-240-135.jpg"
                ,"http://img.mukewang.com/57075b1a000178ad06000338-240-135.jpg"};
        String[] title= new String[]{"我是帅B","超级大帅逼","我是老腊肉","熏得乌漆麻黑的"};
        hotBeans = new ArrayList<>();
        for (int i = 0; i < url.length ; i++) {
            VedioBean bean = new VedioBean();
            bean.setImageUrl(url[i]);
            bean.setVedioName(title[i]);
            hotBeans.add(bean);
        }
        VedioGridAdapter adapter1 = new VedioGridAdapter(getActivity(),getContext(), hotBeans);
        mHotGrid.setAdapter(adapter1);

        programmeBeans = new ArrayList<>();
        String[] url1 = new String[]{"http://img.mukewang.com/545c862c0001e13e06000338-240-135.jpg"
                ,"http://img.mukewang.com/55d6ca740001f2ad06000338-240-135.jpg"
                ,"http://img.mukewang.com/5707598e0001ec7c06000338-240-135.jpg"
                ,"http://img.mukewang.com/55666c0a0001d6b506000338-240-135.jpg"};
        for (int i = 0; i < url1.length ; i++) {
            VedioBean bean = new VedioBean();
            bean.setImageUrl(url1[i]);
            bean.setVedioName(title[i]);
            programmeBeans.add(bean);
        }
        VedioGridAdapter adapter2 = new VedioGridAdapter(getActivity(),getContext(), programmeBeans);
        mProgrammingGrid.setAdapter(adapter2);
    }

    @OnItemClick({R.id.homepage_hotGrid, R.id.homepage_programming})
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        Intent intent;
        switch (parent.getId()) {
            case R.id.homepage_hotGrid:
                intent= new Intent(getActivity(), VideoOnDemandActivity.class);
                startActivity(intent);
                break;
            case R.id.homepage_programming:
                intent = new Intent(getActivity(), LiveActivity.class);
                intent.putExtra("live","watch");
                startActivity(intent);
                break;
        }
    }


    @OnClick({R.id.homePage_herald, R.id.homePage_QRCode,
            R.id.homePage_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.homePage_herald:
                if (!isShowPopup) {
                    popupWindow.showAsDropDown(mLayout, xOff, 0);
                    isShowPopup = true;
                } else {
                    popupWindow.dismiss();
                    isShowPopup = false;
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
        popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.img1));
        popupWindow.setAnimationStyle(R.style.mune_herald);
        ListView heraldList = (ListView) popupView.findViewById(R.id.LY_herald_list);
        heraldList.setAdapter(new HeraldListAdapter(getContext(), null, null, null));
        heraldList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
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

}
