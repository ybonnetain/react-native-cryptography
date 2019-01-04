
# react-native-cryptography

Important notes: On iOS, this is not a library project - see installation instructions

- iOS: It uses `CryptoSwift`

- Android: !TODO! It uses `BouncyCastle` !TODO!

## Cryptographic functions

### Symetric ciphering

- AES 128 (pass 16 bytes key and iv)

- AES 192 (pass 24 bytes key and iv)

- AES 256 (pass 32 bytes key and iv)


### Hashing

- MD5
- SHA256

## Getting started

`$ npm install react-native-cryptography --save`

### Mostly automatic installation

Android:

`$ react-native link react-native-cryptography`

iOS: See manual installation section

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `<Your Target>` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-cryptography` and add `RNCryptography.m + RNCryptography.swift + RNCryptography-Bridging-Header.h`
3. Modify `RNCryptography-Bridging-Header.h` name to `<Your Target>-Bridging-Header.h`
4. Add a `Podfile` in the sources root dir and add `pod 'CryptoSwift'` to it.
5. `cd ios/ && pod install`
6. Reopen xcode using your app workspace

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNCryptographyPackage;` to the imports at the top of the file
  - Add `new RNCryptographyPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-cryptography'
  	project(':react-native-cryptography').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-cryptography/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-cryptography')
  	```

## Usage
```javascript
import RNCryptography from 'react-native-cryptography';

// Advanced Encryption Standard

// For AES 128 -> 16 bytes key and iv
// For AES 192 -> 24 bytes key and iv
// For AES 256 -> 32 bytes key and iv

// AES: encrypt
RNCryptography.encryptAES(
  'Hello, I am a message that needs to be encrypted. bye',
  'keykeykeykeykeyk', // key
  'drowssapdrowssap' // iv
)
.then(value => console.log(value))
.catch(err => console.error(err));

// AES: decrypt
RNCryptography.decryptAES(
  'YAxm2nQCUwFCc3gK7zDTcRmK8uq3NfLZi2qT7hnl0l369XjDNxsO+qAQe8t3B4lxewCb5X6GNPvfrd2vlf689w==',
  'keykeykeykeykeyk', // key
  'drowssapdrowssap' // iv
)
.then(value => console.log(value))
.catch(err => console.error(err));


// Hash

// MD5

RNCryptography.md5('string needing hash').then(digest => console.log(digest));

// SHA256

RNCryptography.sha256('string needing hash').then(digest => console.log(digest));



```
  
