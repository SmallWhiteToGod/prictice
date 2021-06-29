package com.prictice.cryptUtil;

import org.apache.commons.lang3.StringUtils;

import java.nio.charset.Charset;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import org.apache.commons.codec.binary.Base64;

/**
 * @author 苏博
 * @company lhfinance.com
 * @className: RSAUtils.java
 * @package com.prictice.cryptUtil
 * @description:
 * @date 2019/5/27 16:52
 */
public class RSAUtils {
    private static final String SHA256_WITH_RSA_ALGORITHM = "SHA256withRSA";

    private static final String SHA1_WITH_RSA_ALGORITHM = "SHA1withRSA";

    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    private PrivateKey privateKey;

    private PublicKey bankPublicKey;

    public RSAUtils(String priKey, String pubKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //byte[] privateKeys = java.util.Base64.decodeBase64(priKey);
        byte[] privateKeys = Base64.decodeBase64(priKey);
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeys);
        KeyFactory mykeyFactory = KeyFactory.getInstance(StringUtils.substringAfter(SHA1_WITH_RSA_ALGORITHM, "with"));

        this.privateKey = mykeyFactory.generatePrivate(privateKeySpec);

        byte[] pubKeys;
        pubKeys = Base64.decodeBase64(pubKey);
        X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(pubKeys);
        KeyFactory kf = KeyFactory.getInstance(StringUtils.substringAfter(SHA1_WITH_RSA_ALGORITHM, "with"));
        this.bankPublicKey = kf.generatePublic(pubKeySpec);
    }

    public String sign(String str) throws
            NoSuchAlgorithmException, InvalidKeyException, SignatureException {

        //str = new String(Base64.encodeBase64(str.getBytes(Charset.forName("UTF-8"))));
        String signed = null;
        try {

            byte sigData[];
            byte sourceData[] = str.getBytes(DEFAULT_CHARSET);

            // 初始化签名
            Signature sig = Signature.getInstance(SHA1_WITH_RSA_ALGORITHM);
            sig.initSign(privateKey);
            sig.update(sourceData);

            // 1. 签名
            sigData = sig.sign();

            signed = new String(Base64.encodeBase64(sigData));

        } catch (Exception e) {
            throw e;
        }
        return signed;
    }

    public boolean verify(String signed, String sources) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {

        sources = new String(Base64.encodeBase64(sources.getBytes(Charset.forName("UTF-8"))));
        boolean valid = false;
        try {

            byte sourceData[] = sources.getBytes(DEFAULT_CHARSET);

            // base64解密
            byte[] sigData = Base64.decodeBase64(signed);
            // 初始化签名
            Signature sig = Signature.getInstance(SHA1_WITH_RSA_ALGORITHM);
            sig.initVerify(bankPublicKey);
            sig.update(sourceData);

            // 验签
            valid = sig.verify(sigData);
        } catch (Exception e) {
            throw e;
        }

        return valid;

    }

}
