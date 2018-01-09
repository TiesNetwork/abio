package one.utopic.abio.api.exception;

/**
 * Signals that a IO handler has been invoked at an illegal or inappropriate
 * time. In other words, the underlying IO device or channel is not in an
 * appropriate state for the requested operation.
 */
public class IOStateException extends IllegalStateException {

	private static final long serialVersionUID = -9183786040321212015L;

	public IOStateException() {
		super();
	}

	public IOStateException(String message, Throwable cause) {
		super(message, cause);
	}

	public IOStateException(String message) {
		super(message);
	}

	public IOStateException(Throwable cause) {
		super(cause);
	}

}
