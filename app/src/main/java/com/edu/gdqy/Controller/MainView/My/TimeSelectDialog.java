package com.edu.gdqy.Controller.MainView.My;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.edu.gdqy.Controller.R;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HXY on 2016/10/24.
 * 直播预告中的开始时间选择器
 */

public class TimeSelectDialog extends DialogFragment {
    @BindView(R.id.timeSelect_data)
    DatePicker mSelectData;
    @BindView(R.id.timeSelect_time)
    TimePicker mSelectTime;

    private int month,currentMonth;
    private int day,currentDay;
    private int hour,currentHour;
    private int minute;

    private StartTimeListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Activity activity = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_timeselect, null);
        builder.setView(view).setTitle("选择开始时间")
                .setPositiveButton("选择", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        month=mSelectData.getMonth()+1;
                        day=mSelectData.getDayOfMonth();
                        hour=mSelectTime.getCurrentHour();
                        minute=mSelectTime.getCurrentMinute();
                        if (month<currentMonth) {
                            Toast.makeText(getActivity(), "月份不能比当前月份小", Toast.LENGTH_SHORT).show();
                            return;
                        }else if (month==currentMonth && day==currentDay){
                            if (currentHour-hour<6){
                                Toast.makeText(getActivity(),"预告必须提前六小时",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        listener= (StartTimeListener) getActivity();
                        listener.updateStartInput(month,day,hour,minute);
                    }
                }).setNegativeButton("取消", null);

        ButterKnife.bind(this,view);
        init();
        return builder.create();
    }

    private void init() {
        ((ViewGroup) ((ViewGroup) mSelectData.getChildAt(0)).getChildAt(0)).getChildAt(0).setVisibility(View.GONE);
        mSelectTime.setIs24HourView(true);
        Calendar c = Calendar.getInstance();
        currentMonth = c.get(Calendar.MONTH)+1;
        currentDay = c.get(Calendar.DAY_OF_MONTH);
        currentHour=c.get(Calendar.HOUR_OF_DAY);
    }

    public interface StartTimeListener{
        public void updateStartInput(int month,int day,int hour,int minute);
    }

}
