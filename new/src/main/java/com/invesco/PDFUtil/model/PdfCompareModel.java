package com.invesco.PDFUtil.model;

import org.springframework.web.multipart.MultipartFile;

public class PdfCompareModel {

	private MultipartFile baseFile;

	private MultipartFile compareFile;
	
	private String matchCase;
	
	private String compareTextStyles;
	
	private Boolean compareDiffOnly;
	
	private Boolean storeinshare;
	
	private String shareFolder;

	public MultipartFile getBaseFile() {
		return baseFile;
	}

	public void setBaseFile(MultipartFile baseFile) {
		this.baseFile = baseFile;
	}

	public MultipartFile getCompareFile() {
		return compareFile;
	}

	public void setCompareFile(MultipartFile compareFile) {
		this.compareFile = compareFile;
	}

	public String getMatchCase() {
		return matchCase;
	}

	public void setMatchCase(String matchCase) {
		this.matchCase = matchCase;
	}

	public String getCompareTextStyles() {
		return compareTextStyles;
	}

	public void setCompareTextStyles(String compareTextStyles) {
		this.compareTextStyles = compareTextStyles;
	}

	public Boolean getCompareDiffOnly() {
		return compareDiffOnly;
	}

	public void setCompareDiffOnly(Boolean compareDiffOnly) {
		this.compareDiffOnly = compareDiffOnly;
	}

	public Boolean getStoreinshare() {
		return storeinshare;
	}

	public void setStoreinshare(Boolean storeinshare) {
		this.storeinshare = storeinshare;
	}

	public String getShareFolder() {
		return shareFolder;
	}

	public void setShareFolder(String shareFolder) {
		this.shareFolder = shareFolder;
	}

}
