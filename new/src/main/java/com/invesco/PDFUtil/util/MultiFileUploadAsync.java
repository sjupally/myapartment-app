package com.invesco.PDFUtil.util;

import java.net.MalformedURLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.desknetinc.reportingapptools.webservices.PDFCompareImplService;
import com.desknetinc.reportingapptools.webservices.PDFCompareService;
import com.invesco.PDFUtil.desknetClient.PDFCompareClient;

@Component
public class MultiFileUploadAsync {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MultiFileUploadAsync.class);

	@Autowired
	private PDFCompareClient pdfCompareClient;
	
	@Autowired
	private Util util;

	@Value("${filelocation}")
	private String sharedDriveLocation;

	@Value("${wsdlpath}")
	private String wsdlpath;
	
	@Async
	public void multiFilePDFCompare(Map<String, List<byte[]>> filesCompareMap, byte[] configFileBytes, boolean isCompareDiffOnly, String folderName) {
		LOGGER.info("MultiFileUploadAsync:::multiFilePDFCompare:::Method Start");
		try {
			PDFCompareImplService pdfCompareImplService = pdfCompareClient.getPDFCompareImplObject(wsdlpath);
			PDFCompareService pdfCompareService = pdfCompareImplService.getPDFCompareImplPort();
			ExecutorService executorService =  Executors.newFixedThreadPool(filesCompareMap.size());
			for (String fileName : filesCompareMap.keySet()) {
				byte[] baseFileBytes = filesCompareMap.get(fileName).get(0);
				byte[] compareFileBytes = filesCompareMap.get(fileName).get(1);
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				final SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
				String fileNameWithPath = sharedDriveLocation+folderName +"\\" + sdf.format(timestamp)+"_"+fileName;
				MultiPdfCompareExecute multiPdfCompareExecute = new MultiPdfCompareExecute(baseFileBytes, compareFileBytes, fileName, configFileBytes, isCompareDiffOnly, pdfCompareService, fileNameWithPath, util);
				executorService.execute(multiPdfCompareExecute);
			}
			executorService.shutdown();
		} catch (MalformedURLException e) {
			LOGGER.info("MultiFileUploadAsync:::multiFilePDFCompare:::Exception Block", e);
		}
		LOGGER.info("MultiFileUploadAsync:::multiFilePDFCompare:::Method End");
	}
}
