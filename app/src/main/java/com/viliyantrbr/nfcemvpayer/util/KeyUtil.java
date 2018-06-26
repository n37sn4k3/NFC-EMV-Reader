package com.viliyantrbr.nfcemvpayer.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.viliyantrbr.nfcemvpayer.R;

import java.util.Arrays;

public class KeyUtil {
    private static final String TAG = KeyUtil.class.getSimpleName();

    /*
     * FIXME: ENCRYPTION KEY STORING
     * SO READ CAREFULLY, IMPORTANT;
     *
     * In current Android application:
     * - The encryption key is stored in the Android shared preferences (privately)
     * - The encryption key is stored raw in hexadecimal format as string
     *
     * This method is not recommended mainly because:
     * - Application shared preferences can be accessed with su (root) binary
     *
     * Another important advice(s):
     * - Making an application backup will make your secret credentials or keys visible so they must be stored encrypted
     *
     * If you store sensitive data like this in raw format (this will increase storing security a little bit):
     * - Make sure your application "android:installLocation" is set to "internalOnly"
     * - Make sure your application "android:allowBackup" is set to "false"
     *
     * In conclusion:
     * - Do not use this method for storing secret credentials or keys that you need to have in your application
     */

    public static byte[] getEncryptionKey(@NonNull Context context) {
        // Returning result
        byte[] result = null;
        // - Returning result

        SharedPreferences sharedPreferences = null;
        try {
            sharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
            LogUtil.e(TAG, e.toString());

            e.printStackTrace();
        }

        if (sharedPreferences != null) {
            String encryptionKeyHexadecimal = null;
            try {
                encryptionKeyHexadecimal = sharedPreferences.getString("Key", "Key");
            } catch (Exception e) {
                LogUtil.e(TAG, e.getMessage());
                LogUtil.e(TAG, e.toString());

                e.printStackTrace();
            }

            if (encryptionKeyHexadecimal != null && !encryptionKeyHexadecimal.equals("Key")) {
                result = HexUtil.hexadecimalToBytes(encryptionKeyHexadecimal);
            }
        }

        return result;
    }

    public static boolean putEncryptionKey(@NonNull Context context, @NonNull byte[] encryptionKey) {
        // Returning result
        boolean result = false;
        // - Returning result

        String encryptionKeyHexadecimal = HexUtil.bytesToHexadecimal(encryptionKey);

        if (encryptionKeyHexadecimal != null) {
            SharedPreferences sharedPreferences = null;
            try {
                sharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
            } catch (Exception e) {
                LogUtil.e(TAG, e.getMessage());
                LogUtil.e(TAG, e.toString());

                e.printStackTrace();
            }

            if (sharedPreferences != null) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Key", encryptionKeyHexadecimal);
                editor.apply();

                if (sharedPreferences.contains("Key")) {
                    String getEncryptionKeyHexadecimal = null;
                    try {
                        getEncryptionKeyHexadecimal = sharedPreferences.getString("Key", "Key");
                    } catch (Exception e) {
                        LogUtil.e(TAG, e.getMessage());
                        LogUtil.e(TAG, e.toString());

                        e.printStackTrace();
                    }

                    if (getEncryptionKeyHexadecimal != null) {
                        byte[] getEncryptionKey = HexUtil.hexadecimalToBytes(getEncryptionKeyHexadecimal);

                        if (getEncryptionKey != null && Arrays.equals(encryptionKey, getEncryptionKey)) {
                            result = true;
                        }
                    }
                }
            }
        }

        return result;
    }
}
