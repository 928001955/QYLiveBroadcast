package com.edu.gdqy.Controller.MainView.My;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.gdqy.Controller.R;
import com.edu.gdqy.Tool.AvatarClipping.CatActivity;
import com.edu.gdqy.Tool.PublicVariable;

import java.io.FileNotFoundException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HXY on 2016/10/19.
 * 修改资料界面
 */

public class ReviseDateFragment extends Fragment {

    @BindView(R.id.revisedate_picture)
    ImageView mPicture;
    @BindView(R.id.revisedate_username)
    TextView mUsername;
    @BindView(R.id.revisedate_updatePassword)
    TextView mUpdatePassword;
    @BindView(R.id.revisedate_nickname)
    EditText mNickname;
    @BindView(R.id.revisedate_submit)
    Button mSubmit;

    private String token;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_revisedate, null);
        ButterKnife.bind(this, view);
        token=getArguments().getString("secretKey");
        requestUserInfo(token);
        return view;
    }

    private void requestUserInfo(String token) {
        Log.w("TAG",token);
    }

    @OnClick({R.id.revisedate_back, R.id.revisedate_picture, R.id.revisedate_updatePassword, R.id.revisedate_submit})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.revisedate_back:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.revisedate_picture:
                intent = new Intent(getActivity(), CatActivity.class);
                startActivityForResult(intent, 501);
                break;
            case R.id.revisedate_updatePassword:
                intent = new Intent(PublicVariable.SET_ACTION);
                intent.putExtra("FragmentCode", PublicVariable.MODIFYPASSWORDFRAGMENT);
                getActivity().sendBroadcast(intent);
                break;
            case R.id.revisedate_submit:
                if (mNickname.length()==0) {
                    Toast.makeText(getContext(), "请输入昵称", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    sendBroadCastToMyself();
                }
                break;
        }
    }

    //发送广播到MyFragment
    private void sendBroadCastToMyself() {
        Bundle bundle =new Bundle();
        bundle.putBoolean("photo",true);
        bundle.putString("nikename",mNickname.getText().toString());
        Intent intent = new Intent();
        intent.setAction(PublicVariable.REVISEDATEFRAGMENT_ACTION);
        intent.putExtras(bundle);
        getActivity().sendBroadcast(intent);
        getActivity().finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap = CatActivity.bitmap;
        if (bitmap != null) {
            mPicture.setImageBitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
