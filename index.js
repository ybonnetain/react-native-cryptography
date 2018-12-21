import { NativeModules } from 'react-native';

const { RNCryptography } = NativeModules;

export default {
    // AES
    encryptAES: (message, key, iv) => RNCryptography.encryptAES(message, key, iv),
    decryptAES: (cipher, key, iv) => RNCryptography.decryptAES(cipher, key, iv),
    // Hash
    md5: value => RNCryptography.md5(value),
    sha256: value => RNCryptography.sha256(value),
}
