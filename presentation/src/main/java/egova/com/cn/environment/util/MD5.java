/* 
 * MD5.java        2009.11.3
 * 
 * Copyright (c) 2009 北京数字政通科技股份有限公司
 * 版权所有
 * 
 * 文件功能描述：对字符串进行MD5加密，返回加密后的字符串。
 *              对使用MD5加密的密码验证。
 *
 * 修改标识：
 * 修改描述：
 */
package egova.com.cn.environment.util;

import java.security.MessageDigest;

/**
 * 对输入的字符串进行MD5加密.
 */
public class MD5 {

    public static final String MD5_STYLE_ALL = "all";

    public static final String MD5_STYLE_PASSWORD = "password";

    public static final String MD5_STYLE_PATROLID = "patrolID";

    public static final String MD5_STYLE_NOT = "not";

    //加密用户名和密码
    public static String encrypt(String humanID, String pswd) {
//
//		 if(MD5_STYLE_ALL.equalsIgnoreCase(style)){
//			 return encryptStr(carid + pswd);
//		 } else if(MD5_STYLE_PASSWORD.equalsIgnoreCase(style)){
//			 return encryptStr(pswd);
//		 } else if(MD5_STYLE_PATROLID.equalsIgnoreCase(style)){
//			 return encryptStr(DBSessionHelper.getHumanID() + pswd);
//		 } else{
//			 return pswd;
//		 }
        return encryptStr(humanID + pswd);
    }

    /**
     * 对输入的字符串进行MD5加密.
     *
     * @param s 需要加密的字符串.
     * @return 经过加密的字符串.
     */
    public static String encryptStr(String s) {
        //如果密码为空
        if ((null == s || s.equals(""))) {
            return s;
        }
        char hexChars[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] bytes = s.getBytes();
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(bytes);
            bytes = md.digest();
            int j = bytes.length;
            char[] chars = new char[j * 2];
            int k = 0;
            /**
             * 对加密后的字符串进行编码。
             * 低位字节通过把每个字节无符号右移四位再映射得到；高位字节直接映射得到。
             */
            for (int i = 0; i < bytes.length; i++) {
                byte b = bytes[i];
                chars[k++] = hexChars[b >>> 4 & 0xf];
                chars[k++] = hexChars[b & 0xf];
            }
            return new String(chars);
        } catch (Exception e) {
            return null;
        }
    }

}
