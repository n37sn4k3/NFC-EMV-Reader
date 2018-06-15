package com.viliyantrbr.nfcemvpayer.object;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;

public class PaycardObject extends RealmObject {
    private byte[] mHistoricalBytes = null;
    private byte[] mHiResponseLayer = null;

    private byte[] mCPse = null, mRPse = null, mCPpse = null, mRPpse = null;

    private byte[] mCFci = null, mRFci = null;

    // PDOL (Extracted) & PDOL Constructed
    private byte[] mPdol = null, mPdolConstructed = null;
    // - PDOL (Extracted) & PDOL Constructed

    private byte[] mCGpo = null, mRGpo = null;

    private byte[] mAflData = null;

    private RealmList<byte[]> mCAflRecordsList = null, mRAflRecordsList = null;

    private byte[] mCdol1 = null, mCdol2 = null;

    private byte[] mCLastOnlineAtcRegister = null, mRLastOnlineAtcRegister = null;

    private byte[] mCPinTryCounter = null, mRPinTryCounter = null;

    private byte[] mCAtc = null, mRAtc = null;

    private byte[] mCLogFormat = null, mRLogFormat = null;

    private RealmList<byte[]> mCLogEntryList = null, mRLogEntryList = null;

    // TLV extracted data
    private byte[] mAid = null;
    private byte[] mApplicationLabel = null; private boolean mApplicationLabelHasAscii = false;
    private byte[] mApplicationPan = null;
    private byte[] mCardholderName = null; private boolean mCardholderNameHasAscii = false;
    private byte[] mApplicationExpirationDate = null;
    // - TLV extracted data

    // Additional data
    private Date mAddDate = null;
    // - Additional data

    // ----

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

    // PDOL (Extracted) & PDOL Constructed
    public byte[] getPdol() {
        return mPdol;
    }
    public void setPdol(byte[] pdol) {
        this.mPdol = pdol;
    }
    public byte[] getPdolConstructed() {
        return mPdolConstructed;
    }
    public void setPdolConstructed(byte[] pdolConstructed) {
        this.mPdolConstructed = pdolConstructed;
    }
    // - PDOL (Extracted) & PDOL Constructed

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

    public byte[] getAflData() {
        return mAflData;
    }
    public void setAflData(byte[] aflData) {
        mAflData = aflData;
    }

    public byte[] getCdol1() {
        return mCdol1;
    }
    public void setCdol1(byte[] cdol1) {
        this.mCdol1 = cdol1;
    }

    public byte[] getCdol2() {
        return mCdol2;
    }
    public void setCdol2(byte[] cdol2) {
        this.mCdol2 = cdol2;
    }

    public RealmList<byte[]> getCAflRecordsList() {
        return mCAflRecordsList;
    }
    public void setCAflRecordsList(RealmList<byte[]> cAflRecordsList) {
        this.mCAflRecordsList = cAflRecordsList;
    }
    public RealmList<byte[]> getRAflRecordsList() {
        return mRAflRecordsList;
    }
    public void setRAflRecordsList(RealmList<byte[]> rAflRecordsList) {
        this.mRAflRecordsList = rAflRecordsList;
    }

    public byte[] getCLastOnlineAtcRegister() {
        return mCLastOnlineAtcRegister;
    }
    public void setCLastOnlineAtcRegister(byte[] cLastOnlineAtcRegister) {
        this.mCLastOnlineAtcRegister = cLastOnlineAtcRegister;
    }

    public byte[] getRLastOnlineAtcRegister() {
        return mRLastOnlineAtcRegister;
    }
    public void setRLastOnlineAtcRegister(byte[] rLastOnlineAtcRegister) {
        this.mRLastOnlineAtcRegister = rLastOnlineAtcRegister;
    }

    public byte[] getCPinTryCounter() {
        return mCPinTryCounter;
    }
    public void setCPinTryCounter(byte[] cPinTryCounter) {
        this.mCPinTryCounter = cPinTryCounter;
    }

    public byte[] getRPinTryCounter() {
        return mRPinTryCounter;
    }
    public void setRPinTryCounter(byte[] rPinTryCounter) {
        this.mRPinTryCounter = rPinTryCounter;
    }

    public byte[] getCAtc() {
        return mCAtc;
    }
    public void setCAtc(byte[] cAtc) {
        this.mCAtc = cAtc;
    }

    public byte[] getRAtc() {
        return mRAtc;
    }
    public void setRAtc(byte[] rAtc) {
        this.mRAtc = rAtc;
    }

    public byte[] getCLogFormat() {
        return mCLogFormat;
    }
    public void setCLogFormat(byte[] cLogFormat) {
        this.mCLogFormat = cLogFormat;
    }

    public byte[] getRLogFormat() {
        return mRLogFormat;
    }
    public void setRLogFormat(byte[] rLogFormat) {
        this.mRLogFormat = rLogFormat;
    }

    public RealmList<byte[]> getCLogEntryList() {
        return mCLogEntryList;
    }
    public void setCLogEntryList(RealmList<byte[]> cLogEntryList) {
        this.mCLogEntryList = cLogEntryList;
    }

    public RealmList<byte[]> getRLogEntryList() {
        return mRLogEntryList;
    }
    public void setRLogEntryList(RealmList<byte[]> rLogEntryList) {
        this.mRLogEntryList = rLogEntryList;
    }

    // TLV extracted data
    public byte[] getAid() {
        return mAid;
    }
    public void setAid(byte[] aid) {
        this.mAid = aid;
    }

    public byte[] getApplicationLabel() {
        return mApplicationLabel;
    }
    public void setApplicationLabel(byte[] applicationLabel) {
        this.mApplicationLabel = applicationLabel;
    }
    public boolean getApplicationLabelHasAscii() {
        return mApplicationLabelHasAscii;
    }
    public void setApplicationLabelHasAscii(boolean applicationLabelHasAscii) {
        this.mApplicationLabelHasAscii = applicationLabelHasAscii;
    }

    public byte[] getApplicationPan() {
        return mApplicationPan;
    }
    public void setApplicationPan(byte[] applicationPan) {
        this.mApplicationPan = applicationPan;
    }

    public byte[] getCardholderName() {
        return mCardholderName;
    }
    public void setCardholderName(byte[] cardholderName) {
        this.mCardholderName = cardholderName;
    }
    public boolean getCardholderNameHasAscii() {
        return mCardholderNameHasAscii;
    }
    public void setCardholderNameHasAscii(boolean cardholderNameHasAscii) {
        this.mCardholderNameHasAscii = cardholderNameHasAscii;
    }

    public byte[] getApplicationExpirationDate() {
        return mApplicationExpirationDate;
    }
    public void setApplicationExpirationDate(byte[] applicationExpirationDate) {
        this.mApplicationExpirationDate = applicationExpirationDate;
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
