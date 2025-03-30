package chat.support.agent.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ChatMessageTest {

    @Test
    public void testChatMessageConstructorAndGetters() {
        ChatMessage chatMessage = new ChatMessage("Hello, World!");

        assertEquals("Hello, World!", chatMessage.getMessage());
    }

    @Test
    public void testChatMessageSetters() {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMessage("Hello, World!");

        assertEquals("Hello, World!", chatMessage.getMessage());
    }

    @Test
    public void testNoArgsConstructor() {
        ChatMessage chatMessage = new ChatMessage();

        assertNull(chatMessage.getMessage());
    }
}
