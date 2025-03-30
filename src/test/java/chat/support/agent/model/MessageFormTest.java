package chat.support.agent.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MessageFormTest {

    @Test
    void testMessageFormConstructorAndGetters() {
        MessageForm messageForm = new MessageForm("Hello, World!");

        assertEquals("Hello, World!", messageForm.getText());
    }

    @Test
    void testMessageFormSetters() {
        MessageForm messageForm = new MessageForm();
        messageForm.setText("Hello, World!");

        assertEquals("Hello, World!", messageForm.getText());
    }

    @Test
    void testNoArgsConstructor() {
        MessageForm messageForm = new MessageForm();

        assertNull(messageForm.getText());
    }
}
