package com.invesco.PDFUtil.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class PropertiesLoading {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesLoading.class);

	public static final String ENV_PROPERTIES = "Env_Name";
	
	public String propertiesDefaultName = "application.properties";
	
	@Bean
	public PropertyPlaceholderConfigurer properties() {
		LOGGER.info("PropertiesLoading:::properties:::Method Start");
		String myenv = System.getenv(ENV_PROPERTIES);
		if(myenv != null) {
			propertiesDefaultName = "application-"+myenv+".properties";
		}
		LOGGER.info("PropertiesLoading:::propertiesDefaultName:::"+propertiesDefaultName);
		PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
		Resource[] resources = new ClassPathResource[] { new ClassPathResource(propertiesDefaultName) };
		ppc.setLocations(resources);
		ppc.setIgnoreUnresolvablePlaceholders(true);
		LOGGER.info("PropertiesLoading:::properties:::Method End");
		return ppc;
	}
	
	@Bean(name="processExecutor")
    public TaskExecutor workExecutor() {
		LOGGER.info("workExecutor:::properties:::Method Start");
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setThreadNamePrefix("MultiFile Executor-");
        threadPoolTaskExecutor.setCorePoolSize(6);
        threadPoolTaskExecutor.setMaxPoolSize(6);
        threadPoolTaskExecutor.setQueueCapacity(600);
        threadPoolTaskExecutor.afterPropertiesSet();
        LOGGER.info("workExecutor:::properties:::Method End");
        return threadPoolTaskExecutor;
    }
}
