package com.edu.gdqy.Controller.MainView.BottomNavigationBar;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edu.gdqy.Controller.R;
import com.edu.gdqy.Tool.ImageTextGroupView;
import com.edu.gdqy.Tool.PublicVariable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HXY on 2016/10/7.
 * 底部导航栏的fragment
 */

public class BottomNavigationFragment extends Fragment {

    @BindView(R.id.bottonNavigation_homePage)
    ImageTextGroupView mHomePage;
    @BindView(R.id.bottonNavigation_square)
    ImageTextGroupView mSquare;
    @BindView(R.id.bottonNavigation_subscriber)
    ImageTextGroupView mSubscriber;
    @BindView(R.id.bottonNavigation_mySelf)
    ImageTextGroupView mMy;
    private NavigationListener navigationListener;
    private String position = PublicVariable.HOMEPAGE_FRAGMENT;   //当前所在的位置

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigationbar, null);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        navigationListener = (NavigationListener) context;
    }

    private void init() {
        mHomePage.setImageIcon(R.drawable.pressed_home);
        mHomePage.setTextName("主页");
        mSquare.setImageIcon(R.drawable.square);
        mSquare.setTextName("广场");
        mSubscriber.setImageIcon(R.drawable.subscriber);
        mSubscriber.setTextName("订阅");
        mMy.setImageIcon(R.drawable.myself);
        mMy.setTextName("我的");
    }

    /**
     * 判断是否点击的是同一个底部按钮
     */
    private boolean isOnclickSameBtn(String code) {
        if (code.equals(position)) {
            return true;
        }
        return false;
    }

    //将当前按钮图标变成未点击状态
    private void changePositionIcon() {
        if (position.equals(PublicVariable.HOMEPAGE_FRAGMENT))
            mHomePage.setImageIcon(R.drawable.home);
        else if (position.equals(PublicVariable.SQUARE_FRAGMENT))
            mSquare.setImageIcon(R.drawable.square);
        else if (position.equals(PublicVariable.SUBSCRIBER_FRAGMENT))
            mSubscriber.setImageIcon(R.drawable.subscriber);
        else mMy.setImageIcon(R.drawable.myself);
    }

    @OnClick({R.id.bottonNavigation_homePage, R.id.bottonNavigation_square, R.id.bottonNavigation_subscriber, R.id.bottonNavigation_mySelf})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bottonNavigation_homePage:
                if (!isOnclickSameBtn(PublicVariable.HOMEPAGE_FRAGMENT)) {
                    navigationListener.changeMainView(PublicVariable.HOMEPAGE_FRAGMENT, position);
                    mHomePage.setImageIcon(R.drawable.pressed_home);
                    changePositionIcon();
                    position = PublicVariable.HOMEPAGE_FRAGMENT;
                }
                break;
            case R.id.bottonNavigation_square:
                if (!isOnclickSameBtn(PublicVariable.SQUARE_FRAGMENT)) {
                    navigationListener.changeMainView(PublicVariable.SQUARE_FRAGMENT, position);
                    mSquare.setImageIcon(R.drawable.pressed_square);
                    changePositionIcon();
                    position = PublicVariable.SQUARE_FRAGMENT;
                }
                break;
            case R.id.bottonNavigation_subscriber:
                if (!isOnclickSameBtn(PublicVariable.SUBSCRIBER_FRAGMENT)) {
                    navigationListener.changeMainView(PublicVariable.SUBSCRIBER_FRAGMENT, position);
                    mSubscriber.setImageIcon(R.drawable.pressed_subscriber);
                    changePositionIcon();
                    position = PublicVariable.SUBSCRIBER_FRAGMENT;
                }
                break;
            case R.id.bottonNavigation_mySelf:
                if (!isOnclickSameBtn(PublicVariable.MY_FRAGMENT)) {
                    navigationListener.changeMainView(PublicVariable.MY_FRAGMENT, position);
                    mMy.setImageIcon(R.drawable.pressed_my);
                    changePositionIcon();
                    position = PublicVariable.MY_FRAGMENT;
                }
                break;
        }
    }


    public interface NavigationListener {
        void changeMainView(String code, String position);
    }
}
