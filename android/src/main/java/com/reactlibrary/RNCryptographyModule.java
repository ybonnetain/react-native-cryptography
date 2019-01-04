
package com.reactlibrary;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.security.Security;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.crypto.digests.MD5Digest;


public class RNCryptographyModule extends ReactContextBaseJavaModule {

  static final String errorCodeAesEncrypt = "-101";
  static final String errorCodeAesDecrypt = "-102";

  private final ReactApplicationContext reactContext;


  public RNCryptographyModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }


  @Override
  public String getName() {
    return "RNCryptography";
  }


  @ReactMethod
  @SuppressWarnings("unused")
  public void md5(String input, final Promise promise) {
    MD5Digest md5 = new MD5Digest();
    md5.update(input.getBytes(), 0, input.getBytes().length);

    byte[] digest = new byte[md5.getDigestSize()];
    md5.doFinal(digest, 0);

    // TODO new String(Hex.encode(digest)));

    promise.resolve(digest.toString());
  }


  @ReactMethod
  @SuppressWarnings("unused")
  public void encryptAES(String message, String key, String iv, final Promise promise) {
    Security.addProvider(new BouncyCastleProvider());

    try {
      byte [] ciphertext = encrypt(message, key, iv);
      String base64String = new String(Base64.encodeBase64(ciphertext));
      promise.resolve(base64String);
    } catch(Exception e) {
      promise.reject(errorCodeAesEncrypt, "cannot encrypt (AES)", e);
    }
  }


  @ReactMethod
  @SuppressWarnings("unused")
  public void decryptAES(String cipher, String key, String iv, final Promise promise) {
    try {
      Base64 decoder = new Base64();
      byte[] cipheredText = decoder.decode(cipher.getBytes());
      promise.resolve(decrypt(cipheredText, key, iv));
    } catch(Exception e) {
      promise.reject(errorCodeAesDecrypt, "cannot decrypt (AES)", e);
    }
  }


  private static byte[] encrypt(String plaintext, String key, String iv) throws Exception {
    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
    SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("US-ASCII"), "AES");
    cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes("US-ASCII")));

    return cipher.doFinal(plaintext.getBytes());
  }


  private static String decrypt(byte[] ciphertext, String key, String iv) throws Exception {
    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
    SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("US-ASCII"), "AES");
    cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes("US-ASCII")));
    return new String(cipher.doFinal(ciphertext));
  }
}