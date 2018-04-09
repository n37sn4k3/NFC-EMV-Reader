package com.viliyantrbr.nfcemvpayer.helper;

public class ReadPaycardHelper {
    private static final String TAG = ReadPaycardHelper.class.getSimpleName();

    // EMV Command(s) (First Bytes - Cla, Ins)
    /*'00' 'A4' SELECT*/

    public static final byte[] SELECT = {
            (byte) 0x00,
            (byte) 0xA4
    };

    // ----

    /*'80' '2A' COMPUTE CRYPTOGRAPHIC CHECKSUM
    '80' 'EA' EXCHANGE RELAY RESISTANCE DATA
    '80' 'AE' GENERATE AC
    '80' 'CA' GET DATA
    '80' 'A8' GET PROCESSING OPTIONS
    '80' 'DA' PUT DATA
    '00' 'B2' READ RECORD
    '80' 'D0' RECOVER AC*/

    public static final byte[] COMPUTE_CRYPTOGRAPHIC_CHECKSUM = {
            (byte) 0x80,
            (byte) 0x2A
    }, EXCHANGE_RELAY_RESISTANCE_DATA = {
            (byte) 0x80,
            (byte) 0xEA
    }, GENERATE_AC = {
            (byte) 0x80,
            (byte) 0xAE
    }, GET_DATA = {
            (byte) 0x80,
            (byte) 0xCA
    }, GET_PROCESSING_OPTIONS = {
            (byte) 0x80,
            (byte) 0xA8
    }, PUT_DATA = {
            (byte) 0x80,
            (byte) 0xDA
    }, READ_RECORD = {
            (byte) 0x00,
            (byte) 0xB2
    }, RECOVER_AC = {
            (byte) 0x80,
            (byte) 0xD0
    };
    // - Emv Command(s) (First Bytes - Cla, Ins)

    // TLV Tag(s) (Constant(s))
    public static final byte[] AID_TLV_TAG = {
            (byte) 0x4F
    }; // AID (Application Identifier)

    public static final byte[] PDOL_TLV_TAG = {
            (byte) 0x9F,
            (byte) 0x38
    }; // PDOL (Processing Options Data Object List)

    public static final byte[] CDOL_1_TLV_TAG = {
            (byte) 0x8C
    }; // CDOL1 (Card Risk Management Data Object List 1)

    public static final byte[] CDOL_2_TLV_TAG = {
            (byte) 0x8D
    }; // CDOL2 (Card Risk Management Data Object List 2)

    public static final byte[] GPO_RMT1_TLV_TAG = {
            (byte) 0x80
    }; // GPO Response message template 1

    public static final byte[] GPO_RMT2_TLV_TAG = {
            (byte) 0x77
    }; // GPO Response message template 2

    public static final byte[] AFL_TLV_TAG = {
            (byte) 0x94
    }; // AFL (Application File Locator)

    public static final byte[] LAST_ONLINE_ATC_REGISTER_TLV_TAG = {
            (byte) 0x9F,
            (byte) 0x13
    }; // Last Online ATC (Application Transaction Counter) Register

    public static final byte[] PIN_TRY_COUNTER_TLV_TAG = {
            (byte) 0x9F,
            (byte) 0x17
    }; // PIN (Personal Identification Number) Try Counter

    public static final byte[] ATC_TLV_TAG = {
            (byte) 0x9F,
            (byte) 0x36
    }; // ATC (Application Transaction Counter)

    // ATC & UN
    public static final byte[] P_UN_ATC_TRACK1_TLV_TAG = {
            (byte) 0x9F,
            (byte) 0x63
    }, N_ATC_TRACK1_TLV_TAG = {
            (byte) 0x9F,
            (byte) 0x64
    }; // PayPass

    public static final byte[] P_UN_ATC_TRACK2_TLV_TAG = {
            (byte) 0x9F,
            (byte) 0x66
    }, N_ATC_TRACK2_TLV_TAG = {
            (byte) 0x9F,
            (byte) 0x67
    }; // PayPass
    // - ATC & UN

