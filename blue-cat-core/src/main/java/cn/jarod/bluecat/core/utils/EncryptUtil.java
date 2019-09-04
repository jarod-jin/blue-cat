package cn.jarod.bluecat.core.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Random;

/**
 * @auther jarod.jin 2018/12/4
 */
@Slf4j
public class EncryptUtil {

    private static final String SHA_256 = "SHA-256";

    private static final String MD5 = "MD5";

    private static final String ROOT = "0";

    private static final String DIGIT = "0123456789";

    private static final String LETTER = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";


    /**
     * MD5加密 生成32位md5码
     */
    public static String stringEncodeMD5(String inStr){
        MessageDigest md5;
        String encodeStr = "";
        try{
            md5 = MessageDigest.getInstance(MD5);
            char[] charArray = inStr.toCharArray();
            byte[] byteArray = new byte[charArray.length];

            for (int i = 0; i < charArray.length; i++)
                byteArray[i] = (byte) charArray[i];
            byte[] md5Bytes = md5.digest(byteArray);
            StringBuilder hexValue = new StringBuilder();
            for (byte b : md5Bytes){
                int val = ((int) b) & 0xff;
                if (val < 16)
                    hexValue.append(ROOT);
                hexValue.append(Integer.toHexString(val));
            }
            encodeStr = hexValue.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage());
        }
        return encodeStr;
    }

    /**
     * sha256加密
     * @param message
     * @return 加密后字符串
     */
    public static String stringEncodeSHA256(String message)  {
        String encodeStr = "";
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(SHA_256);
            md.update(message.getBytes(StandardCharsets.UTF_8.displayName()));
            byte[] bytes = md.digest();
            encodeStr = byteArrayToHexString(bytes);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            log.error(e.getMessage());
        }
        return encodeStr;
    }

    /**
     * 将加密后的字节数组转换成字符串
     * @param b 字节数组
     * @return 字符串
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toLowerCase();
    }

    public static String getRandomCode(int length){
        return getRandomCode(length, false);
    }


    public static String getRandomCode(int length, boolean hasLetter){
        String str= DIGIT;
        if (hasLetter)
            str += LETTER;
        char[]arr=new char[length];//定义一个长度是4的char型数组
        Random sj=new Random();
        for(int i=0;i<length;i++)
            arr[i]= str.charAt(sj.nextInt(str.length()));//从str中随机截取4个单个字符并赋值给arr这个数组存放
        return new String(arr);
    }
}
