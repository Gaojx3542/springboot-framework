package com.newlandpay.newretail.appstore.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author chenkai
 * @date 2019/8/5
 */
@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "task-executor")
public class TaskExecutorProperties {

    private Integer corePoolSize;

    private Integer maxPoolSize;

    private Integer queueCapacity;
}
