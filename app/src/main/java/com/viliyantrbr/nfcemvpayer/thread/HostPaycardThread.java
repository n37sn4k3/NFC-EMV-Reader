package com.viliyantrbr.nfcemvpayer.thread;

import android.content.Context;
import android.support.annotation.NonNull;

import com.viliyantrbr.nfcemvpayer.util.LogUtil;

public class HostPaycardThread implements Runnable {
    private static final String TAG = HostPaycardThread.class.getSimpleName();

    public HostPaycardThread(@NonNull Context context) {

    }

    @Override
    public void run() {
        LogUtil.d(TAG, "\"" + TAG + "\": Thread run");
    }
}
