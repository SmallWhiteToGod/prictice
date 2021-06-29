package com.prictice.digest;

/**
 * @author 苏博
 * @company lhfinance.com
 * @className: Test.java
 * @package com.prictice.digest
 * @description:
 * @date 2019/12/6 17:14
 */
public class Test {

    public static final int HASH_INTERATIONS = 1024;
    public static final int SALT_SIZE = 8;

    //对明文进行 Hex编码
    public static String entryptPassword(String plainPassword) {
        byte[] salt = DigestUtil.generateSalt(SALT_SIZE);
        byte[] hashPassword = DigestUtil.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
        return EncodeUtil.encodeHex(salt) + EncodeUtil.encodeHex(hashPassword);
    }

    //验证密码
    public static boolean validatePassword(String plainPassword, String password) {
        byte[] salt = EncodeUtil.decodeHex(password.substring(0, 16));
        byte[] hashPassword = DigestUtil.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
        return password.equals(EncodeUtil.encodeHex(salt) + EncodeUtil.encodeHex(hashPassword));
    }

    public static void main(String[] args) {
        String plainPassword = "88888888";
        System.out.println(Test.entryptPassword(plainPassword));

        System.out.println(Test.validatePassword(plainPassword,Test.entryptPassword("88888888")));
    }
}
