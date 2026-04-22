package com.credentialvault.service;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Service
public class CryptoService {
    private static final String ALGORITHM = "AES";
    private static final String SECRET = "1234567890123456";

    public String encryptPassword(String password){
        try{
            SecretKeySpec key = new SecretKeySpec(SECRET.getBytes(), ALGORITHM);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] encrypted = cipher.doFinal(password.getBytes());

            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e){
            throw new RuntimeException();
        }
    }

    public String decryptPassword(String password){
        try{
            SecretKeySpec key = new SecretKeySpec(SECRET.getBytes(), ALGORITHM);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);

            byte[] decoded = Base64.getDecoder().decode(password);

            return new String(cipher.doFinal(decoded));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
