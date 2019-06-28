package com.invesco.PDFUtil.util;

import com.invesco.PDFUtil.model.PdfMultiCompareModel;
import org.apache.commons.io.FilenameUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Util {

	private static final Logger LOGGER = LoggerFactory.getLogger(Util.class);

	public static final String DESK_NET_CONFIG_XML = "filecompareconfig.xml";
	
	@Value("${filelocation}")
	private String sharedDriveLocation;

	/**
	 * This method will make the changes of desknet config changes and read the
	 * configuration file
	 * 
	 * @param compareTextValue
	 * @param compareTextStyles
	 * @return configuration file as byte array
	 * @throws IOException
	 * @throws JDOMException
	 */
	public synchronized byte[] modifyConfigXML(String compareTextValue, String compareTextStyles)
			throws IOException, JDOMException {
		LOGGER.info("Util:::modifyConfigXML:::Method Start");
		File file = new ClassPathResource(DESK_NET_CONFIG_XML).getFile();
		String filePath = file.getAbsolutePath();
		Document doc = (Document) new SAXBuilder().build(file);
		List<Element> listOfEntries = doc.getRootElement().getChildren("entry");
		for (final Element element : listOfEntries) {
			if ("COMPARE_TEXT_CASE_SENSITIVE".equals(element.getAttribute("key").getValue())) {
				element.setText(compareTextValue);
			}
			if ("COMPARE_TEXT_STYLES".equals(element.getAttribute("key").getValue())) {
				element.setText(compareTextStyles);
			}
		}
		final XMLOutputter xmlOutputter = new XMLOutputter();
		xmlOutputter.setFormat(Format.getPrettyFormat());
		Writer writer = null;
		byte[] configResponseBytes = null;
		try {
			writer = new FileWriter(filePath);
			xmlOutputter.output(doc, writer);
			configResponseBytes = readConfigFile();
			LOGGER.info("Util:::modifyConfigXML:::Method End");
		} catch (IOException ex) {
			LOGGER.error("Util:::modifyConfigXML:::Exception Block", ex);
			throw new IOException();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					LOGGER.error("Util:::modifyConfigXML:::Exception Block resource free", e);
				}
			}
		}
		return configResponseBytes;
	}

	/**
	 * This method will responsible to write byte array to given file location with
	 * name.
	 * 
	 * @param bFile
	 * @param fileNameWithPath
	 * @throws IOException
	 */
	public void writeByteArrayToFile(byte[] bFile, String fileNameWithPath) throws IOException {
		LOGGER.info("Util:::writeByteArrayToFile:::Method Start");
		FileOutputStream fileOuputStream = null;
		File file = null;
		try {
			//String path = FilenameUtils.normalize(fileNameWithPath.replace("//", ""));
			file = new File(fileNameWithPath);
			if (!file.exists()) {
				file.createNewFile();
			}
			fileOuputStream = new FileOutputStream(file);
			fileOuputStream.write(bFile);
		} catch (IOException e) {
			LOGGER.error("Util:::writeByteArrayToFile:::Exception Block", e);
			throw new IOException();
		} finally {
			if (fileOuputStream != null) {
				try {
					fileOuputStream.close();
				} catch (IOException e) {
					LOGGER.error("Util:::writeByteArrayToFile:::Exception Block resource free", e);
				}
			}
		}
		LOGGER.info("Util:::writeByteArrayToFile:::Method End");
	}

	/**
	 * In This method we are checking the file array length and names are same
	 * 
	 * @param pdfMultiCompareModel
	 * @return
	 * @throws MultiFileUploadException 
	 */
	public void validateFileLengthAndNames(PdfMultiCompareModel pdfMultiCompareModel) throws MultiFileUploadException {
		LOGGER.info("Util:::validateFileLengthAndNames:::Method Start");
		MultipartFile[] baseFiles = pdfMultiCompareModel.getBaseFile();
		MultipartFile[] compareFiles = pdfMultiCompareModel.getCompareFile();
		if ((baseFiles != null && baseFiles.length > 0) && (compareFiles != null && compareFiles.length > 0)
				&& (compareFiles.length == baseFiles.length)) {
			String[] baseFileNames = new String[baseFiles.length];
			String[] compareFileNames = new String[compareFiles.length];
			for (int loop = 0; loop < baseFileNames.length; loop++) {
				baseFileNames[loop] = baseFiles[loop].getOriginalFilename();
			}
			for (int loop = 0; loop < compareFileNames.length; loop++) {
				compareFileNames[loop] = compareFiles[loop].getOriginalFilename();
			}
			if(!Arrays.equals(baseFileNames, compareFileNames)) {
				throw new MultiFileUploadException("File Names are miss matching to compare", MultiFileUploadCode.FILENAMEARENOTEQUAL);
			}
		} else {
			throw new MultiFileUploadException("Compare files length are miss match", MultiFileUploadCode.NUMBEROFFILESNOTMATCHING);
		}
		LOGGER.info("Util:::validateFileLengthAndNames:::Method End");
	}

	/**
	 * This method is used to store the files data to map against to given files
	 * @param pdfMultiCompareModel
	 * @return
	 * @throws IOException
	 */
	public Map<String, List<byte[]>> mapFilesToCompare(PdfMultiCompareModel pdfMultiCompareModel) throws IOException {
		LOGGER.info("Util:::mapFilesToCompare:::Method Start");
		MultipartFile[] baseFiles = pdfMultiCompareModel.getBaseFile();
		MultipartFile[] compareFiles = pdfMultiCompareModel.getCompareFile();
		Map<String, List<byte[]>> filesMap = new HashMap<>();
		for (int baseFileLoop = 0; baseFileLoop < baseFiles.length; baseFileLoop++) {
			String baseFileName = FilenameUtils.getName(baseFiles[baseFileLoop].getOriginalFilename());
			for (int compareFileLoop = 0; compareFileLoop < compareFiles.length; compareFileLoop++) {
				String compareFileName = FilenameUtils.getName(compareFiles[compareFileLoop].getOriginalFilename());
				if (baseFileName.equals(compareFileName)) {
					List<byte[]> inputFileData = new ArrayList<>();
					inputFileData.add(baseFiles[baseFileLoop].getBytes());
					inputFileData.add(compareFiles[compareFileLoop].getBytes());
					filesMap.put(baseFileName, inputFileData);
					break;
				}
			}
		}
		LOGGER.info("Util:::mapFilesToCompare:::Method End");
		return filesMap;
	}

	/**
	 * This method will read the desknet configuration file
	 * 
	 * @return the byte array of deksnet configuration file
	 * @throws IOException
	 */
	private byte[] readConfigFile() throws IOException {
		LOGGER.info("Util:::readConfigFile:::Method Start");
		FileInputStream fileInputStream = null;
		byte[] bytesArray = null;
		try {
			final Resource resource = new ClassPathResource(DESK_NET_CONFIG_XML);
			File file = resource.getFile();
			bytesArray = new byte[(int) file.length()];
			fileInputStream = new FileInputStream(file);
			int sz;
			while ((sz = fileInputStream.read(bytesArray)) != -1) {
				return bytesArray;
			}
		} catch (IOException e) {
			LOGGER.error("Util:::readConfigFile:::Exception Block", e);
			throw new IOException();
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					LOGGER.error("Util:::readConfigFile:::Exception Block resource free", e);
				}
			}
		}
		LOGGER.info("Util:::readConfigFile:::Method End");
		return bytesArray;
	}

	/**
	 * This method is responsible to read a file in the given path
	 * @param fileNameWithPath
	 * @return
	 * @throws IOException
	 */
	public byte[] readFile(String fileNameWithPath) throws IOException {
		LOGGER.info("Util:::readFile:::Method Start");
		File file = new File(fileNameWithPath);
		FileInputStream fin = null;
		byte[] fileContent = null;
		try {
			fin = new FileInputStream(file);
			fileContent = new byte[(int) file.length()];
			int sz;
			while ((sz = fin.read(fileContent)) != -1) {
				return fileContent;
			}
		} catch (FileNotFoundException e) {
			LOGGER.error("Util:::readConfigFile:::Exception Block", e);
			throw new FileNotFoundException();
		} catch (IOException ioe) {
			LOGGER.error("Util:::readConfigFile:::Exception Block", ioe);
			throw new IOException();
		} finally {
			// close the streams using close method
			try {
				if (fin != null) {
					fin.close();
				}
			} catch (IOException ioe) {
				LOGGER.error("Util:::readConfigFile:::Exception Block", ioe);
			}
		}
		LOGGER.info("Util:::readFile:::Method End");
		return fileContent;
	}
	
	/**
	 *  This method is used to delete the file from the input
	 * @param fileNameWithPath
	 */
	public void deleteFile(String fileNameWithPath) {
		LOGGER.info("Util:::deleteFile:::Method Start");
		try {
			File file=new File(fileNameWithPath);
			file = file.getCanonicalFile();
			if(file.exists()){
				LOGGER.info("Util:::deleteFile:::Method delete start");
				boolean isDeleted = file.delete();
				LOGGER.info("Util:::deleteFile:::Method End :: File deleted " + isDeleted);
			}
		} catch (IOException e) {
			LOGGER.error("Util:::deleteFile:::Method delete Error while deleting file"+fileNameWithPath);
		}
	}
	
	/** This method will return the file name along with path based on the input
	 * This method 
	 * @param storeinshare
	 * @param fileName
	 * @param shareFolder
	 * @return
	 * @throws IOException
	 */
	public String getFileName(boolean storeinshare,String fileName, String shareFolder) throws IOException {
		LOGGER.info("Util:::getFileName:::Method Start");
		if(storeinshare) {
			LOGGER.info("Util:::getFileName:::storeinshare "+storeinshare);
			if(!StringUtils.isEmpty(shareFolder)) {
				Path path = Paths.get(sharedDriveLocation+shareFolder);
				if (!Files.exists(path)) {
					Files.createDirectory(path);
				}
				LOGGER.info("Util:::getFileName:::shareFolder "+shareFolder);
				LOGGER.info("Util:::getFileName:::Method End");
				return sharedDriveLocation+shareFolder +"\\" + fileName;
			}
			LOGGER.info("Util:::getFileName:::Method End");
			return sharedDriveLocation + fileName;
		}else {
			String defaultTmp = System.getProperty("java.io.tmpdir");
			String strFileName = FilenameUtils.normalize(defaultTmp+"\\pdfcompare");
			Path path = Paths.get(strFileName).normalize();
			if (!Files.exists(path)) {
				Files.createDirectory(path);
			}
			LOGGER.info("Util:::getFileName:::defaultTmp "+defaultTmp);
			LOGGER.info("Util:::getFileName:::Method End");
			return defaultTmp+"\\pdfcompare" +"\\"+ fileName;
		}
	}
}
