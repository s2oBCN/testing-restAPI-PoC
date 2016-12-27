package com.s2o.scattergram.images;

import de.codecentric.boot.admin.config.EnableAdminServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAdminServer
@EnableAsync
public class IcaImageGenSrvApplication extends AsyncConfigurerSupport {

    @Value("${imageWritter.maxPool.size:10}")
    private int maxPoolSize;

    @Value("${imageWritter.queue.capacity:5000}")
    private int queueCapacity;

    @Value("${spring.application.name}")
    private String threadNamePrefix;

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(maxPoolSize);
        taskExecutor.setQueueCapacity(queueCapacity);
        taskExecutor.setThreadNamePrefix(threadNamePrefix);
        taskExecutor.initialize();
        return taskExecutor;
    }

	public static void main(String[] args) {
		SpringApplication.run(IcaImageGenSrvApplication.class, args);
	}
}

