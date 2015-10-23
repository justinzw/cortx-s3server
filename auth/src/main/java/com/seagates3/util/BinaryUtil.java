/*
 * COPYRIGHT 2015 SEAGATE LLC
 *
 * THIS DRAWING/DOCUMENT, ITS SPECIFICATIONS, AND THE DATA CONTAINED
 * HEREIN, ARE THE EXCLUSIVE PROPERTY OF SEAGATE TECHNOLOGY
 * LIMITED, ISSUED IN STRICT CONFIDENCE AND SHALL NOT, WITHOUT
 * THE PRIOR WRITTEN PERMISSION OF SEAGATE TECHNOLOGY LIMITED,
 * BE REPRODUCED, COPIED, OR DISCLOSED TO A THIRD PARTY, OR
 * USED FOR ANY PURPOSE WHATSOEVER, OR STORED IN A RETRIEVAL SYSTEM
 * EXCEPT AS ALLOWED BY THE TERMS OF SEAGATE LICENSES AND AGREEMENTS.
 *
 * YOU SHOULD HAVE RECEIVED A COPY OF SEAGATE'S LICENSE ALONG WITH
 * THIS RELEASE. IF NOT PLEASE CONTACT A SEAGATE REPRESENTATIVE
 * http://www.seagate.com/contact
 *
 * Original author:  Arjun Hariharan <arjun.hariharan@seagate.com>
 * Original creation date: 17-Sep-2014
 */

package com.seagates3.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.codec.binary.Base64;

public class BinaryUtil {
    /*
     * Calculate the hash of the string and encode it to hexadecimal characters.
     * The characters have to be lower case.
     */

    private static final int MASK_4BITS = (1 << 4) - 1;

    private static final byte[] hexChars = "0123456789abcdef".getBytes();

    /*
     * Return Hex encoded String.
     * All alphabets are lower case.
     */
    public static String hexEncodedHash(String text) {
        try {
            byte[] hashedText = hash(text.getBytes("UTF-8"));
            return toString(encodeToHex(hashedText));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(BinaryUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /*
     * Compute the hash of the byte array and encode it to hex format.
     * All alphabets are lower case.
     */
    public static String hexEncodedHash(byte[] text) {
        byte[] hashedText = hash(text);
        return toString(encodeToHex(hashedText));
    }

    /*
     * Return a base 64 encoded hash generated using SHA-256.
     */
    public static String base64EncodedHash(String text) {
        ByteBuffer bb;
        String secret_key;

        byte[] digestBuff = hash(text);
        bb = ByteBuffer.wrap(digestBuff);
        secret_key = Base64.encodeBase64String(bb.array());

        return secret_key;
    }

    /*
     * Calculate the HMAC using SHA-256.
     */
    public static byte[] hmac(byte[] key, byte[] data) {
        Mac mac;
        try {
            mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(key, "HmacSHA256"));

            return mac.doFinal(data);
        } catch (NoSuchAlgorithmException | InvalidKeyException ex) {
        }
        return null;
    }

    /*
     * Hash the text using SHA-256 algorithm.
     */
    public static byte[] hash(String text) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(text.getBytes());

            return md.digest();
        } catch (NoSuchAlgorithmException ex) {
        }
        return null;
    }

    /*
     * Hash the text using SHA-256 algorithm.
     */
    public static byte[] hash(byte[] text) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(text);

            return md.digest();
        } catch (NoSuchAlgorithmException ex) {
        }
        return null;
    }

    /*
     * Convert the byte array to hex representation.
     */
    public static String toHex(byte[] src) {
        return toString(encodeToHex(src));
    }

    /*
     * Return a base 64 encoded UUID.
     */
    public static String base64UUID(){
        UUID uid = UUID.randomUUID();
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);

        bb.putLong(uid.getMostSignificantBits());
        bb.putLong(uid.getLeastSignificantBits());

        String b64UUID = new org.apache.commons.codec.binary.Base64(true).encodeToString(bb.array());

        return b64UUID;
    }

    public static Boolean isBase64Encoded(String text) {
        return org.apache.commons.codec.binary.Base64.isBase64(text);
    }

    /*
     * Decode the base 64 text and return the bytes.
     */
    public static byte[] base64DecodedBytes(String text) {
        return Base64.decodeBase64(text);
    }

    /*
     * Decode base 64 string.
     */
    public static String base64Decode(String text) {
        return new String(base64DecodedBytes(text));
    }

    public static byte[] encodeToBase64(String text) {
        return Base64.encodeBase64(text.getBytes());
    }

    /*
     * Convert bytes to String
     */
    private static String toString(byte[] bytes) {
        final char[] dest = new char[bytes.length];
        int i=0;

        for (byte b: bytes) {
            dest[i++] = (char)b;
        }

        return new String(dest);
    }

    /*
     * Convert the bytes into its hex representation.
     * Each byte will be represented with 2 hex characters.
     * Use lower case alphabets.
     */
    private static byte[] encodeToHex(byte[] src) {
        byte[] dest = new byte[src.length * 2];
        byte p;

        for (int i=0,j=0; i < src.length; i++) {
            dest[j++] = (byte)hexChars[(p=src[i]) >>> 4 & MASK_4BITS];
            dest[j++] = (byte)hexChars[p & MASK_4BITS];
        }
        return dest;
    }
}