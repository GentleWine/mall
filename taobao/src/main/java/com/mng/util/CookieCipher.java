package com.mng.util;

import lombok.SneakyThrows;

import javax.annotation.concurrent.ThreadSafe;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.util.Base64;

@SuppressWarnings("unused")
@ThreadSafe
public final class CookieCipher {

    private static final byte[] cipherKey = Base64.getDecoder().decode("eUFkJH4tUGNzJDRRW01fdTx9OlF5MC5wLm1dbElndGBNTFlgPVc7Onl7cUdjSS9rTEQ8RTYgaGVzKit8W1p5bUMsMA==");
    private static volatile CookieCipher INSTANCE;
    private final Cipher encryptCipher;
    private final Cipher decryptCipher;

    private CookieCipher() {
        this(cipherKey);
    }

    @SneakyThrows
    private CookieCipher(byte[] key) {
        Key keyObj = getKey(key);
        encryptCipher = Cipher.getInstance("AES");
        encryptCipher.init(Cipher.ENCRYPT_MODE, keyObj);
        decryptCipher = Cipher.getInstance("AES");
        decryptCipher.init(Cipher.DECRYPT_MODE, keyObj);
    }

    public static CookieCipher getInstance() {
        synchronized (CookieCipher.class) {
            if (INSTANCE == null) {
                synchronized (CookieCipher.class) {
                    INSTANCE = new CookieCipher();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813，和public static byte[]
     * <p>
     * hexStr2ByteArr(String strIn) 互为可逆的转换过程
     *
     * @param arrB 需要转换的byte数组
     * @return 转换后的字符串
     */
    private static String byteArr2HexStr(byte[] arrB) {
        int iLen = arrB.length;
        // 每个byte用2个字符才能表示，所以字符串的长度是数组长度的2倍
        StringBuilder sb = new StringBuilder(iLen * 2);
        for (int b : arrB) {
            int intTmp = b;
            // 把负数转换为正数
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            // 小于0F的数需要在前面补0
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }

    /**
     * 将表示16进制值的字符串转换为byte数组，和public static String byteArr2HexStr(byte[] arrB)
     * 互为可逆的转换过程
     *
     * @param strIn 需要转换的字符串
     * @return 转换后的byte数组
     */
    private static byte[] hexStr2ByteArr(String strIn) {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;
        // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }

    private Key getKey() {
        return getKey(cipherKey);
    }

    private Key getKey(byte[] arrBTmp) {
        // 创建一个空的32位字节数组（默认值为0）
        byte[] arrB = new byte[32];
        // 将原始字节数组转换为32位
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }
        // 生成密钥
        return new SecretKeySpec(arrB, "AES");
    }

    /**
     * 加密字节数组
     *
     * @param arrB 需加密的字节数组
     * @return 加密后的字节数组
     */
    public byte[] encrypt(byte[] arrB) throws GeneralSecurityException {
        return encryptCipher.doFinal(arrB);
    }

    /**
     * 加密字符串
     *
     * @param strIn 需加密的字符串
     * @return 加密后的字符串
     */
    public String encrypt(String strIn) throws GeneralSecurityException {
        return byteArr2HexStr(encrypt(strIn.getBytes()));
    }

    /**
     * 解密字节数组
     *
     * @param arrB 需解密的字节数组
     * @return 解密后的字节数组
     */
    public byte[] decrypt(byte[] arrB) throws GeneralSecurityException {
        return decryptCipher.doFinal(arrB);
    }

    /**
     * 解密字符串
     *
     * @param strIn 需解密的字符串
     * @return 解密后的字符串
     */
    public String decrypt(String strIn) throws GeneralSecurityException {
        return new String(decrypt(hexStr2ByteArr(strIn)));
    }

}
