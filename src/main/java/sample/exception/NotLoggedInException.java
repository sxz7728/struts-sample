package sample.exception;

public class NotLoggedInException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public NotLoggedInException() {
		super("not logged in");
	}
}
