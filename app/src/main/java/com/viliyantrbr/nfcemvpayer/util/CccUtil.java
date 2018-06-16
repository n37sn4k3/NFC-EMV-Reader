package com.viliyantrbr.nfcemvpayer.util;

import android.support.annotation.NonNull;

import com.viliyantrbr.nfcemvpayer.helper.ReadPaycardConstsHelper;

public class CccUtil {
    private static final String TAG = CccUtil.class.getName();

    private static final byte CCC_P1 = (byte) 0x8E, CCC_P2 = (byte) 0x80;

    public static boolean isCccCommand(@NonNull byte[] commandApdu) {
        return commandApdu.length > 4
                && commandApdu[0] == ReadPaycardConstsHelper.COMPUTE_CRYPTOGRAPHIC_CHECKSUM[0]
                && commandApdu[1] == ReadPaycardConstsHelper.COMPUTE_CRYPTOGRAPHIC_CHECKSUM[1]
                && commandApdu[2] == CCC_P1
                && commandApdu[3] == CCC_P2;
    }
}
