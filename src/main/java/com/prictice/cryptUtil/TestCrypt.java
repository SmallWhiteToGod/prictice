package com.prictice.cryptUtil;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

/**
 * @author 苏博
 * @company lhfinance.com
 * @className: TestCrypt.java
 * @package com.prictice.cryptUtil
 * @description: 加解密测试
 * @date 2019/5/21 17:26
 */
public class TestCrypt {

    //支付宝生成秘钥工具(RSA2)
    //https://opendocs.alipay.com/open/291/sign
    private final static String keyPath = "C:\\Users\\74505\\Documents\\支付宝开放平台开发助手\\RSA密钥";

    public static void main(String[] args) throws Exception{

        //随机生成一组秘钥
        //RSAEncrypt.genKeyPair(keyPath);

        String plaintext ="北developerId=1558940489013373409京rtick=1559007288994你好signType=rsa/openapi/v2/storage/contract/upload/5953115fdc307026f4c9197b994f3974啊";
        String prikeyStr = RSAEncrypt.loadPrivateKeyByFile(keyPath);
        String pubKeyStr = RSAEncrypt.loadPublicKeyByFile(keyPath);

        //pubKeyStr = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAk61nbEQEOLf3541flxvxme7On/+G9NsBKbESFREIZySq5Jt/1qcv0qIsIJ2qzZSBYwpxKpcTGgWfe1ALKeaDSsvmTOgucI52KMhT1g6SeUVw+vAcGYnDvWZ+qSDE0BOqnSz645wCrjoR27RuoS03eYQfKLXcwky5q8NlimTYSqk9rcQzpTdIHHKrN5sYjz85Zx6hbzM1g3m17D/cxdLcV0YYixv0h8WR4svnYo73Me1Of5zq+IlWBoOBamqPlFMEf9TWKLGXlH1CxStVnwbDpZGds6ETC3BYA0uIu6bFEpz5Ql2G1h6FFNGAaH1HHVwizd2H/5IrEFRE60Kcr6lNcwIDAQAB";
        pubKeyStr = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuNezzVODmLD26yC+9XvLpZkk+y18wB1RHHbrwn8MWIjqqLt5LlIsEm/4oxwvytCq9Q/d0i+GFRdKxIaGHdy88AjNWUP7tYBLCnywuvts2biJiQ3ouKCkLfBB+Q3/xdL3Hdqv0PRMqCmgxZ2SW6PBaqkOns+nnxm7hmEc0Pf6DaDPZiov30eNSAMWJlAyGuWlaed/bNjysOnHg6VHgdGVIeUKXzER6swLYWj1T3A3iC1X9IkFOG8Vh93dpb/x7Ye70kZP4xKSy2jUN0TvVP8gvHSa3lVLZqUMqGbOQektBzrrX3X0qsByI3NTMriEvAi5U6jRtXfGNxPigtYFeVnvTQIDAQAB";
        prikeyStr = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC417PNU4OYsPbrIL71e8ulmST7LXzAHVEcduvCfwxYiOqou3kuUiwSb/ijHC/K0Kr1D93SL4YVF0rEhoYd3LzwCM1ZQ/u1gEsKfLC6+2zZuImJDei4oKQt8EH5Df/F0vcd2q/Q9EyoKaDFnZJbo8FqqQ6ez6efGbuGYRzQ9/oNoM9mKi/fR41IAxYmUDIa5aVp539s2PKw6ceDpUeB0ZUh5QpfMRHqzAthaPVPcDeILVf0iQU4bxWH3d2lv/Hth7vSRk/jEpLLaNQ3RO9U/yC8dJreVUtmpQyoZs5B6S0HOutfdfSqwHIjc1MyuIS8CLlTqNG1d8Y3E+KC1gV5We9NAgMBAAECggEAeqv80hnIzj8zv9wdn4GnJ6G5X1FS55UNTZzG3m++28sSISU6STmTsjDJjEaamTlfHACJsIKFs92fa0n0HEFwysSss79gf5ZDNxSFnbJbDStha6nldxJ1o18+CXH2+20FP7Tw1fYsIbZu0ne+uD63kHfeEhuyHO7Q+1/b5fuM4hlkJW6v09DfdMs4bRMsvb/1MSIYqpwEvFRiNZP43sGN1rdvA/LzPZFg4hjXPw9ltyahjn+31AfvSsdax7+9OJH6i15EC3MxC5CzS4gGzM8td4xb5uYkTBzeCAsZIE8G5RPuSlo+fOL9Q6iD5R65JWjUFUFriMBQrCFq/qB8wpc7qQKBgQDlea3i/pZmHKM84i0a9snlj2DRQbPg0CX8SL7g9jY/ieSNPuqxBRBg4knWmVZrq1ZMKwcw0mMCPV3YpLuTmK7yi01I6tsndOPuHfRvUWYB+O1MFgbrzm+hRZlZbf03l79zRwqVz+V05gDTKfmMP374WvWfVQCIGDsUDHpu8PdkwwKBgQDONU/46Cy5qK46wgnFPVfHndXPp6yK+MjLLudN6iGfcrOY7Dt1h7tqiDL+mT6UFb4Y+gLPidKoI/SiC1N4Bv5FzXLGJPEakU8GupFWOzI4xmnktjBuS8SWJbZdduOF6M8KWIZwydMl+muXvp2ep9KrwiEOyjNPtHEON8I6uvrarwKBgQCHga75GDVf3xFV5omUHp9ie6REbXP3EXMc4yg0V6/5HUhlT/3uwnrFe1r26l1p/ydamx4H6nSk4fAE+mH56nOErlXY9Tasug3OksnA4mRGTeRyxZ8YqDKrHRpNAPJCXc2kGRNbHAT3Xsql52eBNg2YQr5AdEV4GDbqw+OYl5q/WQKBgEpkK0y1neUAuJJUDChPAArwowD+EZ41MGM0sZeHPsgCHk6t8qls5S0Co7r71/qeeW0TTM5If2nPRuLqyRsFEthlzOE01JJsF6FqzyHXyz92PR+YppIz3WD5i/fd+ykFGyQ1WZL5DKan2AbvanehPbxfFYP7aFV52EtzzCtZ50PbAoGAFcLueCXLcjyhT2s+HUJLotCdBabpPPKLypZZXywKTFTbiqvNu6nUNkVBWRAoUAjLXkWSZyorYTk84MKpGFXXqTjfsVjqhyhQyaSGE0Pa453Baqr6huuJilMIx+jRgHN+o/Daa80wfo7DTiOllsyEuIWE9OTJ4wWCqhBBhuMA6Tc=";

        RSAPublicKey publicKey = RSAEncrypt.loadPublicKeyByStr(pubKeyStr);
        RSAPrivateKey privateKey = RSAEncrypt.loadPrivateKeyByStr(prikeyStr);


        String cryptogram = RSABase64.encode(RSAEncrypt.encrypt(publicKey, plaintext.getBytes()));
        System.out.println("公钥加密的密文");
        System.out.println(cryptogram);

//        String cryptogram2 = Base64.getEncoder().encodeToString(RSAEncrypt.encrypt(publicKey, plaintext.getBytes()));
//        System.out.println("另一种编码");
//        System.out.println(cryptogram2);
//        System.out.println("urlEncode:");
//        String urlEncode = URLEncoder.encode(cryptogram,"utf-8");
//        System.out.println(urlEncode);
//
//        System.out.println("urlDecode");
//        String urlDecode = URLDecoder.decode(urlEncode,"utf-8");
//        System.out.println(urlDecode);

//        byte[] cryptogramDate = RSABase64.decode(cryptogram);
//        String plaintext2 = new String(RSAEncrypt.decrypt(privateKey,cryptogramDate));
//        System.out.println("解密字符串");
//        System.out.println(plaintext2);

       //cryptogram = "EFnyxI5B4WCNiXDgEPArVZF6yECquzByzWDTAqSQa2bUp9XJmH2aP7jfYWfsBKzpu9rNl84VxHlZo5B8R69+OOMpSgW7k4P2viMFT/VDSE8P6NxZ4NT9O0iYVJUB+zVte7ULT9YTj5AOwOTOwVrM6WU3nErqb2dDNKFebzPhuBszgnQhtpOEB7ATYipzitvb+6DGoDCQ2bTPeSOcPmgyB2YwujuJYHBlDVpUvQVfeefiMq2zCIWNE+PVc+wuMDjgXA6VIawt7CFpFMVpiLLZAE5lNTQqmfcsHL4sJx+K3lu7B/BXbHjEpeCQrOcHfqfkYLOicvDatIy5hDvEWBwKSQ==";

        byte[] cryptogramDate2 = java.util.Base64.getDecoder().decode(cryptogram);
        String plaintext3 = new String(RSAEncrypt.decrypt(privateKey,cryptogramDate2));
        System.out.println("私钥解密输出明文");
        System.out.println(plaintext3);

        String cryptogram3 = RSABase64.encode(RSAEncrypt.encrypt(privateKey, plaintext.getBytes()));
        System.out.println("私钥加密的密文");
        System.out.println(cryptogram3);
        String plaintext4 = new String(RSAEncrypt.decrypt(publicKey,RSABase64.decode(cryptogram3)));
        System.out.println("公钥解密输出明文");
        System.out.println(plaintext4);
    }
}
