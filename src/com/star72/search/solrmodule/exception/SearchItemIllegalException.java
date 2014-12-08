package com.star72.search.solrmodule.exception;

/**
 * 查询条件不合法异常
 * 
 * @author wz
 *
 */
public class SearchItemIllegalException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8937076145325717045L;

	public SearchItemIllegalException() {
	}

	public SearchItemIllegalException(String message) {
		super(message);
	}

	public SearchItemIllegalException(Throwable cause) {
		super(cause);
	}

	public SearchItemIllegalException(String message, Throwable cause) {
		super(message, cause);
	}

}