    // Log Entry
    public static final byte[] PAYPASS_LOG_ENTRY_TLV_TAG = {
            (byte) 0x9F,
            (byte) 0x4D
    }, PAYWAVE_LOG_ENTRY_TLV_TAG = {
            (byte) 0x9F,
            (byte) 0x60
    }; // PayPass, PayWave
    // - Log Entry

    // Log Format
    public static final byte[] PAYPASS_LOG_FORMAT_TLV_TAG = {
            (byte) 0x9F,
            (byte) 0x4F
    }, PAYWAVE_LOG_FORMAT_TLV_TAG = {
            (byte) 0x9F,
            (byte) 0x80
    }; // PayPass, PayWave
    // - Log Format

    public static final byte[] APPLICATION_LABEL_TLV_TAG = {
            (byte) 0x50
    }; // Application Label

    public static final byte[] APPLICATION_PAN_TLV_TAG = {
            (byte) 0x5A
    }; // Application PAN (Primary Account Number)

    public static final byte[] CARDHOLDER_NAME_TLV_TAG = {
            (byte) 0x5F,
            (byte) 0x20
    }; // Cardholder Name

    public static final byte[] APPLICATION_EXPIRATION_DATE_TLV_TAG = {
            (byte) 0x5F,
            (byte) 0x24
    }; // Application Expiration Date

    public static final byte[] TTQ_TLV_TAG = {
            (byte) 0x9F,
            (byte) 0x66
    }; // Terminal Transaction Qualifiers (TTQ)

    public static final byte[] AMOUNT_AUTHORISED_TLV_TAG = {
            (byte) 0x9F,
            (byte) 0x02
    }; // Amount, Authorised (Numeric)

    public static final byte[] AMOUNT_OTHER_TLV_TAG = {
            (byte) 0x9F,
            (byte) 0x03
    }; // Amount, Other (Numeric)

    public static final byte[] APPLICATION_IDENTIFIER_TLV_TAG = {
            (byte) 0x9F,
            (byte) 0x06
    }; // Application Identifier

    public static final byte[] APPLICATION_VERSION_NUMBER_TLV_TAG = {
            (byte) 0x9F,
            (byte) 0x09
    }; // Application Version Number

    public static final byte[] TERMINAL_COUNTRY_CODE_TLV_TAG = {
            (byte) 0x9F,
            (byte) 0x1A
    }; // Terminal Country Code

    public static final byte[] TERMINAL_FLOOR_LIMIT_TLV_TAG = {
            (byte) 0x9F,
            (byte) 0x1B
    }; // Terminal Floor Limit

    public static final byte[] TRANSACTION_CURRENCY_CODE_TLV_TAG = {
            (byte) 0x5F,
            (byte) 0x2A
    }; // Transaction Currency Code

    public static final byte[] TVR_TLV_TAG = {
            (byte) 0x95
    }; // Transaction Verification Results (TVR)

    public static final byte[] TRANSACTION_DATE_TLV_TAG = {
            (byte) 0x9A
    }; // Transaction Date

    public static final byte[] TRANSACTION_STATUS_INFORMATION_TLV_TAG = {
            (byte) 0x9B
    }; // Transaction Status Information

    public static final byte[] TRANSACTION_TYPE_TLV_TAG = {
            (byte) 0x9C
    }; // Transaction Type

    public static final byte[] TRANSACTION_TIME_TLV_TAG = {
            (byte) 0x9F,
            (byte) 0x21
    }; // Transaction Time

    public static final byte[] CRYPTOGRAM_INFORMATION_DATA_TLV_TAG = {
            (byte) 0x9F,
            (byte) 0x27
    }; // Cryptogram Information Data

    public static final byte[] UN_TLV_TAG = {
            (byte) 0x9F,
            (byte) 0x37
    }; // Unpredictable Number (UN)

    public static final byte[] MERCHANT_NAME_TLV_TAG = {
            (byte) 0x9F,
            (byte) 0x4E
    }; // Merchant Name
    // - TLV Tag(s) (Constant(s))
}
