package otherTest;

import com.prictice.util.StringUtil.StringUtil;
import org.junit.Test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 苏博
 * @version V1.2.0
 * @className: StringTest.java
 * @package otherTest
 * @description:
 * @date 2019/4/8 16:13
 */
public class StringTest {


    @Test
    public void toColName(){
        StringUtil util = new StringUtil();
        System.out.println(util.propNameToColName("userName"));
        System.out.println(util.propNameToColName("USERNAME"));

    }

    @Test
    public void strTest(){
        String remoteFilePath = "D:\\mysql.test.txt";
        int pos = remoteFilePath.lastIndexOf(File.separator);
        String temp = remoteFilePath.substring(pos+1);
        String tempDir =remoteFilePath.substring(0, pos)+File.separator;

        System.out.println(tempDir+temp);
    }

    @Test
    public void getRealFileName() {

        String remoteFilePath = "/sftp/11003/$yyyymmdd/$org_contract_orderNo_10.pdf";
        int pos = remoteFilePath.lastIndexOf(File.separator);
        String orderNo = "02B001";
        String temp = remoteFilePath.substring(pos + 1);
        String tempDir = remoteFilePath.substring(0, pos) + File.separator;
        temp = temp.replaceAll("orderNo", orderNo);


        String fileName = tempDir + temp;
        String realFileName = fileName.replaceAll("\\\\", "/").contains("/") ? fileName.substring(fileName.lastIndexOf("/") + 1) : fileName;
        System.out.println(realFileName);
    }

    @Test
    public void jsonStr() {

        String remoteFilePath = "/sftp/11003/$yyyymmdd/$org_contract_orderNo_10.pdf";

    }


}
