package com.prictice.util.other;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * 
 * <p> Properties工具类 </p>
 * 
 * @author Liuxiaohui 2013-10-8 下午2:56:21
 */
public class PropertiesUtils {
	private static final String sysfilePath = "conf/sysConfig.properties";
	private static Properties sysProps = null;
	static {
		if (sysProps == null) {
			InputStream inSys = null;
			try {
				inSys = PropertiesUtils.class.getClassLoader().getResourceAsStream(sysfilePath);
				sysProps = new Properties();
				sysProps.load(inSys);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (inSys != null) {
					try {
						inSys.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * 
	 * <p> 通过key获取prop value </p>
	 * 
	 * @author Liuxiaohui 2013-10-8 下午2:55:49
	 */
	public static String getPropertyByKey(String key) {
		return sysProps.getProperty(key);
	}
}
