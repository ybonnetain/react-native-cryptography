import CryptoSwift


@objc(RNCryptography)

class RNCryptography : NSObject {
    
    let errorDomain : String = "RNCryptographyErrorDomain"
    let errorCodeAesEncrypt : Int = -101
    let errorCodeAesDecrypt : Int = -102
    
    
    @objc(md5:resolver:rejecter:)
    func md5(_ value: String,
             resolver resolve: @escaping RCTPromiseResolveBlock,
             rejecter reject: @escaping RCTPromiseRejectBlock) -> Void {
        
        let data = value.bytes
        resolve(data.md5().toHexString())
    }
    
    
    @objc(sha256:resolver:rejecter:)
    func sha256(_ value: String,
                resolver resolve: @escaping RCTPromiseResolveBlock,
                rejecter reject: @escaping RCTPromiseRejectBlock) -> Void {
        
        let data = value.bytes
        resolve(data.sha256().toHexString())
    }
    
    
    @objc(sha512:resolver:rejecter:)
    func sha512(_ value: String,
                resolver resolve: @escaping RCTPromiseResolveBlock,
                rejecter reject: @escaping RCTPromiseRejectBlock) -> Void {
        
        let data = value.bytes
        resolve(data.sha512().toHexString())
    }
    
    
    @objc(encryptAES:key:iv:resolver:rejecter:)
    func encryptAES (_ message : String,
                     key: String,
                     iv: String,
                     resolver resolve: @escaping RCTPromiseResolveBlock,
                     rejecter reject: @escaping RCTPromiseRejectBlock) -> Void {
        do {
            let aes = try AES(key: key, iv: iv)
            let ciphertext = try aes.encrypt(Array(message.utf8))
            resolve(ciphertext.toBase64())
        } catch {
            let error = NSError(domain: errorDomain, code: errorCodeAesEncrypt, userInfo: nil)
            reject(String(errorCodeAesEncrypt), "cannot encrypt (AES)", error)
        }
    }
    
    
    @objc(decryptAES:key:iv:resolver:rejecter:)
    func decryptAES (_ message : String,
                     key: String,
                     iv: String,
                     resolver resolve: @escaping RCTPromiseResolveBlock,
                     rejecter reject: @escaping RCTPromiseRejectBlock) -> Void {
        do {
            let aes = try AES(key: key, iv: iv)
            let decrypted = try message.decryptBase64ToString(cipher: aes)
            resolve(decrypted)
        } catch {
            let error = NSError(domain: errorDomain, code: errorCodeAesDecrypt, userInfo: nil)
            reject(String(errorCodeAesDecrypt), "cannot decrypt (AES)", error)
        }
    }
}
