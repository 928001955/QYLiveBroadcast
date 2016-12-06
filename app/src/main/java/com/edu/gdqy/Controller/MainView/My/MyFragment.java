package com.edu.gdqy.Controller.MainView.My;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.gdqy.Controller.AnchorAudienceView.Live.LiveActivity;
import com.edu.gdqy.Controller.LoginRegisterView.LoginRegisterActivity;
import com.edu.gdqy.Controller.R;
import com.edu.gdqy.Model.ConnectServer;
import com.edu.gdqy.Tool.AvatarClipping.CatActivity;
import com.edu.gdqy.Tool.ImageTextGroupView;
import com.edu.gdqy.Tool.PublicVariable;
import com.edu.gdqy.Tool.Publicmethod;
import com.edu.gdqy.bean.UserInfoBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HXY on 2016/10/7.
 * 我的界面
 */

public class MyFragment extends Fragment {
    @BindView(R.id.mySelf_photo)
    ImageView mPhoto;
    @BindView(R.id.mySelf_loginRegister)
    TextView mLoginRegister;
    @BindView(R.id.mySelf_history)
    ImageTextGroupView mHistory;
    @BindView(R.id.mySelf_manage)
    ImageTextGroupView mManager;
    @BindView(R.id.mySelf_release)
    ImageTextGroupView mRelease;
    @BindView(R.id.mySelf_upload)
    ImageTextGroupView mUpload;

    private String token;       //用户密钥
    private BroadCastListener listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注册广播 监听登录，头像的修改,登出
        IntentFilter filter = new IntentFilter();
        filter.addAction(PublicVariable.REVISEDATEFRAGMENT_ACTION);
        IntentFilter filter1 = new IntentFilter();
        filter1.addAction(PublicVariable.MORESETFRAGMENT_ACTION);
        IntentFilter filter2 = new IntentFilter();
        filter1.addAction(PublicVariable.LOGINFRAGMENTFRAGMENT_ACTION);
        listener = new BroadCastListener();
        getActivity().registerReceiver(listener, filter);
        getActivity().registerReceiver(listener, filter1);
        getActivity().registerReceiver(listener, filter2);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myself, null);
        ButterKnife.bind(this, view);

        String textName[] = new String[]{"记录", "云管理", "直播预告", "上传"};
        int imageIcon[] = new int[]{R.drawable.history, R.drawable.manage, R.drawable.release_notice
                , R.drawable.upload};
        ImageTextGroupView[] views = new ImageTextGroupView[]{mHistory, mManager, mRelease, mUpload};
        init(views, textName, imageIcon);
        return view;
    }

    private void init(ImageTextGroupView[] views, String textName[], int imageIcon[]) {
        for (int i = 0; i < views.length; i++) {
            views[i].changeImageSize(100);
            views[i].setImageIcon(imageIcon[i]);
            views[i].setTextName(textName[i]);
        }
    }

    @OnClick({R.id.mySelf_photo, R.id.mySelf_loginRegister, R.id.mySelf_applyForLive,
            R.id.mySelf_history, R.id.mySelf_manage, R.id.mySelf_release, R.id.mySelf_upload,
            R.id.mySlef_moreSet})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mySelf_photo:
                gotoMoreSetActivity();
                break;
            case R.id.mySelf_loginRegister:
                if (PublicVariable.isLogin) return;
                jumpActivity(LoginRegisterActivity.class);
                break;
            case R.id.mySelf_applyForLive:
                if (PublicVariable.isLogin) {
                    showWriteTopicDialog();
                } else {
                    Toast.makeText(getContext(), "您尚未登录", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.mySelf_history:
                if (PublicVariable.isLogin)
                    jumpActivity(HistoryActivity.class);
                else
                    Toast.makeText(getContext(), "您尚未登录", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mySelf_manage:
                if (PublicVariable.isLogin) {
                    String key[] = new String[]{"nickName", "avatarUrl", "loginName", "status"};
                    String velue[] = new String[]{"黄", "http", "92800", "1"};
                    String json = Publicmethod.createJson("UserInfoBean", key, velue);
                    UserInfoBean bean = Publicmethod.jsonToObject(json);
                } else {
                    Toast.makeText(getContext(), "您尚未登录", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.mySelf_release:
                if (PublicVariable.isLogin)
                    jumpActivity(ReleaseNoticeActivity.class);
                else
                    Toast.makeText(getContext(), "您尚未登录", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mySelf_upload:
                if (PublicVariable.isLogin)
                    jumpActivity(UploadActivity.class);
                else
                    Toast.makeText(getContext(), "您尚未登录", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mySlef_moreSet:
                gotoMoreSetActivity();
                break;
        }
    }

    private void showWriteTopicDialog() {
        Bundle bundle = new Bundle();
        bundle.putString("secretKey",token);
        WriteTopicDiglog diglog = new WriteTopicDiglog();
        diglog.setArguments(bundle);
        diglog.show(getActivity().getFragmentManager(), "WriteTopicDiglog");
    }

    private void gotoMoreSetActivity() {
        Intent intent = new Intent(getActivity(), MoreSetActivity.class);
        intent.putExtra("secretKey", token);
        startActivity(intent);
    }

    private void jumpActivity(Class<?> mclass) {
        Intent intent = new Intent(getActivity(), mclass);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(listener);
    }


    private class BroadCastListener extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            manipulationDateUpdate(intent);
        }
    }

    private void manipulationDateUpdate(Intent intent) {
        switch (intent.getAction()) {
            case PublicVariable.MORESETFRAGMENT_ACTION:
                boolean islogout = intent.getBooleanExtra("logout", false);
                if (islogout) {
                    mLoginRegister.setText("登录|注册");
                    mPhoto.setImageResource(R.drawable.photo);
                }
                break;
            case PublicVariable.REVISEDATEFRAGMENT_ACTION:
                disposeModifyUserInfo(intent);
                break;
            case PublicVariable.LOGINFRAGMENTFRAGMENT_ACTION:
                Handler handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                    }
                };

                mLoginRegister.setText("显耀,欢迎你");
                token = intent.getStringExtra("secretKey");
                ConnectServer cs = new ConnectServer();
                cs.requestUserInfo(handler, token);
                break;
        }
    }

    private void disposeModifyUserInfo(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            boolean ischange = extras.getBoolean("photo");
            String nikename = extras.getString("nikename");
            if (ischange) {
                Bitmap bitmap = CatActivity.bitmap;
                if (bitmap != null)
                    mPhoto.setImageBitmap(bitmap);
            }
            mLoginRegister.setText(nikename + "，欢迎你");
        }
    }
}
