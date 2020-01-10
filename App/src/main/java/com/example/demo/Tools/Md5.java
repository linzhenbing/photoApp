package com.example.demo.Tools;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Md5 {
    public static String changeToMd5(String string ) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md5 = MessageDigest.getInstance("md5");
        Base64.Encoder base64Encode = Base64.getEncoder();
        return base64Encode.encodeToString(md5.digest(string.getBytes("utf-8")));
    }
}
