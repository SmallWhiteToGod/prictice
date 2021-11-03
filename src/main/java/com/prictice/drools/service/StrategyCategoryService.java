package com.prictice.drools.service;

import com.prictice.drools.engine.model.FieldVar;

import java.util.Map;

/**
 * @author 苏博
 * @className: StrategyCategoryService.java
 * @package com.prictice.drools.service
 * @description:
 * @date 2020/1/9 18:49
 */
public interface StrategyCategoryService {

    Map<String, FieldVar> getFieldVar(String stClass);
}
