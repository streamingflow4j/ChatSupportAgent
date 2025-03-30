package chat.support.agent.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ChatFormTest {

    @Test
    void testChatFormConstructorAndGetters() {
        ChatForm chatForm = new ChatForm("user1", "Hello, World!", "text");

        assertEquals("user1", chatForm.getUsername());
        assertEquals("Hello, World!", chatForm.getMessageText());
        assertEquals("text", chatForm.getMessageType());
    }

    @Test
    void testChatFormSetters() {
        ChatForm chatForm = new ChatForm();
        chatForm.setUsername("user1");
        chatForm.setMessageText("Hello, World!");
        chatForm.setMessageType("text");

        assertEquals("user1", chatForm.getUsername());
        assertEquals("Hello, World!", chatForm.getMessageText());
        assertEquals("text", chatForm.getMessageType());
    }

    @Test
    void testNoArgsConstructor() {
        ChatForm chatForm = new ChatForm();

        assertNull(chatForm.getUsername());
        assertNull(chatForm.getMessageText());
        assertNull(chatForm.getMessageType());
    }
}
