package com.viliyantrbr.nfcemvpayer.application;

import android.app.Application;
import android.content.ComponentCallbacks2;

import com.viliyantrbr.nfcemvpayer.util.HexUtil;
import com.viliyantrbr.nfcemvpayer.util.LogUtil;

import java.io.File;
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import io.realm.Realm;
import io.realm.RealmConfiguration;

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

        // Encryption key
        byte[] encryptionKey = null;

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

            encryptionKey = secretKey.getEncoded(); // AES key length (bytes): 512 / 8 (Encoded)
        }

        if (encryptionKey != null) {
            LogUtil.d(TAG, "Encryption key: " + HexUtil.bytesToHexadecimal(encryptionKey));
        }
        // - Encryption key

        // Realm
        Realm.init(this);

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                /*.encryptionKey(encryptionKey)*/
                .build();

        if (realmConfiguration != null) {
            Realm.setDefaultConfiguration(realmConfiguration);
        }
        // - Realm
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
