package snap.ib.personelLoan.adapter.common.exception;

public class CommonException extends Exception {
	
	private static final long serialVersionUID = 1L;
	private String message;
	private String code;
	
	public CommonException() {
		super();
	}
	public CommonException(String message) {
		this.message = message;
	}
	public CommonException(String message, String code) {
		this.message = message;
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
