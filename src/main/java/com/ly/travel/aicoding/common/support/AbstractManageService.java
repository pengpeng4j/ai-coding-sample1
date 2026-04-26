package com.ly.travel.aicoding.common.support;

import com.ly.travel.aicoding.common.consts.CommonConstants;

/**
 * 管理端业务服务基类
 *
 * @author CLAUDE-CODE
 * @version 1.0.0
 * @date 2026/4/26 17:01
 */
public abstract class AbstractManageService {

    protected String getOperator() {
        return CommonConstants.DEFAULT_OPERATOR;
    }

}
