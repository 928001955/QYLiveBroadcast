package com.edu.gdqy.Controller.MainView.Square;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;

import com.edu.gdqy.Controller.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HXY on 2016/10/9.
 * 课程教学选项下的小选项Fragment
 */

public class ClassifyTeachFragment extends Fragment {
    @BindView(R.id.classifyTeach_gridView)
    GridView mGridView;

    private String[] teachList;
    private int image[];
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_classifyteach, null);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        teachList =getResources().getStringArray(R.array.live_category1);
        image = new int[8];
        image[0] = R.drawable.teach1;
        image[1] = R.drawable.teach2;
        image[2] = R.drawable.teach3;
        image[3] = R.drawable.teach4;
        image[4] = R.drawable.teach5;
        image[5] = R.drawable.teach6;
        image[6] = R.drawable.teach7;
        image[7] = R.drawable.teach8;

        ClassifyListAdapter adapter = new ClassifyListAdapter(image,teachList,getContext());
        mGridView.setAdapter(adapter);
    }

}
