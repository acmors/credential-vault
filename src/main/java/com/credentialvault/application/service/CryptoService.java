package com.credentialvault.application.service;

import com.credentialvault.common.exceptions.business.EmailAlreadyExistsException;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class CryptoService {
    private static final String ALGORITHM = "AES";
    private static final String SECRET = "1234567890123456";

    public String encryptPassword(String password){
        if (password == null) {
            throw new IllegalArgumentException("Password null");
        }

        if (password.isBlank()) {
            throw new IllegalArgumentException("Password vazia");
        }

        try{
            password = password.trim();

            SecretKeySpec key = new SecretKeySpec(SECRET.getBytes(StandardCharsets.UTF_8), ALGORITHM);

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] encrypted = cipher.doFinal(password.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(encrypted);

        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Erro ao criptografar", e);
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
            throw new EmailAlreadyExistsException("falhou na descrito ");
        }
    }
}
