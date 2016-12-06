package com.edu.gdqy.Controller.MainView.My;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edu.gdqy.Controller.R;
import com.edu.gdqy.Tool.PublicVariable;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HXY on 2016/10/15.
 * 关于我们的Fragment
 */

public class AboutUsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aboutus, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.aboutus_back)
    public void onClick() {
        getActivity().getSupportFragmentManager().popBackStack();
    }
}
