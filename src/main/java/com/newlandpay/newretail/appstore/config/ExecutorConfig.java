package com.newlandpay.newretail.appstore.config;

import com.newlandpay.newretail.appstore.config.properties.TaskExecutorProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.*;

/**
 * @author chenkai
 * @date 2019/8/5
 */
@Configuration
@EnableAsync
public class ExecutorConfig {

    @Autowired
    private TaskExecutorProperties taskExecutorProperties;

/*    @Bean("taskExecutor")
    public Executor sysExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程数
        executor.setCorePoolSize(taskExecutorProperties.getCorePoolSize());
        //最大线程数
        executor.setMaxPoolSize(taskExecutorProperties.getMaxPoolSize());
        //队列大小
        executor.setQueueCapacity(taskExecutorProperties.getQueueCapacity());
        //线程名称前缀
        executor.setThreadNamePrefix("appstore-task-");
        //当pool已经达到max size的时候，如何处理新任务
        //CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }*/

    @Bean(value="executorService", destroyMethod = "shutdown")
    public ExecutorService executorService(){
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(taskExecutorProperties.getCorePoolSize(),
                taskExecutorProperties.getMaxPoolSize(), 5, TimeUnit.SECONDS,
                new ArrayBlockingQueue(100),
                new ThreadPoolExecutor.CallerRunsPolicy());
        return threadPool;
    }

    @Bean(value="forkJoinPool", destroyMethod = "shutdown")
    public ForkJoinPool forkJoinPool(){
        return new ForkJoinPool();
    }
}
