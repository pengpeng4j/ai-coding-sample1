package com.ly.travel.aicoding.sample.domain.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

/**
 * 领域实体查询参数对象
 *
 * @author CLAUDE-CODE
 * @version 1.0.0
 * @date 2026/4/26 17:27
 */
@Getter
@Setter
@ToString
public class SysUserQuery extends SysUserDO {

    private Set<Integer> userStatuses;

}
