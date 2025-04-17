package chat.support.agent.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {

	private String role;
	private String message;
	private LocalDate timestamp;

	public void addMessage(String username, String messageText) {
		this.message = username.concat(": " + messageText);
	}
}
