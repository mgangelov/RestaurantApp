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
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import static com.bham.restaurantapp.Globals.DEFAULT_CIPHER_TYPE;
import static com.bham.restaurantapp.Globals.DEFAULT_KEY_GENERATION_SCHEME;
import static com.bham.restaurantapp.Globals.DEFAULT_KEY_LENGTH;
import static com.bham.restaurantapp.Globals.DEFAULT_KEY_SPEC;

public class AESEncryptor {
    private static final String TAG = "AESEncryptor";
    private IvParameterSpec ivSpec;
    private SecretKeySpec keySpec;
    private byte[] salt;

    public IvParameterSpec getIvSpec() {
        return ivSpec;
    }

    public void setIvSpec(IvParameterSpec ivSpec) {
        this.ivSpec = ivSpec;
    }

    public byte[] getSalt() {
        return salt;
    }

    public AESEncryptor(char[] masterKey) {
        SecureRandom random = new SecureRandom();
        salt = new byte[DEFAULT_KEY_LENGTH]; // Salt used to generate symmetric key
        random.nextBytes(salt);
        PBEKeySpec pbeKeySpec = new PBEKeySpec(
                masterKey,
                salt,
                1324,
                DEFAULT_KEY_LENGTH
        );
        try {
            SecretKeyFactory secretKeyFactory =
                    SecretKeyFactory.getInstance(
                            DEFAULT_KEY_GENERATION_SCHEME
                    );
            byte[] keyBytes = secretKeyFactory.generateSecret(pbeKeySpec).getEncoded();
            keySpec = new SecretKeySpec(keyBytes, DEFAULT_KEY_SPEC);
            ivSpec = addIv(12);
        } catch (NoSuchAlgorithmException e) {
            Log.i(TAG, "AESEncryptor: No such algorithm found.");
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            Log.i(TAG, "AESEncryptor: Invalid key spec.");
            e.printStackTrace();
        }
    }

    private IvParameterSpec addIv(int ivSize) {
        SecureRandom ivRandom = new SecureRandom();
        byte[] iv = new byte[ivSize];
        ivRandom.nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    public byte[] encryptPlaintext(byte[] plaintext) {
        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_TYPE);
            GCMParameterSpec parameterSpec =
                    new GCMParameterSpec(128, ivSpec.getIV());
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, parameterSpec);
            byte[] ciphertext = cipher.doFinal(plaintext);
//            return cipher.doFinal(plaintext);
            ByteBuffer byteBuffer = ByteBuffer.allocate(
                    4 + ivSpec.getIV().length + ciphertext.length
            );
            byteBuffer.putInt(ivSpec.getIV().length);
            byteBuffer.put(ivSpec.getIV());
            byteBuffer.put(ciphertext);
            return byteBuffer.array();
        } catch (NoSuchAlgorithmException e) {
            Log.i(TAG, "encryptPlaintext: No such algorithm");
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            Log.i(TAG, "encryptPlaintext: No such padding");
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            Log.i(TAG, "encryptPlaintext: Invalid algorithm parameter");
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            Log.i(TAG, "encryptPlaintext: Invalid key");
            e.printStackTrace();
        } catch (BadPaddingException e) {
            Log.i(TAG, "encryptPlaintext: Bad padding");
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            Log.i(TAG, "encryptPlaintext: Illegal block size");
            e.printStackTrace();
        }
        return null;
    }
}
