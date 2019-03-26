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
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import static com.bham.restaurantapp.Globals.DEFAULT_CIPHER_TYPE;
import static com.bham.restaurantapp.Globals.DEFAULT_KEY_GENERATION_SCHEME;
import static com.bham.restaurantapp.Globals.DEFAULT_KEY_LENGTH;
import static com.bham.restaurantapp.Globals.DEFAULT_KEY_SPEC;

public class AESDecryptor {
    private static final String TAG = "AESDecryptor";
    private IvParameterSpec ivSpec;
    private byte[] salt;
    private SecretKeySpec keySpec;

    public AESDecryptor(byte[] ivSpec, byte[] salt, byte[] masterKey) {
        if (ivSpec.length < 12 || ivSpec.length >= 16)
            throw new IllegalArgumentException("Invalid iv length");
        this.ivSpec = new IvParameterSpec(ivSpec);
        this.salt = salt;
        PBEKeySpec pbeKeySpec = new PBEKeySpec(
                new String(masterKey).toCharArray(),
                salt,
                1324,
                DEFAULT_KEY_LENGTH
        );
        try {
            SecretKeyFactory secretKeyFactory =
                    SecretKeyFactory.getInstance(DEFAULT_KEY_GENERATION_SCHEME);
            byte[] keyBytes = secretKeyFactory.generateSecret(pbeKeySpec).getEncoded();
            keySpec = new SecretKeySpec(keyBytes, DEFAULT_KEY_SPEC);
        } catch (NoSuchAlgorithmException e) {
            Log.i(TAG, "AESDecryptor: No such algorithm found.");
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            Log.i(TAG, "AESDecryptor: Invalid key spec");
            e.printStackTrace();
        }
    }

    public byte[] decryptCiphertext(byte[] ciphertext) {
        try {
            ByteBuffer byteBuffer = ByteBuffer.wrap(ciphertext);
            int ivLength = byteBuffer.getInt();
            if (ivLength < 12 || ivLength >= 16)
                throw new IllegalArgumentException("Invalid length of iv");
            byte[] iv = new byte[ivLength];
            byteBuffer.get(iv);
            byte[] encrypted_plaintext = new byte[byteBuffer.remaining()];
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_TYPE);
            GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, parameterSpec);
            return cipher.doFinal(ciphertext);
        } catch (NoSuchAlgorithmException e) {
            Log.i(TAG, "decryptCiphertext: No such algorithm");
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            Log.i(TAG, "decryptCiphertext: No such padding");
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            Log.i(TAG, "decryptCiphertext: Invalid key");
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            Log.i(TAG, "decryptCiphertext: Invalid key");
            e.printStackTrace();
        } catch (BadPaddingException e) {
            Log.i(TAG, "decryptCiphertext: Bad padding");
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            Log.i(TAG, "decryptCiphertext: Illegal block size");
            e.printStackTrace();
        }
        return null;
    }
}
