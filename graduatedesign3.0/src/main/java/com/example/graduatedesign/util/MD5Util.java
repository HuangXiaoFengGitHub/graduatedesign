package com.example.graduatedesign.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * @author     : bless<505629625@qq.com>
 * Create Time : 2011-2-21����09:36:48
 * Description : 
 *             MD5Util�����㷨
 */

public class MD5Util {

   /**
    * 
    * Function  : ����ָ���ַ���
    * @author   : bless<505629625@qq.com>
    * @param str  : �����ܲ���
    * @return   : ���ܺ�Ľ��
    */
    public static final String getMd5(String str)
    {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("String to encript cannot be null or zero length");
        }
        StringBuffer hexString = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] hash = md.digest();
            for (int i = 0; i < hash.length; i++) {
                if ((0xff & hash[i]) < 0x10) {
                    hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
                } else {
                    hexString.append(Integer.toHexString(0xFF & hash[i]));
                }
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hexString.toString();

    }
    
    public static void main(String[] args) {
		System.out.println(MD5Util.getMd5("admin"));
	}
}

