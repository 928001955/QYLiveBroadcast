package com.edu.gdqy.Controller.LoginRegisterView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;

import com.edu.gdqy.Controller.R;

import butterknife.ButterKnife;

/**
 * Created by HXY on 2016/10/25.
 * 登录和注册所需Activity
 */

public class LoginRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginregister);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        init();
    }

    private void init() {
        LoginWayFragment wayFragment = new LoginWayFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.loginregister_frame, wayFragment);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = this.getSupportFragmentManager();
        int count = fm.getBackStackEntryCount();
        if (count == 0)
            super.onBackPressed();
        else
            fm.popBackStack();
    }
}
