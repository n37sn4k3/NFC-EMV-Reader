package com.viliyantrbr.nfcemvpayer.thread;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;

import com.viliyantrbr.nfcemvpayer.R;
import com.viliyantrbr.nfcemvpayer.activity.HostPaycardActivity;
import com.viliyantrbr.nfcemvpayer.envr.MainEnvr;
import com.viliyantrbr.nfcemvpayer.object.PaycardObject;
import com.viliyantrbr.nfcemvpayer.util.HexUtil;
import com.viliyantrbr.nfcemvpayer.util.KeyUtil;
import com.viliyantrbr.nfcemvpayer.util.LogUtil;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class HostPaycardThread implements Runnable {
    private static final String TAG = HostPaycardThread.class.getSimpleName();

    private Realm mRealm = null;

    private PaycardObject mPaycardObject = null;

    private Context mContext;

    public HostPaycardThread(@NonNull Context context) {
        mContext = context;

        // Get encryption key
        byte[] getEncryptionKey = KeyUtil.getEncryptionKey(mContext);
        // - Get encryption key

        // Realm
        if (getEncryptionKey != null) {
            RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                    .encryptionKey(getEncryptionKey)
                    .build();

            try {
                mRealm = Realm.getInstance(realmConfiguration);
            } catch (Exception e) {
                LogUtil.e(TAG, e.getMessage());
                LogUtil.e(TAG, e.toString());

                e.printStackTrace();
            }
        } else {
            try {
                mRealm = Realm.getDefaultInstance();
            } catch (Exception e) {
                LogUtil.e(TAG, e.getMessage());
                LogUtil.e(TAG, e.toString());

                e.printStackTrace();
            }
        }
        // - Realm

        if (mRealm != null) {
            byte[] applicationPan = null;

            SharedPreferences sharedPreferences = mContext.getSharedPreferences(context.getString(R.string.pan_var_name), Context.MODE_PRIVATE);
            String applicationPanHexadecimal = sharedPreferences.getString(context.getString(R.string.pan_var_name), "N/A");

            if (!applicationPanHexadecimal.equals("N/A")) {
                applicationPan = HexUtil.hexadecimalToBytes(applicationPanHexadecimal);
            }

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
                LogUtil.w(TAG, "Realm is null");
            }
            if (mPaycardObject == null) {
                LogUtil.w(TAG, "PaycardObject is null");
            }

            this.cannotHostPaycard();
            return;
        }

        /*Intent mPaymentHostApduServiceIntent = new Intent(mContext, PaymentHostApduService.class);
        mPaymentHostApduServiceIntent.putExtra(mContext.getString(R.string.pan_var_name), mPaycardObject.getApplicationPan());

        // Service(s)
        mContext.startService(mPaymentHostApduServiceIntent);
        // - Service(s)*/
        // - Thread relative

        // Finalize
        successHostPaycard();
        // - Finalize
    }
}
