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
import com.google.gson.JsonElement;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HXY on 2016/10/25.
 * 注册页面的邮箱注册页面
 */

public class MailRegisterFragment extends Fragment {
    @BindView(R.id.mailregister_mailInput)
    EditText mMailInput;
    @BindView(R.id.mailregister_codeInput)
    EditText mCodeInput;
    @BindView(R.id.mailregister_passwordInput)
    EditText mPasswordInput;
    @BindView(R.id.mailregister_captcha)
    Button mCaptcha;
    @BindView(R.id.mailregister_nickname)
    EditText mNickname;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mailregister, null);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        setEditTextDrawable(mMailInput, R.drawable.mail);
        setEditTextDrawable(mCodeInput, R.drawable.captcha);
        setEditTextDrawable(mPasswordInput, R.drawable.password);
        setEditTextDrawable(mNickname, R.drawable.nickname);
    }

    private void setEditTextDrawable(EditText editText, int resource) {
        Context context = getContext();
        Drawable drawable = ContextCompat.getDrawable(context, resource);
        drawable.setBounds(10, 0, 70, 80);
        editText.setCompoundDrawables(drawable, null, null, null);
    }

    @OnClick({R.id.mailregister_captcha, R.id.mailregister_registerBtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mailregister_captcha:
                if (CheckupPhoneMail.testMail(mMailInput.getText().toString().trim())) {
                    startCounter();
                    requestCode();
                } else Toast.makeText(getActivity(), "您输入的邮箱不符合规定", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mailregister_registerBtn:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        if (!isEmpty()) {
            Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    String json = (String) msg.obj;
                    Map<String, JsonElement> map = Publicmethod.jsonToMap(json);
                    boolean issuccess = map.get("code").getAsBoolean();
                    if (issuccess) {
                        Toast.makeText(getContext(), "注册成功", Toast.LENGTH_SHORT).show();
                        getFragmentManager().popBackStack();
                    } else {
                        Toast.makeText(getContext(), map.get("errMessage").getAsString(), Toast.LENGTH_SHORT).show();
                    }
                }
            };
            sendRegisterInfo(handler);
        }
    }

    private void sendRegisterInfo(Handler handler) {
        String nickName = mNickname.getText().toString();
        String password = mPasswordInput.getText().toString();
        String email = mMailInput.getText().toString().trim();
        String code = mCodeInput.getText().toString();
        String key[] = new String[]{"nickName", "password", "email", "code"};
        String velue[] = new String[]{nickName, password, email, code};
        ConnectServer cs = new ConnectServer();
        cs.sendParameterToServer(handler, key, velue, "/front/user/register");
    }

    private boolean isEmpty() {
        boolean b = CheckupPhoneMail.testMail(mMailInput.getText().toString().trim());
        if (!b) {
            Toast.makeText(getActivity(), "您输入的邮箱不符合规定！", Toast.LENGTH_SHORT).show();
            return true;
        } else if (mCodeInput.getText().toString().trim().equals("")
                || mPasswordInput.getText().toString().equals("")
                || mNickname.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "请完善信息！", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private void requestCode() {
        String key[] = new String[]{"recipient", "type"};
        String velue[] = new String[]{mMailInput.getText().toString()
                , PublicVariable.SEND_EMAIL_CODE};
        String s = "928001955%40qq.com";
        try {
            String decode = URLDecoder.decode(s, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        ConnectServer cs = new ConnectServer();
        cs.sendParameterToServer(null, key, velue, "/font/user/verifyCode");
    }

    private void startCounter() {
        GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(getContext(), R.drawable.shape_button_gray);
        mCaptcha.setBackground(drawable);
        mCaptcha.setEnabled(false);
        mMailInput.setEnabled(false);
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                disposeViewStatus(msg.what);
            }
        };
        Counter counter = new Counter(handler);
        Thread thread = new Thread(counter);
        thread.start();
    }

    private void disposeViewStatus(int what) {
        Context context = getContext();
        if (context != null) {
            if (what != 0) {
                mCaptcha.setText(what + "");
            } else {
                mPasswordInput.setEnabled(true);
                mCaptcha.setText("验证码");
                GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(context, R.drawable.shape_button_blue);
                mCaptcha.setBackground(drawable);
                mCaptcha.setEnabled(true);
                mMailInput.setEnabled(true);
            }
        }
    }

}
