package chat.support.agent.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {

	private String message;

	public void setMessage(String username, String messageText) {
		this.message = username.concat(": " + messageText);
	}
}
