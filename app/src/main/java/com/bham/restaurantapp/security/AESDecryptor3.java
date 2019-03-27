package com.bham.restaurantapp.security;

import android.util.Log;

import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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

public class AESDecryptor3 {
    private static final String TAG = "AESDecryptor3";
    private byte[] iv;
    private byte[] salt;
    private SecretKey secretKey;

    public AESDecryptor3(byte[] salt, String masterKey) {
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
    }

    public byte[] decrypt(byte[] cipherText) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(cipherText);
        int ivLength = byteBuffer.getInt();
        Log.i(TAG, "decrypt: getint " + ivLength);
        if (ivLength < 12 || ivLength >= 16)
            throw new IllegalArgumentException("invalid iv length, iv length is " + ivLength);
        byte[] iv = new byte[ivLength];
        byteBuffer.get(iv);
        byte[] encryptedPlaintext = new byte[byteBuffer.remaining()];
        byteBuffer.get(encryptedPlaintext);

        try {
            final Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new GCMParameterSpec(128, iv));
            return cipher.doFinal(encryptedPlaintext);
        } catch (NoSuchAlgorithmException e) {
            Log.i(TAG, "decrypt: no such algorithm");
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            Log.i(TAG, "decrypt: no such padding");
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            Log.i(TAG, "decrypt: invalid algorithm");
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            Log.i(TAG, "decrypt: invalid key");
            e.printStackTrace();
        } catch (BadPaddingException e) {
            Log.i(TAG, "decrypt: bad padding");
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            Log.i(TAG, "decrypt: illegal block size");
            e.printStackTrace();
        }
        return null;
    }
}
