package com.prictice.drools.service.impl;

import com.prictice.drools.engine.kit.StrategyLoader;
import com.prictice.drools.fintechervision.TmDmpStrategy;
import com.prictice.drools.service.StrategyCategoryService;
import com.prictice.drools.service.StrategyExecute;
import com.prictice.drools.service.StrategyService;
import org.apache.commons.lang3.StringUtils;
import org.drools.KnowledgeBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("strategyExecute")
public class StrategyExecuteImpl implements StrategyExecute {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private StrategyService strategyService = new StrategyServiceImpl();

    //@Autowired
    private StrategyCategoryService strategyCategoryService;

    @Override
    public void executeStClass(String stClass, Map<String, Object> fact) {
        long beg = System.currentTimeMillis();
        KnowledgeBase knowledgeBase = loadKnowledgeBase(stClass);
        //执行规则
        StrategyLoader.execute(knowledgeBase, fact);
        long end = System.currentTimeMillis();
        logger.error("StrategyExecuteImpl==策略为：" + stClass + "执行时间是：" + (end - beg));
    }

    @Override
    public void runStrategy(Map<String, Object> fact, String strategyClass) {
        if (StringUtils.isEmpty(strategyClass)) {
            logger.error("策略名称为空");
            throw new RuntimeException("策略名称为空");
        }
    }

    @Override
    public void executeStId(Long stId, Map<String, Object> fact) {
        TmDmpStrategy tmDmpStrategy = strategyService.getTmDmpStrategy(stId);
        if (tmDmpStrategy == null) {
            throw new RuntimeException("找不到该编号的策略");
        }

        executeStrategy(tmDmpStrategy, fact);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void executeStrategy(TmDmpStrategy tmStrategy, Map<String, Object> fact) {
        //填充没设置的决策变量
        //StrategyLoader.inputFactMerge(fact, strategyCategoryService.getFieldVar(tmStrategy.getStClass()));
        //加载知识库
        KnowledgeBase knowledgeBase = StrategyLoader.loadKnowledgeBase(tmStrategy.getStObject());
        //执行规则
        StrategyLoader.execute(knowledgeBase, fact);
    }

    @Override
    public KnowledgeBase loadKnowledgeBase(String stClass) {
        TmDmpStrategy strategy = loadStrategy(stClass);
        //加载知识库
        return StrategyLoader.loadKnowledgeBase(strategy.getStObject());
    }

    @Override
    public TmDmpStrategy loadStrategy(String stClass) {
        TmDmpStrategy tmDmpStrategy = strategyService.getDefaultStrategy(stClass);
        if (tmDmpStrategy == null) {
            throw new RuntimeException("找不到可执行的策略类型为" + stClass + "的策略方案");
        }
        return tmDmpStrategy;
    }
}
