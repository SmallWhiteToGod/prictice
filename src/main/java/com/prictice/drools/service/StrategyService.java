package com.prictice.drools.service;

import com.prictice.drools.fintechervision.TmDmpStrategy;

/**
 * @author 苏博
 * @className: StrategyService.java
 * @package com.prictice.drools.service
 * @description:
 * @date 2020/1/9 18:49
 */
public interface StrategyService {

    TmDmpStrategy getDefaultStrategy(String stClass);

    TmDmpStrategy getTmDmpStrategy(Long stId);
}
