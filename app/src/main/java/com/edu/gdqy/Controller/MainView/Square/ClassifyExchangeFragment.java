package com.edu.gdqy.Controller.MainView.Square;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.edu.gdqy.Controller.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HXY on 2016/10/9.
 * 学生交流选项下的小选项Fragment
 */

public class ClassifyExchangeFragment extends Fragment {
    @BindView(R.id.classifyExchange_gridView)
    GridView mGridView;
    private String[] teachList;
    private int image[];

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_classifyexchange, null);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        teachList=getResources().getStringArray(R.array.live_category4);
        image = new int[8];
        image[0] = R.drawable.exchange1;
        image[1] = R.drawable.exchange2;
        image[2] = R.drawable.exchange3;
        image[3] = R.drawable.exchange4;
        image[4] = R.drawable.exchange5;
        image[5] = R.drawable.exchange6;
        image[6] = R.drawable.exchange7;
        image[7] = R.drawable.exchange8;

        ClassifyListAdapter adapter = new ClassifyListAdapter(image,teachList,getContext());
        mGridView.setAdapter(adapter);
    }


}
