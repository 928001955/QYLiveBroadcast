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
 * 课后辅导选项下的小选项Fragment
 */

public class ClassifyCoachFragment extends Fragment {
    @BindView(R.id.classifyCoach_gridView)
    GridView mGridView;
    private String[] teachList;
    private int image[];
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_classifycoach, null);
        ButterKnife.bind(this, view);
        init();
        return view;
    }


    private void init() {
        teachList = getResources().getStringArray(R.array.live_category2);
        image = new int[4];
        image[0] = R.drawable.coach1;
        image[1] = R.drawable.coach2;
        image[2] = R.drawable.coach3;
        image[3] = R.drawable.coach4;
        ClassifyListAdapter adapter = new ClassifyListAdapter(image,teachList,getContext());
        mGridView.setAdapter(adapter);
    }


}
