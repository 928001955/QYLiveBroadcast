package com.edu.gdqy.Controller.LoginRegisterView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edu.gdqy.Controller.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HXY on 2016/10/25.
 * 注册界面Fragment
 */

public class RegisterFragment extends Fragment {
    @BindView(R.id.register_registerTitle)
    TabLayout mRegisterTitle;
    @BindView(R.id.register_registerPager)
    ViewPager mRegisterPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, null);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        RegisterViewpagerAdapter adapter = new RegisterViewpagerAdapter(getChildFragmentManager());
        mRegisterPager.setAdapter(adapter);
        mRegisterTitle.setupWithViewPager(mRegisterPager);
    }

    @OnClick(R.id.register_back)
    public void onClick() {
        getActivity().getSupportFragmentManager().popBackStack();
    }


}
