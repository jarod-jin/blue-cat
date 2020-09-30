package cn.jarod.bluecat.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/5/21
 */
@Configuration
@EnableAsync
public class ExecutorConfig {

    @Value("${executor.core-pool:2}")
    private Integer corePool;

    @Value("${executor.max-pool:5}")
    private Integer maxPool;

    @Value("${executor.queue-capacity:10}")
    private Integer queueCapacity;

    @Value("${executor.keep-alive:200}")
    private Integer keepAlive;

    @Value("${executor.thread-name-prefix:sys}")
    private String threadNamePrefix;

    @Bean
    @Primary
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置核心线程数
        executor.setCorePoolSize(corePool);
        // 设置最大线程数
        executor.setMaxPoolSize(maxPool);
        // 设置队列容量
        executor.setQueueCapacity(queueCapacity);
        // 设置线程活跃时间（秒）
        executor.setKeepAliveSeconds(keepAlive);
        // 设置默认线程名称
        executor.setThreadNamePrefix(threadNamePrefix);
        // 设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }
}
