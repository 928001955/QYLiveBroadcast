package com.edu.gdqy.Controller.MainView.HomePage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import com.edu.gdqy.Controller.R;
import com.edu.gdqy.Tool.VedioGridAdapter;
import com.edu.gdqy.bean.VedioBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HXY on 2016/10/23.
 * 搜索界面
 */

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.search_InputKey)
    EditText mInputKey;
    @BindView(R.id.search_SearchBtn)
    ImageView mSearchBtn;
    @BindView(R.id.search_Result)
    GridView mResult;
    @BindView(android.R.id.empty)
    ImageView empty;

    private List<VedioBean> vedioBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        vedioBeanList = new ArrayList<>();

        mResult.setEmptyView(empty);
        VedioGridAdapter adapter = new VedioGridAdapter(this,this,vedioBeanList);
        mResult.setAdapter(adapter);
    }

    @OnClick({R.id.search_Back, R.id.search_SearchBtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_Back:
                this.finish();
                break;
            case R.id.search_SearchBtn:
                break;
        }
    }
}
