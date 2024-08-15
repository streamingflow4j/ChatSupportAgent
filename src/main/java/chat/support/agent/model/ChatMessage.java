package chat.support.agent.model;

public class ChatMessage {

	private String message;

	/*
	 * public ChatMessage(String username, String messageText) { this.message =
	 * username.concat(": " + messageText); }
	 */

	public String getMessage() {
		return message;
	}
	public void setMessage(String username, String messageText) {
		this.message = username.concat(": " + messageText);
	}
}
