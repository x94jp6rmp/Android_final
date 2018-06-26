package com.example.afinal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;



public class practice9_1 extends Activity
{
    private SoundPool sp;
    private MediaPlayer mp=new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        final MediaPlayer mp =MediaPlayer.create(this, R.raw.ring);
        new AlertDialog.Builder(practice9_1.this)
                .setIcon(R.drawable.clock)
                .setTitle("起床")
                .setMessage("給我起床喔")
                .setPositiveButton("不要",
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int whichButton)
                            {
                                practice9_1.this.finish();
                                mp.stop();
                            }
                        })
                .show();
        mp.start();


    }

    }


