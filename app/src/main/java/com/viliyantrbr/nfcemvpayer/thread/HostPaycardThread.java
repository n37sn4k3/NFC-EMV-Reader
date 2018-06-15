package com.viliyantrbr.nfcemvpayer.thread;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;

import com.viliyantrbr.nfcemvpayer.activity.HostPaycardActivity;
import com.viliyantrbr.nfcemvpayer.envr.MainEnvr;
import com.viliyantrbr.nfcemvpayer.util.LogUtil;

public class HostPaycardThread implements Runnable {
    private static final String TAG = HostPaycardThread.class.getSimpleName();

    private Context mContext;

    public HostPaycardThread(@NonNull Context context) {
        mContext = context;

        Vibrator vibrator = null;
        try {
            vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
            LogUtil.e(TAG, e.toString());

            e.printStackTrace();
        }

        if (vibrator != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                try {
                    vibrator.vibrate(VibrationEffect.createOneShot(MainEnvr.HOST_PAYCARD_VIBE_TIME, VibrationEffect.DEFAULT_AMPLITUDE));
                } catch (Exception e) {
                    LogUtil.e(TAG, e.getMessage());
                    LogUtil.e(TAG, e.toString());

                    e.printStackTrace();
                }
            } else {
                try {
                    vibrator.vibrate(MainEnvr.HOST_PAYCARD_VIBE_TIME);
                } catch (Exception e) {
                    LogUtil.e(TAG, e.getMessage());
                    LogUtil.e(TAG, e.toString());

                    e.printStackTrace();
                }
            }
        }
    }

    private void successHostPaycard() {
        Intent intent = new Intent(HostPaycardActivity.ACTION_SUCCESS_HOST_PAYCARD_BROADCAST);

        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

    private void cannotHostPaycard() {
        Intent intent = new Intent(HostPaycardActivity.ACTION_CANNOT_HOST_PAYCARD_BROADCAST);

        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

    @Override
    public void run() {
        LogUtil.d(TAG, "\"" + TAG + "\": Thread run");

        // Finalize
        successHostPaycard();
        // - Finalize
    }
}
