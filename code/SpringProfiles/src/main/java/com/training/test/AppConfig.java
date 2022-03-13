package com.training.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableCaching
@ComponentScan({ "com.training.*" })
public class AppConfig {

	private static final Logger log = LoggerFactory.getLogger(AppConfig.class);
	
	@Bean
	@Profile("dev")
    public CacheManager concurrentMapCacheManager() {
		log.debug("Cache manager is concurrentMapCacheManager");
        return new ConcurrentMapCacheManager("movieFindCache");
    }
	
	@Bean
	@Profile("live")
	public CacheManager cacheManager() {
		log.debug("Cache manager is ehCacheCacheManager");
		return new EhCacheCacheManager(ehCacheCacheManager().getObject());
	}

	@Bean
	@Profile("live")
	public EhCacheManagerFactoryBean ehCacheCacheManager() {
		EhCacheManagerFactoryBean cmfb = new EhCacheManagerFactoryBean();
		cmfb.setConfigLocation(new ClassPathResource("ehcache.xml"));
		cmfb.setShared(true);
		return cmfb;
	}
	
}