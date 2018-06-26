package com.example.afinal;

import android.app.Activity;
import android.graphics.Bitmap;
import android.widget.TextView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.AnalogClock;
import android.widget.TextView;
import java.util.Calendar;
import java.lang.Thread;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

public class clock extends Activity {
        /*聲明一常數作為判別信息用*/
        protected static final int GUINOTIFIER = 0x1234;
        /*聲明兩個widget對像變量*/
        TextView mTextView;
        AnalogClock mAnalogClock;
        /*聲明與時間相關的變量*/
        Calendar mCalendar;
        int mMinutes;
        int mHour;
        int mSecond ;
        Bitmap second ;
        /*聲明關鍵Handler與Thread變量*/
        Handler mHandler;
        Thread mClockThread;
        /** Called when the activity is first created. */
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            /*通過findViewById取得兩個widget對象*/
           // mTextView=(TextView)findViewById(R.id.myTextView);
            mAnalogClock=(AnalogClock)findViewById(R.id.clock);
            /*通過Handler來接收進程所傳遞的信息並更新TextView*/
            mHandler = new Handler() {
                public void handleMessage(Message msg) {
                    /*處理信息的方法*/
                    switch (msg.what) {
                        case clock.GUINOTIFIER:
                            /* 在這處理要TextView對象Show時間的事件*/
                            mTextView.setText(mHour+" : "+mMinutes);
                            break;
                    }
                    super.handleMessage(msg);
                }
            };
            /*通過進程來持續取得系統時間*/
            mClockThread=new LooperThread();
            mClockThread.start();
        }
        /*改寫一個Thread Class用來持續取得系統時間*/
        class LooperThread extends Thread {
            public void run() {
                super.run();
                try {
                    do {
                        /*取得系統時間*/
                        long time = System.currentTimeMillis();
                        /*通過Calendar對象來取得小時與分鐘*/
                        final Calendar mCalendar = Calendar.getInstance();
                        mCalendar.setTimeInMillis(time);
                        mHour = mCalendar.get(Calendar.HOUR);
                        mMinutes = mCalendar.get(Calendar.MINUTE);
                        /*讓進程休息一秒*/
                        Thread.sleep(1000);
                        /*重要關鍵程序:取得時間後發出信息給Handler*/
                        Message m = new Message();
                        m.what = clock.GUINOTIFIER;
                        clock.this.mHandler.sendMessage(m);
                    }
                    while(clock.LooperThread.interrupted()==false);
                    /*當系統發出中斷信息時停止本循環*/
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

