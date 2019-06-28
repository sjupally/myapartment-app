package com.invesco.PDFUtil.desknetClient;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.desknetinc.reportingapptools.webservices.PDFCompareImplService;


@Component
public class PDFCompareClient {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PDFCompareClient.class);
	
	public PDFCompareImplService getPDFCompareImplObject(String wsdlLocation) throws MalformedURLException {
		LOGGER.info("PDFCompareClient:::getPDFCompareImplObject:::Method Start");
		URL url = getWSDLURL(wsdlLocation);
		QName qName = new QName("http://webservices.reportingapptools.desknetinc.com/", "PDFCompareImplService");
		PDFCompareImplService pdfCompareImplService = new PDFCompareImplService(url,qName);
		LOGGER.info("PDFCompareClient:::getPDFCompareImplObject:::Method End");
		return pdfCompareImplService;
	}
	
	private URL getWSDLURL(String wsdlLocation) throws MalformedURLException {
		LOGGER.info("PDFCompareClient:::getWSDLURL:::Method Start");
		URL baseUrl = com.desknetinc.reportingapptools.webservices.PDFCompareImplService.class.getResource(".");
		URL url = new URL(baseUrl, wsdlLocation);
		LOGGER.info("PDFCompareClient:::getWSDLURL:::Method End");
		return url;
	}
}
