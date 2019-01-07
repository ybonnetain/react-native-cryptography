
# react-native-cryptography

Important notes: On iOS, this is not a library project - see installation instructions

- iOS `CryptoSwift`

- Android `BouncyCastle` !TODO finish md5 and sha256!

## Cryptographic functions

### Symetric ciphering

func      | ios                  | android
----------|----------------------|--------------
`AES 128` | ✓                    | ✓
`AES 192` | ✓                    | ✓
`AES 256` | ✓                    | ✓

- AES 128 (pass 16 bytes key and iv)

- AES 192 (pass 24 bytes key and iv)

- AES 256 (pass 32 bytes key and iv)

### Hashing

func      | ios                  | android
----------|----------------------|--------------
`MD5`     | ✓                    | TODO
`SHA256`  | ✓                    | TODO

## Getting started

`$ npm install react-native-cryptography --save`

### Mostly automatic installation

Android:

`$ react-native link react-native-cryptography`

iOS: See manual installation section

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `<Your Target>` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-cryptography` and add `RNCryptography.m + RNCryptography.swift`
3. Accept the bridging header creation and add `#import <React/RCTBridge.h>`
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
import React, { Component } from 'react';
import { Button, StyleSheet, Text, TextInput, View } from 'react-native';
import RNCryptography from 'react-native-cryptography';

const styles = StyleSheet.create({
  container: {
    flex: 1,
    marginTop: 50,
    justifyContent: 'flex-start',
    alignItems: 'center',
  },
});

export default class App extends Component {
  static AES_KEY_128 = 'keykeykeykeykeyk';
  static AES_IV_128 = 'drowssapdrowssap';

  encryptAES() {
    RNCryptography.encryptAES('Hello, I am the message to cipher', App.AES_KEY_128, App.AES_IV_128) 
      .then(value => console.log(value))
      .catch(err => console.error(err));
  }

  decryptAES() {
    RNCryptography.decryptAES('FNblGLlDskkoe1vLbwtTJ8xeIXmQp3udaFL8KI91hfGhYFyqA0eLcfoy3iFFw2af', App.AES_KEY_128, App.AES_IV_128) 
      .then(value => console.log(value))
      .catch(err => console.error(err));
  }

  md5() {
    RNCryptography.md5('string to digest').then(digest => console.log(digest));
  }

  sha256() {
    RNCryptography.sha256('string to digest').then(digest => console.log(digest));
  }

  render() {
    return (
      <View style={styles.container}>
        <Button onPress={this.encryptAES} title={'encrypt with AES'} />
        <Button onPress={this.decryptAES} title={'decrypt AES'} />
        <Button onPress={this.md5} title={'MD5'} />
        <Button onPress={this.sha256} title={'SHA256'} />
      </View>
    );
  }
}
```
