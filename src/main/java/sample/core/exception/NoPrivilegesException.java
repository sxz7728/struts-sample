package sample.core.exception;

public class NoPrivilegesException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public NoPrivilegesException() {
		super("no privileges");
	}
}
