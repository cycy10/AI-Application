package org.backend.contentgenerator.Security;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class KeyGenerationclass {
    public static void main(String[] args){
        try{
            String secretKey=null;
            KeyGenerator key = null;
            key = KeyGenerator.getInstance("HmacSHA256");
            SecretKey s = key.generateKey();
            secretKey = Base64.getEncoder().encodeToString(s.getEncoded());
            System.out.println(secretKey);
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

}
