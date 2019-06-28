package com.invesco.PDFUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class PdfUtilApplication extends SpringBootServletInitializer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PdfUtilApplication.class);

	public static void main(String[] args) {
		LOGGER.info("PdfUtilApplication:::main:::Method Start");
		SpringApplication.run(PdfUtilApplication.class, args);
		LOGGER.info("PdfUtilApplication:::main:::Method End");
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		LOGGER.info("PdfUtilApplication:::main:::Method Start End");
		return application.sources(PdfUtilApplication.class);
	}
}
