# NFC-EMV-Payer
(Unreleased, In Development) Android Java written application which reads and extracts contactless EMV paycard data, stores it into an encrypted Realm database, and emulates it to a payment terminal.

## Application Features
* Paycard read **(In Development)**
* Paycard host (emulation) **(In Development)**
* Multiple paycards storing
* Encrypted paycards storing

## Supported EMV Paycards
* Mastercard (PayPass); AID: A0000000041010
* Maestro (PayPass); AID: A0000000043060
* Visa (PayWave); AID: A0000000031010
* Visa Electron (PayWave); AID: A0000000032010

## Device Requirements
* Android 4.4+ (API 19+)
* NFC hadrware (Manifest required)
* NFC feature (for "Paycard read" feature)
* HCE feature (for "Paycard host (emulation)" feature)

## Technical information
* Compile SDK Version: 27
* Build Tools Version: 27.1.1
* Min SDK Version: 19
* Target SDK Version: 27
* Realm Mobile Database Version: 5.0.0
* IDE used for development: Android Studio (recommended)

## License
This project is released under the The GNU General Public License v3.0. See "LICENSE" file for further information.
