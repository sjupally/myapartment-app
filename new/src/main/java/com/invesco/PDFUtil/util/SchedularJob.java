package com.invesco.PDFUtil.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.AgeFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedularJob {

	private static final Logger LOGGER = LoggerFactory.getLogger(SchedularJob.class);

	private final static ThreadLocal<DateFormat> dateFormat = new ThreadLocal<DateFormat>() {
		@Override
		public DateFormat initialValue() {
			return new SimpleDateFormat("HH:mm:ss");
		}
	};
	@Scheduled(cron = "	0 0/15 * 1/1 * ?")
	public void cleanTempDir() {
		LOGGER.info("SchedularJob:::cleanTempDir:::Method Start");
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MINUTE, -15);
		Date minusFifteenMin = now.getTime();
		LOGGER.info("SchedularJob::::cleanTempDir:::the time is " + dateFormat.get().format(minusFifteenMin));
		FileFilter fileFilter = new AgeFileFilter(now.getTime());
		String defaultTmp = System.getProperty("java.io.tmpdir");
		LOGGER.info("SchedularJob:::defaultTmp:::" + defaultTmp);
		String path = FilenameUtils.normalize(defaultTmp + File.separator + "pdfcompare");
		File directory = new File(path);
		File[] filesInsideDir = directory.listFiles(fileFilter);
		if (filesInsideDir != null) {
			LOGGER.info("SchedularJob:::filesInsideDir.length:::" + filesInsideDir.length);
			for (File file : filesInsideDir) {
				LOGGER.info("SchedularJob:::cleanTempDir:::file   " + file.getName());
				Path fileToDelete = new File(file.getName()).toPath();
				try {
					Files.delete(fileToDelete);
				} catch (IOException x) {
					LOGGER.error("SchedularJob:::cleanTempDir:::Error while deleting  file   " + file.getName(), x);
				}
			}
		}
		LOGGER.info("SchedularJob:::cleanTempDir:::Method End");
	}
}
