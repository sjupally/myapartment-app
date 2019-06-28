package com.invesco.PDFUtil.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.desknetinc.reportingapptools.webservices.PDFCompareService;

public class MultiPdfCompareExecute implements Runnable {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MultiPdfCompareExecute.class);

	byte[] baseFileBytes = null;

	byte[] compareFileBytes = null;

	String fileName = null;

	byte[] configFileBytes = null;

	boolean isCompareDiffOnly = false;

	PDFCompareService pdfCompareService = null;

	String fileNameWithPath = null;

	Util util = null;
	

	public MultiPdfCompareExecute(byte[] baseFileBytes, byte[] compareFileBytes, String fileName, 
			byte[] configFileBytes, boolean isCompareDiffOnly, PDFCompareService pdfCompareService,
			String fileNameWithPath, Util util) {
		super();
		this.baseFileBytes = baseFileBytes;
		this.compareFileBytes = compareFileBytes;
		this.fileName = fileName;
		this.configFileBytes = configFileBytes;
		this.isCompareDiffOnly = isCompareDiffOnly;
		this.pdfCompareService = pdfCompareService;
		this.fileNameWithPath = fileNameWithPath;
		this.util = util;
	}


	@Override
	public void run() {
		LOGGER.info("MultiPdfCompareExecute:::run:::Method Start");
		try {
			byte[] responseBytes = pdfCompareService.compare(baseFileBytes, fileName, compareFileBytes, fileName, configFileBytes, isCompareDiffOnly);
			LOGGER.info("MultiPdfCompareExecute:::run:::fileNameWithPath:::::::" + fileNameWithPath);
			LOGGER.info("MultiPdfCompareExecute:::run:::responseBytes:::::::", responseBytes != null ? responseBytes.length : null);
			if (responseBytes != null && responseBytes.length > 0) {
				util.writeByteArrayToFile(responseBytes, fileNameWithPath);
			}
			LOGGER.info("MultiPdfCompareExecute:::run:::Method End");
		} catch (Exception e) {
			LOGGER.error("MultiPdfCompareExecute:::run:::Method exception    ",e);
			LOGGER.info("MultiPdfCompareExecute:::run:::Method End");
		}
	}

}
