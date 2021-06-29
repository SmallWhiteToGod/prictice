package com.prictice.cryptUtil;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author 苏博
 * @company lhfinance.com
 * @className: TestBestSign.java
 * @package com.prictice.cryptUtil
 * @description:
 * @date 2019/5/27 16:53
 */
public class TestBestSign {

    private static final String KEY_PRI = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCC/VH4RFZlZwZgLl+06DUXjA9B/6iriJxb0pftw9wwVn57BRmTaA14JuPjJr6WbUteDkeqeUvWHcwYo+u4WN9VcwEvYQ34RUegCP8vdxnCFAmFt3iXXUrWjnXvSgxYtFE7yegqSX7sUB2LqVPT0M8yxxTSulEqLouZiwwWFuc0KvHPGQG4opTB0KasbND3EaVkf1fkmfWe+mwOddN7+2bpaqR4naRDNe6ZN5GfR/kAS15D3NBptuVq45PDP4/WwsfNPA23AvA9yRJ0pdth/6OpNEONGJYd0pAGrlXp/rqojm3xxh4Z4/LpMEExgmKZFWL6x25gnI6rJQN55rZy5Q6bAgMBAAECggEAJQczCrlWqueKt9mkGGGj0Ho5PfDlX0gI2zQW2pGJRujMbD4u0qqRVCu6M9YZ/i/7JCVpQNl1BJv7u+NqmPwWdfNCnIAVEN+UtbqVWHeUgnZjkrcgkbk/lJNE51XJU7H1XK9gv8Ny655iG77MAY5/Y90/F6qvkvsBX3AdBImI5gfuMjOMU7zCRSdVbnI43GVHalYjXe2K4ReAx/ONLH90Gy2KKCaf0/TyfPR9ycz+SATqvLGK/Y+2VVsFE5Wb+g5bR7+a9cngdDIZiAOUpjtCSwFuUo2SRFtAT/r5mMmXGwjeTVjEYHJc6VM4chfPOsiTIgYa2pTCdmJR/27sfgYoMQKBgQC6tcOll0F5z6PLu+ILI0V449sg8WpISlCI+q+u+qfCmGSmIdSqGDM+/ZdM/moOhzQHbT6TlQl4WCiXBsvB2pbN4r5hvlJrEnVaylIjvb+PkvIeqauZvz96BDPv7gYoUe4Qz5Yk3AdbbWuQrdzXw1ueJqgGkLT7syBOFRRaNa4AlwKBgQCzmeA6SP7bMGQmARnw7qibm58F5MZenLw2RYsYQuoBapCS5CoQ0nqfqa3+SeIIXLELemPffGVLnirMxyNqRqYAhQ0uGBLLHfiFCHho7zr1WeO345C8jpYd5ZRwKGwYVzwbRaF0SqzJ8+BNR6TADiKYtkH+pP1roDSR/pSQvGsenQKBgQCXh1TVW0BkX8QLYNw/Ncz4I9R/k+4x5T4fLXTPidYE9DEbM3Go6iMNCDp23jOsvsXXES4sEFSzOrkWeQl8tq6VQWRJZ3D0PPLJUFIBd8mz+ApQIFuIV9JocGjGXGLa7kNLmW29VGUUKmWV8cS9x2rfm3fafC6l5+Zb+HegDti82QKBgGdHiokZ5uy29DBim79QDseFAzqJZ4WKzjTLYwRtnlF4LXE7IfGQiBF+qt67L+8tFmaKWBpxV3LXnrcpoPUBcX6FBKJIF66UKpCd4Vg+kgH7UhcyFs5HlI3WsriYliidKaRpM0JDmv97bbYjB9ulhUGaNXMw/8Y/V1vJMTfWEieFAoGAbLKIEWPz159kDpsXdI08Z4UYk8AA6kHYQGTbJurcYcT1BuwsAFu477gwkC9YuBT/z8JQXoxKmHUuGi7sWOiJBL4rNSdUcpHFngkh8tD1jMGv0v/C2dkW0ypdseIgVGwJ3rTw1i7o0MoTkMODU9WUxwO7hjtyCT19WZjumAEC97U=";

    private static final String KEY_PUB = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgv1R+ERWZWcGYC5ftOg1F4wPQf+oq4icW9KX7cPcMFZ+ewUZk2gNeCbj4ya+lm1LXg5HqnlL1h3MGKPruFjfVXMBL2EN+EVHoAj/L3cZwhQJhbd4l11K1o5170oMWLRRO8noKkl+7FAdi6lT09DPMscU0rpRKi6LmYsMFhbnNCrxzxkBuKKUwdCmrGzQ9xGlZH9X5Jn1nvpsDnXTe/tm6WqkeJ2kQzXumTeRn0f5AEteQ9zQabblauOTwz+P1sLHzTwNtwLwPckSdKXbYf+jqTRDjRiWHdKQBq5V6f66qI5t8cYeGePy6TBBMYJimRVi+sduYJyOqyUDeea2cuUOmwIDAQAB";


    public static void main(String[] args) throws Exception{

        RSAUtils rsaUtils = new RSAUtils(KEY_PRI,KEY_PUB);

        String sign = rsaUtils.sign(plaintext);

        System.out.println(URLEncoder.encode(sign,"UTF-8"));

        //System.out.println(rsaUtils.verify(urldecode(),plaintext));

        System.out.println(rsaUtils.verify(URLDecoder.decode(mysign, "utf-8"), plaintext));

    }


    private static String plaintext ="developerId=1558940489013373409rtick=1559009174603signType=rsa/openapi/v2/storage/contract/upload/2e772111bf2311bb2c2f4001b5071620";
    private static String mysign = "J0I1UeiqwkU37kNLc5D8V17t9Hqu9cPYa1g5Vktb2vLmFqBPreN1uUsZ%2FiP5bqD52cmkuZayEsdnMa2POZ9In8E4nEYGPROaAwxfIMiSfFG387Ahx23NwwIhQj3lwTaqORjH0Wch5OndfKZ02mtjPxLbmXyqiLU3Yi6JKYgQToqP6iTU8QSMxUj4VeMSc0okyxgbh4Kbm4LjQSeWm7XpJm9%2FnxceUuKhnKui%2BotD0j2OwVmWwbfgcZvG5GCymF8Ny3Qc0dmUDzuk9LZBMpKE4coiUqhstUHKjTLEl9YDP40VhsMBKGy2qpe01%2FcVQRzrCrXRF0wFe8yBdCLxt8AIXg%3D%3D";

}
