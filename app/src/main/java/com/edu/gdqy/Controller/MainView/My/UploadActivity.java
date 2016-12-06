package com.edu.gdqy.Controller.MainView.My;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.gdqy.Controller.R;
import com.edu.gdqy.Tool.ImageTextGroupView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HXY on 2016/10/14.
 * 上传界面的Activity
 */

public class UploadActivity extends AppCompatActivity {
    @BindView(R.id.upload_shootUpload)
    ImageTextGroupView mShootUpload;
    @BindView(R.id.upload_localUpload)
    ImageTextGroupView mLocalUpload;
    @BindView(R.id.AC_Upload_Preview)
    ImageView mPreview;
    @BindView(R.id.upload_vedioName)
    TextView mVedioName;

    private static final int GETVEDIO_CODE = 520;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.hide();
        }
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mShootUpload.changeImageSize(120);
        mShootUpload.setImageIcon(R.drawable.shoot);
        mShootUpload.setTextName("拍摄上传");
        mLocalUpload.changeImageSize(120);
        mLocalUpload.setImageIcon(R.drawable.file);
        mLocalUpload.setTextName("本地上传");
    }

    @OnClick({R.id.upload_back, R.id.upload_shootUpload, R.id.upload_localUpload, R.id.upload_uploadBtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.upload_back:
                this.finish();
                break;
            case R.id.upload_shootUpload:
                break;
            case R.id.upload_localUpload:
                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, GETVEDIO_CODE);
                break;
            case R.id.upload_uploadBtn:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GETVEDIO_CODE) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                if (uri!=null) {
                    Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                    cursor.moveToFirst();
                    String vedioName = cursor.getString(2); // 图片文件名称

                    MediaMetadataRetriever media = new MediaMetadataRetriever();
                    media.setDataSource(this, uri);
                    Bitmap frameAtTime = media.getFrameAtTime();
                    if (frameAtTime != null) {
                        mPreview.setImageBitmap(frameAtTime);
                        mVedioName.setText(vedioName);
                    }
                }else {
                    Toast.makeText(this,"url=null",Toast.LENGTH_SHORT).show();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
