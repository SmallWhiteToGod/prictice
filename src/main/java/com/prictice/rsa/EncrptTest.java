package com.prictice.rsa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author ??
 * @company lhfinance.com
 * @className: EncrptTest.java
 * @package com.fintechervision.fdp.batch.jfSteps.common
 * @description:  ????
 * @date 2019/7/15 9:55
 */
public class EncrptTest {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final String Algorithm = "DESede";

    private String lihPath="E:\\ij_workSpace\\data\\lih-priKeyPath\\cs.p12";

    private String jfPath="E:\\ij_workSpace\\data\\lih-pubKeyPath\\cs.der";

    private String secretKey = "123456";

    private RSA rsa = null;


    public EncrptTest() throws Exception {
        rsa = new RSA(jfPath,lihPath,secretKey);
    }

    /**
     * ??
     * @param express
     * @return
     * @throws IOException
     */
    public String encrypt (String express) throws IOException {
        return rsa.encryptByRSA(express, "UTF-8");
    }

    /**
     * ??
     * @param ciphertext
     * @return
     * @throws IOException
     */
    public String decrypt (String ciphertext) throws IOException {
        return  rsa.decryptByRSA(ciphertext,  "UTF-8");
    }


    public static void main(String[] args) throws Exception{
        EncrptTest rsa = new EncrptTest();

        //String text = "UA4GK7H5vtlgawf09jRYPuidItCEMVRLt2oeR4tpAyHIO0FXnOsEhaIbYBntWtFfDwNXbNKySdtAxwHTfz7DFeGd0rWuEnCZE44c7FcCLzMP0vzFvwNqfQ7qTq+8HBMExzaaqwax+637gKCrgNPLyd6N+yxZICV1KHgsUHeY4NZABrbOd2NEha0tXjv04PTFpE1ZjPoz0YZ0ArI+AZbHc6zM0rJhywXF8n78CgvHfJNJWgxjncS0U2vvGSccp2T/4ZDdEz/yqTouscDAp9tT1x96p0v6FkfJYwtRgYp/5cUBZZ/RPjuQhuoIcx7o1N2UXFXNuTODfhg65s3/CaKtcg==";

        String text = "今晚打老虎";
        String plainText = rsa.encrypt(text);
        System.out.println(plainText);
        System.out.println(rsa.decrypt(plainText));
    }

}
