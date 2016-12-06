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
 * 专题讲座选项下的小选项Fragment
 */

public class ClassifyLectureFragment extends Fragment {
    @BindView(R.id.classifyLecture_gridView)
    GridView mGridView;
    private String[] teachList;
    private int image[];

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_classifylecture, null);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        teachList = getResources().getStringArray(R.array.live_category3);
        image = new int[6];
        image[0] = R.drawable.lecture1;
        image[1] = R.drawable.lecture2;
        image[2] = R.drawable.lecture3;
        image[3] = R.drawable.lecture4;
        image[4] = R.drawable.lecture5;
        image[5] = R.drawable.lecture6;

        ClassifyListAdapter adapter = new ClassifyListAdapter(image, teachList,getContext());
        mGridView.setAdapter(adapter);
    }

}
