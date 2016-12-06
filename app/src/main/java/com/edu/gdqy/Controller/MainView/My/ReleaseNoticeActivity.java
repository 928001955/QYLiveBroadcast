package com.edu.gdqy.Controller.MainView.My;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.edu.gdqy.Controller.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * Created by HXY on 2016/10/24.
 * 发布直播预告的页面
 */

public class ReleaseNoticeActivity extends AppCompatActivity
        implements TimeSelectDialog.StartTimeListener{
    @BindView(R.id.releasenotice_startTime)
    EditText mStartTime;
    @BindView(R.id.releasenotice_subject)
    EditText mSubject;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_releasenotice);
        ButterKnife.bind(this);
    }

    @OnTouch(R.id.releasenotice_startTime)
    public boolean onTouch(View view, MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            TimeSelectDialog timeSelectDialog = new TimeSelectDialog();
            timeSelectDialog.show(getFragmentManager(),"timeSelectDialog");
        }
        return true;
    }

    @OnClick(R.id.releasenotice_submit)
    public void onClick() {
        finish();
    }

    @Override
    public void updateStartInput(int month, int day, int hour, int minute) {
        mStartTime.setText(month+"月"+day+"日"+" "+hour+"时"+minute+"分");

    }
}
