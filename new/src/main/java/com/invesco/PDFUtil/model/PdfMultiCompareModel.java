package com.invesco.PDFUtil.model;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

public class PdfMultiCompareModel {
	
	private boolean matchCase;
	
	private boolean compareTextStyles;
	
	private boolean compareDiffOnly;
	
	private MultipartFile[] baseFile;
	
	private MultipartFile[] compareFile;
	
	@NotEmpty
	@NotBlank
	private String folderName;

	public boolean isMatchCase() {
		return matchCase;
	}

	public void setMatchCase(boolean matchCase) {
		this.matchCase = matchCase;
	}

	public boolean isCompareTextStyles() {
		return compareTextStyles;
	}

	public void setCompareTextStyles(boolean compareTextStyles) {
		this.compareTextStyles = compareTextStyles;
	}

	public boolean isCompareDiffOnly() {
		return compareDiffOnly;
	}

	public void setCompareDiffOnly(boolean compareDiffOnly) {
		this.compareDiffOnly = compareDiffOnly;
	}

	public MultipartFile[] getBaseFile() {
		return baseFile;
	}

	public void setBaseFile(MultipartFile[] baseFile) {
		this.baseFile = baseFile;
	}

	public MultipartFile[] getCompareFile() {
		return compareFile;
	}

	public void setCompareFile(MultipartFile[] compareFile) {
		this.compareFile = compareFile;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	
}
