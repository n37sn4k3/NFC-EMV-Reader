package com.viliyantrbr.nfcemvpayer.thread;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.viliyantrbr.nfcemvpayer.R;
import com.viliyantrbr.nfcemvpayer.activity.HostPaycardActivity;
import com.viliyantrbr.nfcemvpayer.envr.MainEnvr;
import com.viliyantrbr.nfcemvpayer.object.PaycardObject;
import com.viliyantrbr.nfcemvpayer.service.PaymentHostApduService;
import com.viliyantrbr.nfcemvpayer.util.LogUtil;

import io.realm.Realm;

public class HostPaycardThread implements Runnable {
    private static final String TAG = HostPaycardThread.class.getSimpleName();

    private Realm mRealm = null;

    private PaycardObject mPaycardObject = null;

    private Context mContext;

    public HostPaycardThread(@NonNull Context context, @NonNull byte[] applicationPan) {
        mContext = context;

        try {
            mRealm = Realm.getDefaultInstance();
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
            LogUtil.e(TAG, e.toString());

            e.printStackTrace();
        }

        if (mRealm != null) {
            try {
                mPaycardObject = mRealm.where(PaycardObject.class).equalTo(mContext.getString(R.string.pan_var_name), applicationPan).findFirst();
            } catch (Exception e) {
                LogUtil.e(TAG, e.getMessage());
                LogUtil.e(TAG, e.toString());

                e.printStackTrace();
            }
        }

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
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(new Intent(HostPaycardActivity.ACTION_SUCCESS_HOST_PAYCARD_BROADCAST));
    }

    private void cannotHostPaycard() {
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(new Intent(HostPaycardActivity.ACTION_CANNOT_HOST_PAYCARD_BROADCAST));
    }

    @Override
    public void run() {
        LogUtil.d(TAG, "\"" + TAG + "\": Thread run");

        // Thread relative
        if (mRealm == null || mPaycardObject == null) {
            if (mRealm == null) {
                Log.w(TAG, "Realm is null");
            }
            if (mPaycardObject == null) {
                Log.w(TAG, "PaycardObject is null");
            }

            this.cannotHostPaycard();
            return;
        }

        // - Thread relative

        // Finalize
        successHostPaycard();
        // - Finalize
    }
}
