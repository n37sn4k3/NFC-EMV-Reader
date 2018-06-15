package com.viliyantrbr.nfcemvpayer.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class HexUtil {
    private static final String TAG = HexUtil.class.getName();

    @Nullable
    public static String bytesToHexadecimal(@NonNull byte[] bytesIn) {
        // Returning result
        String result = null;
        // - Returning result

        StringBuilder stringBuilder = null;
        try {
            stringBuilder = new StringBuilder();
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
            LogUtil.e(TAG, e.toString());

            e.printStackTrace();
        }

        if (stringBuilder != null) {
            for (byte byteOut : bytesIn) {
                try {
                    stringBuilder.append(String.format("%02X", byteOut));
                } catch (Exception e) {
                    LogUtil.e(TAG, e.getMessage());
                    LogUtil.e(TAG, e.toString());

                    e.printStackTrace();
                }
            }

            try {
                result = stringBuilder.toString();
            } catch (Exception e) {
                LogUtil.e(TAG, e.getMessage());
                LogUtil.e(TAG, e.toString());

                e.printStackTrace();
            }
        }

        return result;
    }

    @Nullable
    public static byte[] hexadecimalToBytes(@NonNull String hexadecimal) {
        // Returning result
        byte[] result = null;
        // - Returning result

        try {
            result = new byte[hexadecimal.length() / 2];
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
            LogUtil.e(TAG, e.toString());

            e.printStackTrace();
        }

        if (result != null) {
            for (int i = 0; i < hexadecimal.length(); i += 2) {
                try {
                    result[i / 2] = (byte) ((Character.digit(hexadecimal.charAt(i), 16) << 4) + Character.digit(hexadecimal.charAt(i + 1), 16));
                } catch (Exception e) {
                    LogUtil.e(TAG, e.getMessage());
                    LogUtil.e(TAG, e.toString());

                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    @Nullable
    public static String hexadecimalToAscii(@NonNull String hexadecimal) {
        // Returning result
        String result = null;
        // - Returning result

        StringBuilder stringBuilder = null;
        try {
            stringBuilder = new StringBuilder();
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
            LogUtil.e(TAG, e.toString());

            e.printStackTrace();
        }

        if (stringBuilder != null) {
            for (int i = 0; i < hexadecimal.length(); i += 2) {
                try {
                    stringBuilder.append((char) Integer.parseInt(hexadecimal.substring(i, i + 2), 16));
                } catch (Exception e) {
                    LogUtil.e(TAG, e.getMessage());
                    LogUtil.e(TAG, e.toString());

                    e.printStackTrace();
                }
            }

            try {
                result = stringBuilder.toString();
            } catch (Exception e) {
                LogUtil.e(TAG, e.getMessage());
                LogUtil.e(TAG, e.toString());

                e.printStackTrace();
            }
        }

        return result;
    }

    @Nullable
    public static String bytesToAscii(@NonNull byte[] bytesIn) {
        // Returning result
        String result = null;
        // - Returning result

        String hexadecimal = bytesToHexadecimal(bytesIn);

        if (hexadecimal != null) {
            result = hexadecimalToAscii(hexadecimal);
        }

        return result;
    }
}
