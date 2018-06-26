package com.viliyantrbr.nfcemvpayer.application;

import android.app.Application;
import android.content.ComponentCallbacks2;

import com.viliyantrbr.nfcemvpayer.util.KeyUtil;
import com.viliyantrbr.nfcemvpayer.util.LogUtil;

import java.io.File;
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import io.realm.Realm;

public class MainApplication extends Application {
    private static final String TAG = MainApplication.class.getSimpleName();

    public static String sPackageName = null;

    public static File sFilesDirMemory = null, sCacheDirMemory = null;
    public static String sFilesDirPathMemory = null, sCacheDirPathMemory = null;

    @Override
    public void onCreate() {
        sFilesDirMemory = getFilesDir();
        sFilesDirPathMemory = getFilesDir().getPath();

        sCacheDirMemory = getCacheDir();
        sCacheDirPathMemory = getCacheDir().getPath();

        super.onCreate();
        LogUtil.d(TAG, "\"" + TAG + "\": Application create");

        sPackageName = getPackageName();

        // Application relative
        // Get encryption key
        byte[] getEncryptionKey = KeyUtil.getEncryptionKey(this);
        // - Get encryption key

        if (getEncryptionKey == null) {
            boolean putEncryptionKeyOk = false;

            // Put encryption key
            byte[] putEncryptionKey = null;

            KeyGenerator aesKeyGenerator = null;
            try {
                aesKeyGenerator = KeyGenerator.getInstance("AES");
            } catch (Exception e) {
                LogUtil.e(TAG, e.getMessage());
                LogUtil.e(TAG, e.toString());

                e.printStackTrace();
            }

            if (aesKeyGenerator != null) {
                aesKeyGenerator.init(512, new SecureRandom());

                SecretKey secretKey = aesKeyGenerator.generateKey();

                putEncryptionKey = secretKey.getEncoded(); // AES key length (bytes): 512 / 8 = 64 (Encoded)
            }

            if (putEncryptionKey != null) {
                putEncryptionKeyOk = KeyUtil.putEncryptionKey(this, putEncryptionKey);
            }
            // - Put encryption key

            if (putEncryptionKeyOk) {
                getEncryptionKey = putEncryptionKey;
            }
        }

        // Realm
        Realm.init(this);

        /*RealmConfiguration realmConfiguration = null;

        if (getEncryptionKey != null) {
            realmConfiguration = new RealmConfiguration.Builder()
                    .encryptionKey(getEncryptionKey)
                    .build();
        } else {
            realmConfiguration = new RealmConfiguration.Builder()
                    .build();
        }

        if (realmConfiguration != null) {
            Realm.setDefaultConfiguration(realmConfiguration);
        }*/
        // - Realm
        // - Application relative
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        LogUtil.d(TAG, "\"" + TAG + "\": Application trim memory");

        switch (level) {
            case ComponentCallbacks2.TRIM_MEMORY_RUNNING_MODERATE:
                LogUtil.d(TAG, "Trim memory: Running moderate");

                break;
            case ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW:
                LogUtil.d(TAG, "Trim memory: Running low");

                break;
            case ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL:
                LogUtil.d(TAG, "Trim memory: Running critical");

                break;

            case ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN:
                LogUtil.d(TAG, "Trim memory: UI hidden");

                break;

            case ComponentCallbacks2.TRIM_MEMORY_BACKGROUND:
                LogUtil.d(TAG, "Trim memory: Background");

                break;
            case ComponentCallbacks2.TRIM_MEMORY_MODERATE:
                LogUtil.d(TAG, "Trim memory: Moderate");

                break;
            case ComponentCallbacks2.TRIM_MEMORY_COMPLETE:
                LogUtil.d(TAG, "Trim memory: Complete");

                if (sPackageName == null) {
                    sPackageName = getPackageName();
                }

                if (sFilesDirMemory == null) {
                    sFilesDirMemory = getFilesDir();
                }
                if (sFilesDirPathMemory == null) {
                    sFilesDirPathMemory = getFilesDir().getPath();
                }

                if (sCacheDirMemory == null) {
                    sCacheDirMemory = getCacheDir();
                }
                if (sCacheDirPathMemory == null) {
                    sCacheDirPathMemory = getCacheDir().getPath();
                }

                break;
        }
    }
}
