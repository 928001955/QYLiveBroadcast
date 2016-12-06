package com.edu.gdqy.Controller.MainView.Square;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edu.gdqy.Controller.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HXY on 2016/10/7.
 * 广场界面  分类
 */

public class SquareFragment extends Fragment {

    @BindView(R.id.square_tablayout)
    TabLayout mTablayout;
    @BindView(R.id.square_viewpager)
    ViewPager mViewpager;

    private List<Fragment> fragments;
    private String[] titles;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_square, null);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        fragments= new ArrayList<>();
        ClassifyTeachFragment teachFragment = new ClassifyTeachFragment();
        ClassifyCoachFragment coachFragment = new ClassifyCoachFragment();
        ClassifyLectureFragment lectureFragment = new ClassifyLectureFragment();
        ClassifyExchangeFragment exchangeFragment = new ClassifyExchangeFragment();
        fragments.add(teachFragment);
        fragments.add(coachFragment);
        fragments.add(lectureFragment);
        fragments.add(exchangeFragment);

        titles = getResources().getStringArray(R.array.live_category);
        mTablayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
        mTablayout.addTab(mTablayout.newTab().setText(titles[0]));//添加tab选项卡
        mTablayout.addTab(mTablayout.newTab().setText(titles[1]));
        mTablayout.addTab(mTablayout.newTab().setText(titles[2]));
        mTablayout.addTab(mTablayout.newTab().setText(titles[3]));

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager(),fragments,titles);
        mViewpager.setAdapter(adapter);
        mTablayout.setupWithViewPager(mViewpager);
        mTablayout.setTabsFromPagerAdapter(adapter);
    }
}
