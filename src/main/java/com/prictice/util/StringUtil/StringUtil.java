package com.prictice.util.StringUtil;

/**
 * @author 苏博
 * @version V1.2.0
 * @className: StringUtil.java
 * @package com.prictice.test.StringTest
 * @description: 字符串工具测试
 * @date 2019/4/8 10:50
 */
public class StringUtil {

    //小写转大写,非小写加下划线
    public String propNameToColName(String propName) {
        StringBuilder colName = new StringBuilder("");
        int len = propName.length();

        for(int i = 0; i < len; ++i) {
            char c = propName.charAt(i);
            if (c >= 'a' && c <= 'z') {
                colName.append((char)(c - 32));
            } else {
                colName.append("_");
                colName.append(c);
            }
        }

        return colName.toString();
    }
}
