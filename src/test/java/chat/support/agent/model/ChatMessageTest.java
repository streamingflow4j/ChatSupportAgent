package chat.support.agent.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ChatMessageTest {

    @Test
    void testChatMessageConstructorAndGetters() {
        ChatMessage chatMessage = new ChatMessage("Role","Hello, World!", LocalDate.now());

        assertEquals("Hello, World!", chatMessage.getMessage());
    }

    @Test
    void testChatMessageSetters() {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMessage("Hello, World!");
        assertEquals("Hello, World!", chatMessage.getMessage());
        chatMessage.addMessage("testUser", "Hello, World!");
        assertEquals("testUser: Hello, World!", chatMessage.getMessage());
    }

    @Test
    void testNoArgsConstructor() {
        ChatMessage chatMessage = new ChatMessage();

        assertNull(chatMessage.getMessage());
    }
}
