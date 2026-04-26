package com.ly.travel.aicoding.sample.api.controller;

import com.ly.travel.aicoding.common.model.DefaultResponse;
import com.ly.travel.aicoding.sample.api.model.SysUserDTO;
import com.ly.travel.aicoding.sample.api.request.SysUserQueryRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 系统用户开放API接口
 *
 * @author CLAUDE-CODE
 * @version 1.0.0
 * @date 2026/4/26 16:07
 */
@RestController
@RequestMapping("/api/sysuser")
public class SysUserOpenapiController extends AbstractController {

    /**
     * 根据查询用户列表
     *
     * @param request 请求参数
     * @return 响应参数
     */
    @PostMapping("/queryUsersByCondition")
    public DefaultResponse<List<SysUserDTO>> queryUsersByCondition(@RequestBody @Validated SysUserQueryRequest request) {
        //TODO
        return null;
    }

}
