package com.edu.gdqy.Controller.MainView.My;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.edu.gdqy.Controller.R;
import com.edu.gdqy.Tool.PublicVariable;

/**
 * Created by HXY on 2016/10/14.
 * 更多设置Fragment的Activity容器
 */

public class MoreSetActivity extends AppCompatActivity {

    private Fragment setFragment;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moreset);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.hide();
        }
        token = getIntent().getStringExtra("secretKey");
        addsetFragment();
    }

    private void addsetFragment() {
        Bundle bundle = new Bundle();
        bundle.putString("secretKey",token);
        setFragment = new MoreSetFragment();
        setFragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.moreSet_frame, setFragment);
        ft.commit();
    }
}
