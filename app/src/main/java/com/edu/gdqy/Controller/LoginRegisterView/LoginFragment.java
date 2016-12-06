package com.edu.gdqy.Controller.LoginRegisterView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.edu.gdqy.Controller.R;
import com.edu.gdqy.Model.ConnectServer;
import com.edu.gdqy.Tool.LoadingDialog;
import com.edu.gdqy.Tool.PublicVariable;
import com.edu.gdqy.Tool.Publicmethod;
import com.google.gson.JsonElement;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HXY on 2016/10/25.
 * 登录界面的Fragment
 */

public class LoginFragment extends Fragment {
    @BindView(R.id.login_userInput)
    EditText mUserInput;
    @BindView(R.id.login_passwordInput)
    EditText mPasswordInput;
    @BindView(R.id.login_Remember)
    CheckBox mRemember;
    @BindView(R.id.login_Automatic)
    CheckBox mAutomatic;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, null);
        ButterKnife.bind(this, view);

        init();
        return view;
    }

    private void init() {
        Context context = getContext();
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.myself);
        drawable.setBounds(10, 0, 70, 80);
        mUserInput.setCompoundDrawables(drawable, null, null, null);
        drawable = ContextCompat.getDrawable(context, R.drawable.password);
        drawable.setBounds(10, 0, 70, 80);
        mPasswordInput.setCompoundDrawables(drawable, null, null, null);

        SharedPreferences sp = getActivity().getSharedPreferences("LoginInfo", 0);
        String user = sp.getString("user", "");
        String password = sp.getString("password", "");
        boolean remember = sp.getBoolean("remember", false);
        mUserInput.setText(user);
        mPasswordInput.setText(password);
        mRemember.setChecked(remember);
    }


    @OnClick({R.id.login_back, R.id.login_forgetPassword, R.id.login_registerUser, R.id.login_immediatelyLogin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_back:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.login_forgetPassword:
                ForgetPasswordFragment forgetPasswordFragment = new ForgetPasswordFragment();
                replaceFragment(forgetPasswordFragment);
                break;
            case R.id.login_registerUser:
                RegisterFragment registerFragment = new RegisterFragment();
                replaceFragment(registerFragment);
                break;
            case R.id.login_immediatelyLogin:
                if (checkEmpty() || !Publicmethod.checkNetwork(getContext()))
                    return;
                LoadingDialog dialog = new LoadingDialog();
                dialog.show(getActivity().getFragmentManager(), "dialog");
                disposeLogininfo(dialog);
                break;
        }
    }

    private void disposeLogininfo(final LoadingDialog dialog) {
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Map<String, JsonElement> stringObjectMap = Publicmethod.jsonToMap((String) msg.obj);
                boolean isLoginSuccess = stringObjectMap.get("code").getAsBoolean();
                if (isLoginSuccess) {
                    PublicVariable.isLogin = true;
                    sendBroadcastToMyself(stringObjectMap.get("token").getAsString());
                    getActivity().finish();
                    dialog.dismiss();
                } else {
                    String errorMessage = stringObjectMap.get("errMessage").getAsString();
                    Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        };

        String username = mUserInput.getText().toString().trim();
        String password = mPasswordInput.getText().toString().trim();
        String[] key = new String[]{"username", "password"};
        String[] velue = new String[]{username, password};
        ConnectServer connectServer = new ConnectServer();
        connectServer.sendParameterToServer(handler, key, velue,"/front/authorize/login");
        saveLoginInfo(username, password);
    }

    private void sendBroadcastToMyself(String token) {
        Intent intent = new Intent();
        intent.setAction(PublicVariable.LOGINFRAGMENTFRAGMENT_ACTION);
        intent.putExtra("secretKey", token);
        getActivity().sendBroadcast(intent);
    }

    private boolean checkEmpty() {
        boolean result = false;
        if (mUserInput.length() == 0 || mPasswordInput.length() == 0) {
            Toast.makeText(getActivity(), "账号或密码输入为空", Toast.LENGTH_LONG).show();
            result = true;
        }
        return result;
    }

    private void saveLoginInfo(String username, String password) {
        SharedPreferences sp = getActivity().getSharedPreferences("LoginInfo", 0);
        SharedPreferences.Editor editor = sp.edit();
        if (mRemember.isChecked()) {
            editor.putString("user", username);
            editor.putString("password", password);
            editor.putBoolean("remember", true);
        } else {
            editor.putString("user", "");
            editor.putString("password", "");
            editor.putBoolean("remember", false);
        }
        if (mAutomatic.isChecked())
            editor.putBoolean("automatic", true);
        else
            editor.putBoolean("automatic", false);
        editor.commit();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
        fm.replace(R.id.loginregister_frame, fragment);
        fm.addToBackStack(null);
        fm.commit();
    }
}
