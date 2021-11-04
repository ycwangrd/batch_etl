package com.huayanginfo.etl.annotation;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author wangrd 北京华洋峻峰信息工程股份公司
 * https://www.huayanginfo.com ©2008-2021 huayanginfo.com
 * All Rights Reserved.
 * @since 2021年08月24日 星期二 17:16:09
 */
@Log4j2
@Aspect
@Component
public class RecordAccessAspect {
    @Pointcut(value = "@annotation(com.huayanginfo.etl.annotation.RecordAccess)")
    public void pointCut() {
    }

    @Before(value = "pointCut()")
    public void doBefore(JoinPoint point) {
        log.warn("访问类[{}]的方法[{}];", point.getTarget().getClass(), point.getSignature());
    }
}
