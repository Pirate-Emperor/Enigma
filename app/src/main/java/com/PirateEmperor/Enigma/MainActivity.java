package com.PirateEmperor.Enigma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity {

    // This activity contains all functions//

    // aes//

    // rsa//
    static Key publicKey = null;
    static Key privateKey = null;

    /// encryption
    public byte[] keygeneratorAes() {
        byte[] key = new byte[0];
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            SecretKey secretKey = keyGenerator.generateKey();
            key = secretKey.getEncoded();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return key;
    }

    public static byte[] generateKeyAes(String password) throws Exception {
        // SecretKeySpec key = generateKey(password_text);
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");// for using hash function SHA-256
        byte[] bytes = password.getBytes(StandardCharsets.UTF_8);
        digest.update(bytes, 0, bytes.length);// process kr bytes array ko
        byte[] key = digest.digest();//// Completes the hash computation by performing final operations such as
                                     //// padding.
        // SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        return key;
    }

    public static String encryptndecryptAes(byte[] plaintext, int mode, String pass) throws Exception {

        byte[] key;

        key = generateKeyAes(pass);

        if (mode == 2) {
            plaintext = hex2byte(plaintext);
        }

        // byte[] IV = new byte[16];

        // SecureRandom random = new SecureRandom();
        // random.nextBytes(IV);

        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        // IvParameterSpec ivSpec = new IvParameterSpec(IV);
        cipher.init(mode, keySpec);
        byte[] cipherText = cipher.doFinal(plaintext);

        // String encryptedString=Base64.encodeToString(cipherText, Base64.DEFAULT);

        String result;
        if (mode == 1) {
            result = byte2hex(cipherText);

        } else {
            String encryptText = new String(cipherText);

            result = encryptText;

        }

        return result;

    }

    public static String byte2hex(byte[] bArr) {
        String str = "";
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b & 255);
            str = hexString.length() == 1 ? str + "0" + hexString : str + hexString;
        }
        return str.toUpperCase();
    }

    private static String convertByteToHex(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            sb.append(Integer.toString((b & 255) + 256, 16).substring(1));
        }
        return sb.toString().toUpperCase();
    }

    public static byte[] hex2byte(byte[] bArr) {
        if (bArr.length % 2 == 0) {
            byte[] bArr2 = new byte[(bArr.length / 2)];
            for (int i = 0; i < bArr.length; i += 2) {
                bArr2[i / 2] = (byte) Integer.parseInt(new String(bArr, i, 2), 16);
            }
            return bArr2;
        }
        throw new IllegalArgumentException("hello");
    }

    static void generateRsakey() throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.genKeyPair();
        publicKey = kp.getPublic();
        privateKey = kp.getPrivate();

    }

    /*
     * public void asymmetricRsa(byte[] targetString,int mode) throws Exception{
     * Key keyvalue;
     * if(mode==1) {
     * generateRsakey();
     * keyvalue=privateKey;
     * }else {
     * keyvalue=publicKey;
     * }
     * // if (password.getText().toString().trim().isEmpty()) {
     * // key = keygeneratorAes();
     * // } else {
     * // // key= keygenerator();
     * // key = generateKeyAes(password.toString().trim());
     * // }
     * 
     * // Encode the original data with the RSA private key
     * Cipher c = Cipher.getInstance("RSA");
     * c.init(mode, keyvalue);
     * byte[] rsaencodedBytes = c.doFinal(targetString);
     * 
     * String encryptText = new String(rsaencodedBytes);
     * 
     * String encryptedString=Base64.encodeToString(rsaencodedBytes,
     * Base64.DEFAULT);
     * 
     * if(mode==1){
     * //output//encrypted
     * // tv1.setText(encryptedString);
     * }else {
     * // tv2.setText(encryptText);
     * //output//decrypted
     * publicKey=null;
     * privateKey=null;
     * }
     * 
     * }
     * 
     * 
     */
    public static String dititaldecryptnencrypt(byte[] encryptedMessageHash, byte[] input, int mode) throws Exception {
        byte[] messageBytes = input;// et.getText().toString().getBytes();

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] newMessageHash = md.digest(messageBytes);

        Key keyvalue;
        if (mode == 1) {

            generateRsakey();
            keyvalue = privateKey;
            encryptedMessageHash = newMessageHash;
        } else {
            keyvalue = publicKey;
        }
        // byte[] encryptedMessageHash =
        // signature;//Files.readAllBytes(Paths.get("digital_signature_1"));

        // byte[] messageBytes = targetString.getBytes();

        /// byte[] decryptedMessageHash = new byte[0];
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(mode, keyvalue);
        byte[] decryptedMessageHash = cipher.doFinal(encryptedMessageHash);
        // Next, we generate a new message hash from the received message:

        // And finally, we check if the newly generated message hash matches the
        // decrypted one:
        String encryptedString = Base64.encodeToString(decryptedMessageHash, Base64.DEFAULT);
        String result;
        if (mode == 2) {
            boolean isCorrect = Arrays.equals(decryptedMessageHash, newMessageHash);
            if (isCorrect) {
                result = encryptedString + "\nVerification: PASS";

            } else {
                result = "Verification: FAIL";
            }

        } else {

            result = encryptedString;
        }
        return result;
    }

    public static String hashingmd5(String targetString) throws Exception {

        MessageDigest messageDigest;
        byte[] digest;

        messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.reset();
        messageDigest.update(targetString.getBytes(StandardCharsets.UTF_8));
        digest = messageDigest.digest();

        BigInteger bigInt = new BigInteger(1, digest);
        String md5Hex = bigInt.toString(16);

        while (md5Hex.length() < 32) {
            md5Hex = "0" + md5Hex;
        }

        return md5Hex.toUpperCase();
    }

    public static SecretKey generatedes(String pass) throws Exception {
        // String myEncKey="This is Key";
        byte[] KeyAsBytes = generateKeyAes(pass);// keygeneratorAes();//myEncKey.getBytes("UTF8");
        KeySpec myKeySpec = new DESKeySpec(KeyAsBytes);
        SecretKeyFactory mySecretKeyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = mySecretKeyFactory.generateSecret(myKeySpec);
        return key;
    }

    public static String encryptdes(byte[] unencryptedString, int mode, String pass) throws Exception {

        SecretKey deskey = generatedes(pass);

        if (mode == 2) {
            unencryptedString = hex2byte(unencryptedString);
        }

        Cipher cipher = Cipher.getInstance("DES");

        cipher.init(mode, deskey);
        byte[] encryptedText = cipher.doFinal(unencryptedString);

        String result;
        if (mode == 1) {
            result = byte2hex(encryptedText);

        } else {
            String encryptText = new String(encryptedText);

            result = encryptText;

        }

        return result;

    }

    public static String encryptblowfish(byte[] input, int mode, String pass) throws Exception {

        byte[] key = new byte[0];
        if (mode == 2) {
            input = hex2byte(input);
        }

        key = generateKeyAes(pass);
        SecretKeySpec secretKey = new SecretKeySpec(key, "Blowfish");
        Cipher cipher = Cipher.getInstance("Blowfish");
        cipher.init(mode, secretKey);

        byte[] encryptedText = cipher.doFinal(input);

        String result;
        if (mode == 1) {
            result = byte2hex(encryptedText);

        } else {
            String encryptText = new String(encryptedText);

            result = encryptText;

        }

        return result;

    }

    // rc2 not working because library is not found we can include jar file and it
    // is working
    // ALl engines of crypto are available
    // :https://github.com/bcgit/bc-java/tree/master/core/src/main/java/org/bouncycastle/crypto/engines
    //
    /*
     * public static String encryptrc2(byte[] input,int mode, String pass) throws
     * Exception {
     * 
     * byte[] key= new byte[0];
     * if(mode==2){
     * input= hex2byte(input);
     * }
     * 
     * 
     * key = generateKeyAes(pass);
     * SecretKeySpec secretKey = new SecretKeySpec(key, new
     * RC2Engine().getAlgorithmName());
     * Cipher cipher = Cipher.getInstance(new RC2Engine().getAlgorithmName());
     * cipher.init(mode, secretKey);
     * 
     * byte[] encryptedText = cipher.doFinal(input);
     * 
     * String result;
     * if(mode==1){
     * result = byte2hex(encryptedText);
     * 
     * }else {
     * String encryptText = new String(encryptedText);
     * 
     * result=encryptText;
     * 
     * }
     * 
     * return result;
     * 
     * }
     * 
     * 
     */

    // hashing functions//

    public static String convertToSHA1or512(String str, String type) throws Exception {
        MessageDigest instance = MessageDigest.getInstance(type);
        instance.reset();
        instance.update(str.getBytes("UTF-8"));
        return convertByteToHex(instance.digest());

    }

    public static String convertToSHA384or224or256(String str, String type) throws Exception {
        char append = '0';
        if (type.equals("SHA-384")) {
            append = '0';
        } else if (type.equals("SHA-224")) {
            append = '1';
        } else if (type.equals("SHA-256")) {
            append = '0';
        }
        MessageDigest instance = MessageDigest.getInstance(type);
        instance.reset();
        byte[] digest = instance.digest(str.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            String hexString = Integer.toHexString(b & 255);
            if (hexString.length() == 1) {
                sb.append(append);
            }
            sb.append(hexString);
        }
        return sb.toString().toUpperCase();

    }

}