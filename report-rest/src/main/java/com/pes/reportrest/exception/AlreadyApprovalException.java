package com.pes.reportrest.exception;

/**
 * @author huston.peng
 * @version 1.3.0
 * @date 5/7/21
 */
public class AlreadyApprovalException extends RuntimeException {
  public AlreadyApprovalException() {
    super();
  }

  public AlreadyApprovalException(String message) {
    super(message);
  }

  public AlreadyApprovalException(String message, Throwable cause) {
    super(message, cause);
  }

  public AlreadyApprovalException(Throwable cause) {
    super(cause);
  }

  protected AlreadyApprovalException(String message, Throwable cause, boolean enableSuppression,
                                     boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
