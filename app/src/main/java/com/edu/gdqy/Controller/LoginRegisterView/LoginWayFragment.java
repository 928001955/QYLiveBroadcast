package com.edu.gdqy.Controller.LoginRegisterView;

import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.edu.gdqy.Controller.R;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HXY on 2016/10/25.
 * 登录方式选择
 */

public class LoginWayFragment extends Fragment {
    @BindView(R.id.loginWay_playAudio)
    SurfaceView mPlayAudio;
    @BindView(R.id.loginWay_wayQQ)
    ImageView mWayQQ;
    @BindView(R.id.loginWay_wayWechat)
    ImageView mWechat;
    @BindView(R.id.loginWay_wayQinghao)
    ImageView mQinghao;

    private MediaPlayer mediaPlayer;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loginway, null);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        mPlayAudio.getHolder().setKeepScreenOn(true);//keepScreenOn属性保持屏幕常亮
        mPlayAudio.getHolder().addCallback(new SurfaceViewLis());//添加回调函数
    }

    @OnClick({R.id.loginWay_wayQQ, R.id.loginWay_wayWechat, R.id.loginWay_wayQinghao})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginWay_wayQQ:
                break;
            case R.id.loginWay_wayWechat:
                break;
            case R.id.loginWay_wayQinghao:
                LoginFragment loginFragment = new LoginFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.loginregister_frame,loginFragment);
                fm.addToBackStack(null);
                fm.commit();
                break;
        }
    }

    private class SurfaceViewLis implements SurfaceHolder.Callback{

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        }

        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            try{
                play();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        }
    }

    //播放背景视频方法
    private void play() throws IOException {
        AssetFileDescriptor mAssetFileDescriptor = getActivity().getAssets().openFd("loginwayback.mp4");
        mediaPlayer = new MediaPlayer(); //必须在surface创建后才能初始化MediaPlayer,否则不会显示图像
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_VOICE_CALL); //设置视频流的类型
        mediaPlayer.setDisplay(mPlayAudio.getHolder());
        mediaPlayer.setDataSource(mAssetFileDescriptor.getFileDescriptor(),
                mAssetFileDescriptor.getStartOffset(),mAssetFileDescriptor.getLength());
        mediaPlayer.setLooping(true);//是否循环播放
        //异步加载视频图像
        mediaPlayer.prepareAsync();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                //装载完毕
                LoginWayFragment.this.mediaPlayer.start();//播放视频
            }
        });

    }
}
