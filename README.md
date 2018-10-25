# NFC-EMV-Reader
Android Java written application which reads and extracts contactless EMV paycard data using NFC and stores it into a Realm database.

## Get application from Google Play
<a href="https://play.google.com/store/apps/details?id=com.pxdworks.nfcemvreader" target="_blank">
    <img src="https://i.imgur.com/SodCdkm.png" height=60" />
</a>

## Screenshots

<img src="https://i.imgur.com/dvyHW5g.png" width="280" /> <img src="https://i.imgur.com/gg83c0x.png" width="280" /> <img src="https://i.imgur.com/mphSO7B.png" width="280" />

<img src="https://i.imgur.com/kSQDSbb.png" width="280" /> <img src="https://i.imgur.com/6BIDYi9.png" width="280" /> <img
src="https://i.imgur.com/GEJafBq.png" width="280" />

<img src="https://i.imgur.com/oldZPB7.png" width="280" /> <img src="https://i.imgur.com/TqGANSd.png" width="280" />

## Application Features
* Read, store & manage multiple payment cards
* View accessible application data & records
* Extract sensitive payment cards information
• View "Track 1" & "Track 2" data (which is most actively used in payment card processing)
* Take a look at the last made transactions (not extracted in the source code)

### Additional
* Encrypted multiple paycards storing (Realm)

## Supported EMV Paycards
* Mastercard (PayPass); AID: A0000000041010
* Maestro (PayPass); AID: A0000000043060
* Visa (PayWave); AID: A0000000031010
* Visa Electron (PayWave); AID: A0000000032010

###
* More cards are supported in the Google Play application

## Device Requirements
* Android 4.4+ (API 19+)
* NFC hadrware feature (Manifest required)
* NFC feature (for "Paycard read" feature)

## Technical information
* Compile SDK Version: 28
* Build Tools Version: 28.0.0-alpha3
* Min SDK Version: 19
* Target SDK Version: 28
* Gradle Version: 3.1.3
* Realm Mobile Database Version: 5.3.0
* IDE used for development: Android Studio (recommended)

## Additional repositories
No additional Ant/Gradle/Maven repositories used.

## Manifest permission(s) (Also runtime requested on 6.0+ (API 23+))
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">

    <uses-permission android:name="android.permission.NFC" />

    <uses-permission android:name="android.permission.INTERNET" /> <!-- Not used, declarated only -->

    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" /> <!-- Not used, declarated only -->
    <uses-permission android:name="android.permission.CHANGE_NETWORK_STATE" /> <!-- Not used, declarated only -->

    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" /> <!-- Not used, declarated only -->
    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" /> <!-- Not used, declarated only -->
    
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    
    <uses-permission android:name="android.permission.VIBRATE" />
</manifest>

## Manifest requirement(s)
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">
    
    <uses-feature
         android:name="android.hardware.nfc"
         android:required="true" />
</manifest>

## APK (Google Play)
https://play.google.com/store/apps/details?id=com.pxdworks.nfcemvreader

## License
This project is released under the The GNU General Public License v3.0. See "LICENSE" file for further information.
