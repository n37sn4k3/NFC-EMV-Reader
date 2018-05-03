package com.viliyantrbr.nfcemvpayer.object;

import java.util.Date;

import io.realm.RealmObject;

public class PaycardObject extends RealmObject {
    private byte[] mHistoricalBytes = null;
    private byte[] mHiResponseLayer = null;

    private byte[] mCPse = null, mRPse = null, mCPpse = null, mRPpse = null;

    private byte[] mCFci = null, mRFci = null;

    private byte[] mCGpo = null, mRGpo = null;

    // TLV extracted data
    private byte[] mAid = null;

    // ----

    private byte[] mApplicationLabel = null;
    private byte[] mPan = null;
    private byte[] mCardholderName = null;
    private byte[] mExpirationDate = null;
    // - TLV extracted data

    // Additional data
    private Date mAddDate = null;
    // - Additional data

    public byte[] getHistoricalBytes() {
        return mHistoricalBytes;
    }
    public void setHistoricalBytes(byte[] historicalBytes) {
        this.mHistoricalBytes = historicalBytes;
    }
    public byte[] getHiResponseLayer() {
        return mHiResponseLayer;
    }
    public void setHiResponseLayer(byte[] hiResponseLayer) {
        this.mHiResponseLayer = hiResponseLayer;
    }

    public byte[] getCPse() {
        return mCPse;
    }
    public void setCPse(byte[] cPse) {
        this.mCPse = cPse;
    }
    public byte[] getRPse() {
        return mRPse;
    }
    public void setRPse(byte[] rPse) {
        this.mRPse = rPse;
    }

    public byte[] getCPpse() {
        return mCPpse;
    }
    public void setCPpse(byte[] cPpse) {
        this.mCPpse = cPpse;
    }
    public byte[] getRPpse() {
        return mRPpse;
    }
    public void setRPpse(byte[] rPpse) {
        this.mRPpse = rPpse;
    }

    public byte[] getCFci() {
        return mCFci;
    }
    public void setCFci(byte[] cFci) {
        this.mCFci = cFci;
    }
    public byte[] getRFci() {
        return mRFci;
    }
    public void setRFci(byte[] rFci) {
        this.mRFci = rFci;
    }

    public byte[] getCGpo() {
        return mCGpo;
    }
    public void setCGpo(byte[] cGpo) {
        this.mCGpo = cGpo;
    }

    public byte[] getRGpo() {
        return mRGpo;
    }
    public void setRGpo(byte[] rGpo) {
        this.mRGpo = rGpo;
    }

    // TLV extracted data
    public byte[] getAid() {
        return mAid;
    }
    public void setAid(byte[] aid) {
        this.mAid = aid;
    }

    // ----

    public byte[] getApplicationLabel() {
        return mApplicationLabel;
    }
    public void setApplicationLabel(byte[] applicationLabel) {
        this.mApplicationLabel = applicationLabel;
    }

    public byte[] getPan() {
        return mPan;
    }
    public void setPan(byte[] pan) {
        this.mPan = pan;
    }

    public byte[] getCardholderName() {
        return mCardholderName;
    }
    public void setCardholderName(byte[] cardholderName) {
        this.mCardholderName = cardholderName;
    }

    public byte[] getExpirationDate() {
        return mExpirationDate;
    }
    public void setExpirationDate(byte[] expirationDate) {
        this.mExpirationDate = expirationDate;
    }
    // - TLV extracted data

    // Additional data
    public Date getAddDate() {
        return mAddDate;
    }
    public void setAddDate(Date addDate) {
        this.mAddDate = addDate;
    }
    // - Additional data
}
