/*
 * Copyright 2018 IP Exchange : https://ip.sx/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sx.ip.utils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.SecretKeySpec;

/**
 * Class responsible for handle security issues
 */
public class SecurityHandle {

    private final byte[] keyBytes = new byte[]{0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09,
        0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f, 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17};

    private final SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");

    private Cipher cipher;
    
    /**
    * Class constructor.
    */
    public SecurityHandle() {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        try {
            this.cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");
        } catch (NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException ex) {
            Logger.getLogger(SecurityHandle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
    * Method resposible for encrypt text entries.
    *
    * @param input
    *          Text entry to be encrypted
    * @return Map
    */
    public Map<byte[], Integer> encryption(String input) {
        byte[] inputByte = input.getBytes();
        
        int ctLength = 0;
        byte[] cipherText = null;
        Map<byte[], Integer> values = new HashMap<>();
        
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);

            cipherText = new byte[cipher.getOutputSize(inputByte.length)];
            ctLength = cipher.update(inputByte, 0, inputByte.length, cipherText, 0);
            ctLength += cipher.doFinal(cipherText, ctLength);

            values.put(cipherText, ctLength);
        } catch (InvalidKeyException | ShortBufferException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(SecurityHandle.class.getName()).log(Level.SEVERE, null, ex);
        }

        return values;
    }
    
    /**
    * Method resposible for decrypt text entries.
    *
    * @param values
    *          Map structure that holds the information to be decrypted
    * @return Map
    */
    public String decryption(Map<byte[], Integer> values) {
        byte[] plainText = null;
        byte[] cipherText = null;
        int ctLength = 0;
        
        for (byte[] securityKey : values.keySet()) {
            cipherText = securityKey;
            ctLength = values.get(securityKey);            
        }
        
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);

            plainText = new byte[cipher.getOutputSize(ctLength)];
            int ptLength = cipher.update(cipherText, 0, ctLength, plainText, 0);
            ptLength += cipher.doFinal(plainText, ptLength);
        } catch (InvalidKeyException | ShortBufferException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(SecurityHandle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new String(plainText);
    }


}
