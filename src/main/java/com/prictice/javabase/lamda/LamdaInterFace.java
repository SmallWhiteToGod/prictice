package com.prictice.javabase.lamda;

import org.apache.commons.lang3.StringUtils;

/**
 * @author 苏博
 * @company lhfinance.com
 * @className: FunctionInterFace.java
 * @package com.prictice.javabase.lamda
 * @description:
 * @date 2019/9/18 9:32
 */
@FunctionalInterface
public interface LamdaInterFace {

     String convert(Integer f);

     default Integer increment(Integer i){
        return ++i;
     }

    default String reverse(String s){
        return StringUtils.reverse(s);
    }
}
