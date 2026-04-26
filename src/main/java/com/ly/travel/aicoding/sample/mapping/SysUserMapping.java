package com.ly.travel.aicoding.sample.mapping;

import com.ly.travel.aicoding.common.support.DateRelatedMapper;
import com.ly.travel.aicoding.sample.api.model.SysUserDTO;
import com.ly.travel.aicoding.sample.api.request.SysUserCreateRequest;
import com.ly.travel.aicoding.sample.api.request.SysUserModifyRequest;
import com.ly.travel.aicoding.sample.api.request.SysUserPageRequest;
import com.ly.travel.aicoding.sample.domain.model.SysUserDO;
import com.ly.travel.aicoding.sample.domain.model.SysUserQuery;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * 系统用户映射器
 *
 * @author CLAUDE-CODE
 * @version 1.0.0
 * @date 2026/4/26 17:17
 */
@Mapper(componentModel = "spring", uses = {DateRelatedMapper.class}, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class SysUserMapping {

    public abstract SysUserDTO toDTO(SysUserDO source);

    public abstract SysUserDO toCreateDO(SysUserCreateRequest source);

    public abstract SysUserDO toModifyDO(SysUserModifyRequest source);

    public abstract SysUserQuery toQuery(SysUserPageRequest source);

}
