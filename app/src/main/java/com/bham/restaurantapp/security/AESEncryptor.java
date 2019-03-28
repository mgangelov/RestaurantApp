package com.bham.restaurantapp.security;

import android.util.Log;

import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;

import static com.bham.restaurantapp.Globals.DEFAULT_KEY_GENERATION_SCHEME;

public class AESEncryptor {
    private static final String TAG = "AESEncryptor";
    private byte[] iv;

    public byte[] getIv() {
        return iv;
    }

    public byte[] getSalt() {
        return salt;
    }

    private byte[] salt;
    private SecretKey secretKey;

    public AESEncryptor(String masterKey) {
        SecureRandom secureRandom = new SecureRandom();
        salt = new byte[16];
        secureRandom.nextBytes(salt);
        PBEKeySpec pbeKeySpec = new PBEKeySpec(
                masterKey.toCharArray(),
                salt,
                1000,
                128
        );
        secretKey = null;
        try {
            SecretKeyFactory secretKeyFactory =
                    SecretKeyFactory.getInstance(DEFAULT_KEY_GENERATION_SCHEME);
            secretKey = secretKeyFactory.generateSecret(pbeKeySpec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        secureRandom = new SecureRandom();
        iv = new byte[12];
        secureRandom.nextBytes(iv);
        Log.i(TAG, "AESEncryptor: iv length is " + iv.length);
        Log.i(TAG, "AESEncryptor: Secret key length is " + secretKey.getEncoded().length);
    }

    public byte[] encrypt(byte[] plainText) {
        try {
            Log.i(TAG, "encrypt: iv length in try " + iv.length);
            final Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);
            byte[] cipherText = cipher.doFinal(plainText);
            ByteBuffer byteBuffer = ByteBuffer.allocate(
                    4 + iv.length + cipherText.length
            );
            byteBuffer.putInt(iv.length);
            byteBuffer.put(iv);
            byteBuffer.put(cipherText);
            return byteBuffer.array();
        } catch (NoSuchAlgorithmException e) {
            Log.i(TAG, "encrypt: no such algorithm");
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            Log.i(TAG, "encrypt: no such padding");
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            Log.i(TAG, "encrypt: invalid parameter");
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            Log.i(TAG, "encrypt: invalid key");
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
            Log.i(TAG, "encrypt: bad padding");
        } catch (IllegalBlockSizeException e) {
            Log.i(TAG, "encrypt: illegal block size");
            e.printStackTrace();
        }
        return null;
    }
}
