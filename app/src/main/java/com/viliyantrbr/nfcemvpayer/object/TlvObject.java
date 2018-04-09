package com.viliyantrbr.nfcemvpayer.object;

public class TlvObject {
    public TlvObject(byte[] tlvTag, int tlvTagLength) {
        mTlvTag = tlvTag;
        mTlvTagLength = tlvTagLength;
    }

    private byte[] mTlvTag;
    private int mTlvTagLength;

    public byte[] getTlvTag() {
        return mTlvTag;
    }

    public int getTlvTagLength() {
        return mTlvTagLength;
    }
}
