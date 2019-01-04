
package com.reactlibrary;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
//import com.facebook.react.bridge.Callback;

//import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.Cipher;
//import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

//import java.util.Base64;
//import java.util.Arrays;

import org.bouncycastle.jce.provider.BouncyCastleProvider;


public class RNCryptographyModule extends ReactContextBaseJavaModule {

  static final String errorCodeAesEncrypt = "-101";
  static final String errorCodeAesDecrypt = "-102";

  private final ReactApplicationContext reactContext;

//  private static final int keyLength = 32;
//  private static final SecureRandom random = new SecureRandom();

//  private static SecretKey key;
//  private static byte[] iv;


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
  public void encryptAES(String message, String key, String iv, final Promise promise) {
    Security.addProvider(new BouncyCastleProvider());

    try {
      byte [] ciphertext = encrypt(message, key, iv);
      promise.resolve(new String(ciphertext));
    } catch(Exception e) {
      promise.reject(errorCodeAesEncrypt, "cannot encrypt (AES)", e);
    }
  }


  private static byte [] encrypt(String plaintext, String key, String iv) throws Exception {
    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");

    SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("US-ASCII"), "AES");


    cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes("US-ASCII")));

//    System.out.println(
//            Base64.getEncoder().withoutPadding()
//                    .encodeToString(key.getEncoded())
//    );
//    printByteArr(iv);
    return cipher.doFinal(plaintext.getBytes());
  }
}