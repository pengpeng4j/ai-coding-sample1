package com.ly.travel.aicoding.sample.domain.model;

import java.util.Set;

/**
 * 领域实体查询参数对象
 *
 * 这里可以自定义查询参数字段，然后传递到Mybatis中
 *
 * @author CLAUDE-CODE
 * @version 1.0.0
 * @date 2026/4/26 17:27
 */
public class SysUserQuery extends SysUserDO {

    private Set<Integer> userStatuses;

}
