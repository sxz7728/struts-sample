package sample.core.exception;

public class NotLoginException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public NotLoginException() {
		super("not login");
	}
}
