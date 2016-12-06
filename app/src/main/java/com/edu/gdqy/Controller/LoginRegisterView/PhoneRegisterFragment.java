package com.edu.gdqy.Controller.LoginRegisterView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.edu.gdqy.Controller.R;
import com.edu.gdqy.Model.ConnectServer;
import com.edu.gdqy.Tool.CheckupPhoneMail;
import com.edu.gdqy.Tool.Counter;
import com.edu.gdqy.Tool.PublicVariable;
import com.edu.gdqy.Tool.Publicmethod;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HXY on 2016/10/25.
 * 注册界面中手机注册的部分
 */

public class PhoneRegisterFragment extends Fragment {
    @BindView(R.id.phoneregister_phoneInput)
    EditText mPhoneInput;
    @BindView(R.id.phoneregister_codeInput)
    EditText mCodeInput;
    @BindView(R.id.phoneregister_passwordInput)
    EditText mPasswordInput;
    @BindView(R.id.phoneregister_captcha)
    Button mCaptcha;
    @BindView(R.id.phoneregister_nickname)
    EditText mNickname;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phoneregister, null);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        setEditTextDrawable(mPhoneInput, R.drawable.phone);
        setEditTextDrawable(mCodeInput, R.drawable.captcha);
        setEditTextDrawable(mPasswordInput, R.drawable.password);
        setEditTextDrawable(mNickname, R.drawable.nickname);
    }

    private void setEditTextDrawable(EditText editText, int resource) {
        Context context = getContext();
        Drawable drawable = ContextCompat.getDrawable(context, resource);
        drawable.setBounds(10, 0, 60, 70);
        editText.setCompoundDrawables(drawable, null, null, null);
    }

    @OnClick({R.id.phoneregister_captcha, R.id.phoneRegister_registerBtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.phoneregister_captcha:
                if (CheckupPhoneMail.testPhone(mPhoneInput.getText().toString().trim())) {
                    startCounter();
                    requestCode();
                } else Toast.makeText(getActivity(), "您输入的手机号不符合规定", Toast.LENGTH_SHORT).show();
                break;
            case R.id.phoneRegister_registerBtn:
                String phone = mPhoneInput.getText().toString();
                String identifyingcode = mCodeInput.getText().toString();
                String password = mPasswordInput.getText().toString();
                String nickname = mNickname.getText().toString();
                String key[] = new String[]{"phone", "code", "password", "nikename"};
                String velue[] = new String[]{phone, identifyingcode, password, nickname};
                String registerInfo = Publicmethod.createJson("registerInfo", key, velue);
                Log.w("TAG", registerInfo);
                ConnectServer connectServer = new ConnectServer();
            //    connectServer.loginInfoToServer(registerInfo, "servlet");
                break;
        }
    }

    private void requestCode() {
        String key[] = new String[]{"loginName", "code"};
        String velue[] = new String[]{mPhoneInput.getText().toString(),
                PublicVariable.SEND_MOBILE_CODE};
        ConnectServer cs = new ConnectServer();
        cs.sendParameterToServer(null,key,velue,"/font/user/verifyCode");
    }

    private void startCounter() {
        GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(getContext(), R.drawable.shape_button_gray);
        mCaptcha.setBackground(drawable);
        mCaptcha.setEnabled(false);
        mPhoneInput.setEnabled(false);
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Context context = getContext();
                if (context != null) {
                    int what = msg.what;
                    if (what != 0) {
                        mCaptcha.setText(what + "");
                    } else {
                        mPasswordInput.setEnabled(true);
                        mCaptcha.setText("验证码");
                        GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(context, R.drawable.shape_button_blue);
                        mCaptcha.setBackground(drawable);
                        mCaptcha.setEnabled(true);
                        mPhoneInput.setEnabled(true);
                    }
                }
            }
        };
        Counter counter = new Counter(handler);
        Thread thread = new Thread(counter);
        thread.start();
    }
}
