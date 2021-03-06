package com.galt.java.taskgenerator.core.utils;

import com.galt.java.taskgenerator.App;
import com.sun.jndi.toolkit.url.Uri;

import java.io.*;
import java.net.URI;
import java.nio.charset.Charset;
import java.security.*;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * A utility class that encrypts or decrypts a file.
 *
 * @author www.codejava.net
 */
public class CryptoUtils {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";
    public static final String PASSWORD_HASH_ALGORITHM = "SHA-1";
    private static final String DATA_FILE_NAME = "data.dat";

    public static void encrypt(String input, File outputFile)
            throws CryptoException {
        try {
            Charset.forName("UTF-8").encode(input);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, buildKey());

            // byte[] inputBytes = Charset.forName("UTF-8").encode(input).array();
            byte[] inputBytes = input.getBytes();
            byte[] outputBytes = cipher.doFinal(inputBytes);

            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(outputBytes);
            outputStream.close();

        } catch (NoSuchPaddingException | NoSuchAlgorithmException
                | InvalidKeyException | BadPaddingException
                | IllegalBlockSizeException | IOException ex) {
            throw new CryptoException("Error encrypting/decrypting file", ex);
        }
    }

    public static String decrypt(File input)
            throws CryptoException {
        String data = null;
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, buildKey());

            FileInputStream fis = new FileInputStream(input);

            byte[] inputBytes = new byte[(int) input.length()];
            fis.read(inputBytes);
            byte[] outputBytes = cipher.doFinal(inputBytes);
            data = new String(outputBytes);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException
                | InvalidKeyException | BadPaddingException
                | IllegalBlockSizeException ex) {
            throw new CryptoException("Error encrypting/decrypting file", ex);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    private static void doCrypto(int cipherMode, String input,
                                 File outputFile) throws CryptoException {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(cipherMode, buildKey());

            byte[] inputBytes = input.getBytes();
            byte[] outputBytes = cipher.doFinal(inputBytes);

            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(outputBytes);
            outputStream.close();

        } catch (NoSuchPaddingException | NoSuchAlgorithmException
                | InvalidKeyException | BadPaddingException
                | IllegalBlockSizeException | IOException ex) {
            throw new CryptoException("Error encrypting/decrypting file", ex);
        }
    }

    public static String getPassword() {
        BufferedReader reader;
        String hash = null;
        try {
            reader = new BufferedReader(new InputStreamReader(App.class.getClassLoader().getResourceAsStream("it_is_not_password")));
            hash = reader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hash;
    }

    private static MessageDigest getMessageDigester() {
        MessageDigest digester = null;
        try {
            digester = MessageDigest.getInstance(PASSWORD_HASH_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return digester;
    }

    private static Key buildKey() {
        SecretKeySpec spec = null;
        byte[] key = getMessageDigester().digest();
        key = Arrays.copyOf(key, 16);
        spec = new SecretKeySpec(key, ALGORITHM);
        return spec;
    }

    public static String getSHA(String password) {
        MessageDigest messageDigest = getMessageDigester();
        messageDigest.update(password.getBytes());
        return byteArrayToHexString(messageDigest.digest());
    }

    public static URI getPathOfData() {
        try {
            URI pathExecution = CryptoUtils.class.getProtectionDomain().getCodeSource().getLocation().toURI();
            if (pathExecution.getPath().contains(".jar")) {
                pathExecution = new URI(pathExecution.toString().substring(0, pathExecution.toString().lastIndexOf('/') + 1));
            }
            return new URI(String.format("%s%s", pathExecution.toString(), DATA_FILE_NAME));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getData() {
        try {
            return decrypt(new File(getPathOfData()));
        } catch (CryptoException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static class CryptoException extends Exception {

        public CryptoException() {
        }

        public CryptoException(String message, Throwable throwable) {
            super(message, throwable);
        }
    }

    public static String byteArrayToHexString(byte[] b) {
        String result = "";
        for (int i = 0; i < b.length; i++) {
            result +=
                    Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }

}