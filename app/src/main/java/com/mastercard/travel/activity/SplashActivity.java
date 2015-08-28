package com.mastercard.travel.activity;

import android.content.Intent;
import android.os.Bundle;

import com.mastercard.travel.R;
import com.mastercard.travel.fragment.BaseFragment;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by diego.rotondale on 2/10/2015.
 * Copyright (c) 2015 AnyPresence, Inc. All rights reserved.
 */
public class SplashActivity extends BaseActivityWithoutToolbar {
    private static long SPLASH_DELAY = 3500;
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            callActivity();
        }
    };
    private boolean canOpenMainActivity = true;
    private boolean loginDone = false;

    private void callActivity() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (canOpenMainActivity) {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Timer().schedule(task, SPLASH_DELAY);
    }

    @Override
    public Class<? extends BaseFragment> getFragmentClass() {
        return null;
    }

    @Override
    protected void onStop() {
        super.onStop();
        canOpenMainActivity = false;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        canOpenMainActivity = true;
    }
}
