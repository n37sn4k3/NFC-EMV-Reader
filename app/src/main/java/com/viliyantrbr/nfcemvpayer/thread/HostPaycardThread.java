package com.viliyantrbr.nfcemvpayer.thread;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.NonNull;

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
            long vibeTime = 400; // milliseconds

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                try {
                    vibrator.vibrate(VibrationEffect.createOneShot(vibeTime, VibrationEffect.DEFAULT_AMPLITUDE));
                } catch (Exception e) {
                    LogUtil.e(TAG, e.getMessage());
                    LogUtil.e(TAG, e.toString());

                    e.printStackTrace();
                }
            } else {
                try {
                    vibrator.vibrate(vibeTime);
                } catch (Exception e) {
                    LogUtil.e(TAG, e.getMessage());
                    LogUtil.e(TAG, e.toString());

                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void run() {
        LogUtil.d(TAG, "\"" + TAG + "\": Thread run");
    }
}
