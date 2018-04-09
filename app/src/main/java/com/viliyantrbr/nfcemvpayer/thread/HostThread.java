package com.viliyantrbr.nfcemvpayer.thread;

import android.content.Context;

import com.viliyantrbr.nfcemvpayer.util.LogUtil;

public class HostThread implements Runnable {
    private static final String TAG = HostThread.class.getSimpleName();

    public HostThread(Context context) {

    }

    @Override
    public void run() {
        LogUtil.d(TAG, "\"" + TAG + "\": Thread run");
    }
}
