import { NativeModules } from 'react-native';

const { RNCryptography } = NativeModules;

export default {
    encryptAES: (message, key, iv) => RNCryptography.encryptAES(message, key, iv),
    decryptAES: (cipher, key, iv) => RNCryptography.encryptAES(cipher, key, iv),
    md5: value => RNCryptography.md5(value),
    md5: value => RNCryptography.sha256(value),
}
