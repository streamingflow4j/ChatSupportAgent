package chat.support.agent.service;

import chat.support.agent.model.ChatForm;
import chat.support.agent.model.ChatMessage;
import chat.support.agent.utils.Util;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MessageServiceTest {

    private MessageService messageService;

    @Mock
    private Util util;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        messageService = new MessageService();
        messageService.postConstruct();
    }

    @Test
    void testGetMessages() {
        List<ChatMessage> messages = messageService.getMessages();
        assertNotNull(messages);
        assertTrue(messages.isEmpty());
    }

    @Test
    void testGetEmbeddingStore() throws IOException {
        InMemoryEmbeddingStore<TextSegment> embeddingStore = messageService.getEmbedingStore();
        assertNotNull(embeddingStore);
    }

}
