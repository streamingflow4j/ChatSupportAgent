package chat.support.agent.exceptions;

public class DenyAcessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DenyAcessException(String msg) {
		super(msg);
	}
	
	public DenyAcessException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
