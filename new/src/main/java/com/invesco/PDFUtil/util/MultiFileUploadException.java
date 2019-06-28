package com.invesco.PDFUtil.util;


public class MultiFileUploadException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7978905043097803746L;
	
	private final ErrorCode code;

	public MultiFileUploadException(ErrorCode code) {
		super();
		this.code = code;
	}

	public MultiFileUploadException(String message, Throwable cause, ErrorCode code) {
		super(message, cause);
		this.code = code;
	}

	public MultiFileUploadException(String message, ErrorCode code) {
		super(message);
		this.code = code;
	}

	public MultiFileUploadException(Throwable cause, ErrorCode code) {
		super(cause);
		this.code = code;
	}
	
	public ErrorCode getCode() {
		return this.code;
	}
}
