package com.invesco.PDFUtil.util;

public enum MultiFileUploadCode implements ErrorCode {
	NUMBEROFFILESNOTMATCHING(101), FILENAMEARENOTEQUAL(102);

	private final int number;

	private MultiFileUploadCode(int number) {
		this.number = number;
	}

	@Override
	public int getNumber() {
		return number;
	}

}
