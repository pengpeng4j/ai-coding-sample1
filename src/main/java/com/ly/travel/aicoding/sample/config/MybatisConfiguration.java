package com.ly.travel.aicoding.sample.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * Mybatis相关的自定义配置
 *
 * @author CLAUDE-CODE
 * @version 1.0.0
 * @date 2026/4/26 17:25
 */
@Configuration
@MapperScan("com.ly.travel.aicoding.sample.dal.mapper")
public class MybatisConfiguration {



}
