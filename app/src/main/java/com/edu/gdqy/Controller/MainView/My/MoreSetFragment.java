package com.edu.gdqy.Controller.MainView.My;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.edu.gdqy.Controller.R;
import com.edu.gdqy.Tool.PublicVariable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * Created by HXY on 2016/10/14.
 * 显示更多设置的Fragment
 */

public class MoreSetFragment extends Fragment {
    @BindView(R.id.moreset_setList)
    ListView mSetList;

    private String token;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_moreset, null);
        ButterKnife.bind(this, view);

        token = getArguments().getString("secretKey");
        SetListAdapter adapter = new SetListAdapter(getContext());
        mSetList.setAdapter(adapter);
        return view;
    }

    @OnClick(R.id.moreset_back)
    public void onClick() {
        getActivity().finish();
    }

    @OnItemClick(R.id.moreset_setList)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                if (PublicVariable.isLogin)
                    gotoReviseDateFragement();
                else
                    Toast.makeText(getContext(), "您尚未登录", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                break;
            case 4:
                break;
            case 5:
                AboutUsFragment usFragment = new AboutUsFragment();
                gotoFragment(usFragment);
                break;
            case 6:
                logout();
        }
    }

    private void gotoFragment(Fragment fragment) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.moreSet_frame, fragment).commit();
    }

    private void gotoReviseDateFragement() {
        Bundle bundle = new Bundle();
        bundle.putString("secretKey", token);
        ReviseDateFragment rdf = new ReviseDateFragment();
        rdf.setArguments(bundle);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.moreSet_frame, rdf);
        ft.addToBackStack(null).commit();
    }

    private void logout() {
        if (PublicVariable.isLogin) {
            PublicVariable.isLogin = false;
            Toast.makeText(getContext(), "已退出", Toast.LENGTH_SHORT).show();
            sendBroadcastToMyFragment();
        } else {
            Toast.makeText(getContext(), "您尚未登录", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendBroadcastToMyFragment() {
        Intent intent = new Intent(PublicVariable.MORESETFRAGMENT_ACTION);
        intent.putExtra("logout", true);
        getActivity().sendBroadcast(intent);
    }
}
