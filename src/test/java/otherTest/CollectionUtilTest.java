package otherTest;

import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 苏博
 * @version V1.2.0
 * @company lhfinance.com
 * @className: CollectionUtilTest.java
 * @package otherTest
 * @description:
 * @date 2019/4/8 17:09
 */
public class CollectionUtilTest {

    @Test
    public void collect(){
        System.out.println("\\");
        String remoteFilePath = "D:\\\\mysql\\\\/sftp/11003/$yyyymmdd/$org_contract_orderNo_10.pdf";
        int pos = remoteFilePath.lastIndexOf(File.separator);
        System.out.println(pos);
        String orderNo = "02B001";
        String temp = remoteFilePath.substring(pos + 1);
        System.out.println("temp:"+temp);
        String tempDir = remoteFilePath.substring(0, pos) + File.separator;
        temp = temp.replaceAll("orderNo", orderNo);


        String fileName = tempDir + temp;
        System.out.println(fileName);
        String realFileName = fileName.replaceAll("\\\\", "/").contains("/") ? fileName.substring(fileName.lastIndexOf("/") + 1) : fileName;
        System.out.println(realFileName);
    }
}
