package com.invesco.PDFUtil.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import com.invesco.PDFUtil.util.*;
import org.jdom2.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.desknetinc.reportingapptools.webservices.PDFCompareImplService;
import com.desknetinc.reportingapptools.webservices.PDFCompareService;
import com.invesco.PDFUtil.desknetClient.PDFCompareClient;
import com.invesco.PDFUtil.model.PdfCompareModel;
import com.invesco.PDFUtil.model.PdfMultiCompareModel;

@Controller
public class PdfUtilController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PdfUtilController.class);

	@Autowired
	private PDFCompareClient pdfCompareClient;
	
	@Autowired
	private Util util;
	
	@Autowired
	private MultiFileUploadAsync multiFileUploadAsync;

	@Value("${filelocation}")
	private String sharedDriveLocation;

	@Value("${wsdlpath}")
	private String wsdlpath;

	@Value("${cacheburst}")
	private String cacheburst;

	/**
	 * This method will return the initial landing page of the file upload
	 * @return
	 */
	@PreAuthorize("hasAnyRole('CORP\\_HYD- GCCP QUALITY ASSURANCE', 'CORP\\_GBL- IT CLIENT REPORTING ALL')")
	@RequestMapping(value= {"/","/fileUpload"})
	public String fileUpload(Map<String, Object> model, Authentication auth) {
		LOGGER.info("PdfMergeController:::fileUpload:::Method Start");
		model.put("cacheburst", cacheburst);
		model.put("sharedLocation", sharedDriveLocation.replaceAll("//", ""));
		model.put("loggedinuser", auth.getPrincipal());
		LOGGER.info("PdfMergeController:::fileUpload:::Method End");
		return "fileUpload";
	}
	
	/**
	 * This method used for landing page for multiple file upload
	 * @param model
	 * @param auth
	 * @return
	 */
	@PreAuthorize("hasAnyRole('CORP\\_HYD- GCCP QUALITY ASSURANCE', 'CORP\\_GBL- IT CLIENT REPORTING ALL')")
	@RequestMapping("/multiFileUpload")
	public String multipleFilesUpload(Map<String, Object> model, Authentication auth) {
		LOGGER.info("PdfMergeController:::multipleFilesUpload:::Method Start");
		model.put("cacheburst", cacheburst);
		model.put("sharedLocation", sharedDriveLocation.replaceAll("//", ""));
		model.put("loggedinuser", auth.getPrincipal());
		LOGGER.info("PdfMergeController:::multipleFilesUpload:::Method End");
		return "multiFileUpload";
	}

	/**
	 * This method is used to redirect the unauthorized user to accessDenied page 
	 * @param model
	 * @return
	 */
	@RequestMapping("/accessDenied")
	public String accessDenied(Map<String, Object> model) {
		LOGGER.info("PdfMergeController:::accessDenied:::Method Start End");
		return "accessDenied";
	}
	
	/**
	 * This method is responsible to compare the two PDF files
	 * @param pdfCompareModel
	 * @return
	 */
	@PreAuthorize("hasAnyRole('CORP\\_HYD- GCCP QUALITY ASSURANCE', 'CORP\\_GBL- IT CLIENT REPORTING ALL')")
	@RequestMapping(value="/compareFile", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> compareFile (@ModelAttribute PdfCompareModel pdfCompareModel) {
		LOGGER.info("PdfMergeController:::compareFile:::Method Start");
		byte[] responseBytes = null;
		String fileName = null;
		try {
			byte[] configFileBytes = util.modifyConfigXML(pdfCompareModel.getMatchCase(), pdfCompareModel.getCompareTextStyles());
			boolean compareDiffOnlyValue = pdfCompareModel.getCompareDiffOnly();
			responseBytes = getPdfCompareService().compare(pdfCompareModel.getBaseFile().getBytes(), pdfCompareModel.getBaseFile().getName(), pdfCompareModel.getCompareFile().getBytes(), pdfCompareModel.getCompareFile().getName(), configFileBytes, compareDiffOnlyValue);
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
			fileName = sdf.format(timestamp)+"_Output.pdf";
			String fileNameWithPath = util.getFileName(false, fileName, null);
			LOGGER.info("PdfMergeController:::compareFile:::fileNameWithPath:::::::"+fileNameWithPath);
			if(responseBytes != null && responseBytes.length > 0) {
				LOGGER.info("PdfMergeController:::compareFile:::responseBytes:::::::"+responseBytes.length);
				util.writeByteArrayToFile(responseBytes, fileNameWithPath);
				if(pdfCompareModel.getStoreinshare()) {
					fileNameWithPath = util.getFileName(true, fileName, pdfCompareModel.getShareFolder());
				    LOGGER.info("PdfMergeController:::compareFile:::remotefileNameWithPath:::::::"+fileNameWithPath);
				    util.writeByteArrayToFile(responseBytes, fileNameWithPath);
				}
			}else {
				return ResponseEntity.ok().body(compareDiffOnlyValue ? "BOTHFILESHASSAMEDATA" : "Problem while generating file from desknet.");
			}
		} catch (IOException e) {
			LOGGER.info("PdfMergeController:::compareFile:::IOException Block", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}  catch (JDOMException e) {
			LOGGER.info("PdfMergeController:::compareFile:::JDOMException Block", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		LOGGER.info("PdfMergeController:::compareFile:::Method End");
		return ResponseEntity.ok().body(fileName);
	}	

	/**
	 * This method is responsible to download the file
	 * @param fileName
	 * @return
	 */
	@RequestMapping(value = "/downloadFile", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam("fileName") String fileName) {
		LOGGER.info("PdfMergeController:::downloadFile:::Method Start");
		ByteArrayResource resource = null;
		byte[] responseBytes = null;
		try {
			String fileNameWithPath = util.getFileName(true, fileName, null);
			responseBytes = util.readFile(fileNameWithPath);
			resource = new ByteArrayResource(responseBytes);
		} catch (IOException e) {
			LOGGER.info("PdfMergeController:::downloadFile:::Exception Block", e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		LOGGER.info("PdfMergeController:::downloadFile:::Method End");
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION).contentType(MediaType.APPLICATION_PDF).contentLength(responseBytes.length).body(resource);
	}
	
	/**
	 * This method is used to compare the set of PDF in async way
	 * @param auth
	 * @param model
	 * @param pdfMultiCompareModel
	 * @return
	 */
	@PreAuthorize("hasAnyRole('CORP\\_HYD- GCCP QUALITY ASSURANCE', 'CORP\\_GBL- IT CLIENT REPORTING ALL')")
	@RequestMapping(value = "/multiFileCompare", method = RequestMethod.POST)
	public String uploadMutliFileCompare(Authentication auth, Map<String, Object> model, @ModelAttribute PdfMultiCompareModel pdfMultiCompareModel) {
		LOGGER.info("PdfMergeController:::uploadMutliFileCompare:::Method Start");
		try {
			if (pdfMultiCompareModel != null) {
				util.validateFileLengthAndNames(pdfMultiCompareModel);
				byte[] configFileBytes = util.modifyConfigXML(String.valueOf(pdfMultiCompareModel.isMatchCase()),String.valueOf(pdfMultiCompareModel.isCompareTextStyles()));
				Path path = Paths.get(sharedDriveLocation+pdfMultiCompareModel.getFolderName());
				if (!Files.exists(path)) {
					Files.createDirectory(path);
				}
				Map<String, List<byte[]>> filesUploadedMap = util.mapFilesToCompare(pdfMultiCompareModel);
				if(filesUploadedMap != null && filesUploadedMap.size() > 0) {
					multiFileUploadAsync.multiFilePDFCompare(filesUploadedMap, configFileBytes, pdfMultiCompareModel.isCompareDiffOnly(), pdfMultiCompareModel.getFolderName());
				}
			}
		} catch (MultiFileUploadException e) {
			if(e.getCode().getNumber() == MultiFileUploadCode.NUMBEROFFILESNOTMATCHING.getNumber()) {
				model.put("errormsg", "From and to files length are miss matching");
			}
			if(e.getCode().getNumber() == MultiFileUploadCode.FILENAMEARENOTEQUAL.getNumber()) {
				model.put("errormsg", "From and to files name are miss matching");
			}
			LOGGER.info("PdfMergeController:::uploadMutliFileCompare:::MultiFileUploadException Block", e.getCode());
		}catch (IOException e) {
			LOGGER.info("PdfMergeController:::uploadMutliFileCompare:::IOException Block", e);
		} catch (JDOMException e) {
			LOGGER.info("PdfMergeController:::uploadMutliFileCompare:::JDOMException Block", e);
		}
		model.put("sharedLocation", sharedDriveLocation.replaceAll("//", ""));
		model.put("loggedinuser", auth.getPrincipal());
		LOGGER.info("PdfMergeController:::uploadMutliFileCompare:::Method End");
		return "multiFileUpload";
	}

	public PDFCompareService getPdfCompareService() throws MalformedURLException {
		PDFCompareImplService pdfCompareImplService = pdfCompareClient.getPDFCompareImplObject(wsdlpath);
		return pdfCompareImplService.getPDFCompareImplPort();
	}
}
