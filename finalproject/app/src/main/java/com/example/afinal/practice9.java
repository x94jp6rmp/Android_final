package com.example.afinal;
import java.util.Calendar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class practice9 extends Activity
{

    TextView setTime1;
    TextView setTime2;
    Button mButton1;
    Button mButton2;
    Button mButton3;
    Button mButton4;
    Calendar c=Calendar.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTime1=findViewById(R.id.setTime1);
        mButton1=findViewById(R.id.mButton1);
        mButton1.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                c.setTimeInMillis(System.currentTimeMillis());
                int mHour=c.get(Calendar.HOUR_OF_DAY);
                int mMinute=c.get(Calendar.MINUTE);

                new TimePickerDialog(practice9.this,
                        new TimePickerDialog.OnTimeSetListener()
                        {
                            public void onTimeSet(TimePicker view,int hourOfDay,
                                                  int minute)
                            {
                                c.setTimeInMillis(System.currentTimeMillis());
                                c.set(Calendar.HOUR_OF_DAY,hourOfDay);
                                c.set(Calendar.MINUTE,minute);
                                c.set(Calendar.SECOND,0);
                                c.set(Calendar.MILLISECOND,0);

                                Intent intent = new Intent(practice9.this, practice9_2.class);
                                PendingIntent sender=PendingIntent.getBroadcast(
                                        practice9.this,0, intent, 0);
                                AlarmManager am;
                                am = (AlarmManager)getSystemService(ALARM_SERVICE);
                                am.set(AlarmManager.RTC_WAKEUP,
                                        c.getTimeInMillis(),
                                        sender
                                );

                                String tmpS=format(hourOfDay)+"："+format(minute);
                                setTime1.setText(tmpS);
                                Toast.makeText(practice9.this,"設定鬧鐘時間為"+tmpS,
                                        Toast.LENGTH_SHORT)
                                        .show();
                            }
                        },mHour,mMinute,true).show();
            }
        });

        mButton2= findViewById(R.id.mButton2);
        mButton2.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(practice9.this, practice9_2.class);
                PendingIntent sender=PendingIntent.getBroadcast(
                        practice9.this,0, intent, 0);
                AlarmManager am;
                am =(AlarmManager)getSystemService(ALARM_SERVICE);
                am.cancel(sender);

                Toast.makeText(practice9.this,"鬧鐘時間解除",
                        Toast.LENGTH_SHORT).show();
                setTime1.setText("目前無設定");
            }
        });


        setTime2= findViewById(R.id.setTime2);
        LayoutInflater factory = LayoutInflater.from(this);
        final View setView = factory.inflate(R.layout.timeset,null);
        final TimePicker tPicker=setView
                .findViewById(R.id.tPicker);
        tPicker.setIs24HourView(true);

        final AlertDialog di=new AlertDialog.Builder(practice9.this)
                .setIcon(R.drawable.clock)
                .setTitle("設定")
                .setView(setView)
                .setPositiveButton("確定",
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                EditText ed=setView.findViewById(R.id.mEdit);
                                int times=Integer.parseInt(ed.getText().toString())
                                        *1000;
                                c.setTimeInMillis(System.currentTimeMillis());
                                c.set(Calendar.HOUR_OF_DAY,tPicker.getCurrentHour());
                                c.set(Calendar.MINUTE,tPicker.getCurrentMinute());
                                c.set(Calendar.SECOND,0);
                                c.set(Calendar.MILLISECOND,0);

                                Intent intent = new Intent(practice9.this,
                                        practice9_2.class);
                                PendingIntent sender = PendingIntent.getBroadcast(
                                        practice9.this,1, intent, 0);
                                AlarmManager am;
                                am = (AlarmManager)getSystemService(ALARM_SERVICE);
                                am.setRepeating(AlarmManager.RTC_WAKEUP,
                                        c.getTimeInMillis(),times,sender);
                                String tmpS=format(tPicker.getCurrentHour())+"："+
                                        format(tPicker.getCurrentMinute());
                                setTime2.setText("設定鬧鐘時間為"+tmpS+
                                        "開始，重複間隔時間為"+times/5000+"分鐘");
                                Toast.makeText(practice9.this,"設定鬧鐘時間為"+tmpS+
                                                "開始，重複間格時間為"+times/5000+"分鐘",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                .setNegativeButton("取消",
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which)
                            {
                            }
                        }).create();


        mButton3=(Button) findViewById(R.id.mButton3);
        mButton3.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

                c.setTimeInMillis(System.currentTimeMillis());
                tPicker.setCurrentHour(c.get(Calendar.HOUR_OF_DAY));
                tPicker.setCurrentMinute(c.get(Calendar.MINUTE));

                di.show();
            }
        });

        mButton4=(Button) findViewById(R.id.mButton4);
        mButton4.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(practice9.this, practice9_2.class);
                PendingIntent sender = PendingIntent.getBroadcast(
                        practice9.this,1, intent, 0);

                AlarmManager am;
                am = (AlarmManager)getSystemService(ALARM_SERVICE);
                am.cancel(sender);
                Toast.makeText(practice9.this,"鬧鐘時間解除",
                        Toast.LENGTH_SHORT).show();
                setTime2.setText("目前無設定");
            }
        });
    }


    private String format(int x)
    {
        String s=""+x;
        if(s.length()==1) s="0"+s;
        return s;
    }
}

