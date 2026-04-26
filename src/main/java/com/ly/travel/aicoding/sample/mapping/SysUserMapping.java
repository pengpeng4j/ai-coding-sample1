package com.ly.travel.aicoding.sample.mapping;

import com.ly.travel.aicoding.common.support.DateRelatedMapper;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

/**
 * 系统用户映射器
 *
 * MapStruct使用规范：
 * 1、全部以抽象类的形式存在；
 * 2、全部使用SpringBean注入的方式使用；
 *
 * @author CLAUDE-CODE
 * @version 1.0.0
 * @date 2026/4/26 17:17
 */
@Mapper(componentModel = "spring", uses = {DateRelatedMapper.class}, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
public abstract class SysUserMapping {
}
