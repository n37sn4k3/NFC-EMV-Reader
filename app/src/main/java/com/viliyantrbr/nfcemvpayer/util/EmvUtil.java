package com.viliyantrbr.nfcemvpayer.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Arrays;

public class EmvUtil {
    private static final String TAG = EmvUtil.class.getName();

    // APDU (Application Protocol Data Unit) - Get response status words (bytes)
    @Nullable
    public static byte[] getSwBytes(@NonNull byte[] bytesIn) {
        // Returning result
        byte[] result = null;
        // - Returning result

        if (bytesIn.length < 2) {
            try {
                throw new Exception("Invalid response bytes");
            } catch (Exception e) {
                LogUtil.e(TAG, e.getMessage());
                LogUtil.e(TAG, e.toString());

                e.printStackTrace();
            }
        }

        try {
            result = Arrays.copyOfRange(
                    bytesIn, // Original
                    bytesIn.length - 2, // From (to retrieve SW1 & SW2)
                    bytesIn.length // To
            );
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
            LogUtil.e(TAG, e.toString());

            e.printStackTrace();
        }

        return result;
    }
    // - APDU (Application Protocol Data Unit) - Get response status words (bytes)

    // APDU (Application Protocol Data Unit) - Get response status words (hexadecimal)
    @Nullable
    public static String getSwHexadecimal(@NonNull byte[] bytesIn) {
        // Returning result
        String result = null;
        // - Returning result

        byte[] bytesResult = getSwBytes(bytesIn);

        if (bytesResult != null) {
            try {
                result = HexUtil.bytesToHexadecimal(bytesResult);
            } catch (Exception e) {
                LogUtil.e(TAG, e.getMessage());
                LogUtil.e(TAG, e.toString());

                e.printStackTrace();
            }
        }

        return result;
    }
    // - APDU (Application Protocol Data Unit) - Get response status words (hexadecimal)

    // APDU (Application Protocol Data Unit) - Check if response succeed
    @Nullable
    public static boolean isOk(@NonNull byte[] bytesIn) {
        // Returning result
        boolean result = false;
        // - Returning result

        byte[] bytesResult = getSwBytes(bytesIn);

        if (bytesResult != null) {
            try {
                result = Arrays.equals(
                        bytesResult,
                        new byte[]{
                                (byte) 0x90,
                                (byte) 0x00
                        }
                );
            } catch (Exception e) {
                LogUtil.e(TAG, e.getMessage());
                LogUtil.e(TAG, e.toString());

                e.printStackTrace();
            }
        }

        return result;
    }
    // - APDU (Application Protocol Data Unit) - Check if response succeed
}
