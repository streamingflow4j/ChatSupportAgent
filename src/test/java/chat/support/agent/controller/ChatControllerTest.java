package chat.support.agent.controller;

import chat.support.agent.model.ChatForm;
import chat.support.agent.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ChatControllerTest {

    @Mock
    private MessageService messageService;

    @Mock
    private Model model;

    @InjectMocks
    private ChatController chatController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetChatPage() {
        ChatForm chatForm = new ChatForm();
        when(messageService.getMessages()).thenReturn(new ArrayList<>());

        String viewName = chatController.getChatPage(chatForm, model);

        verify(model, times(1)).addAttribute(eq("chats"), anyList());
        assertEquals("chat", viewName);
    }

    @Test
    public void testPostChatMessage() throws IOException, ExecutionException, InterruptedException {
        ChatForm chatForm = new ChatForm();
        chatForm.setMessageText("Hello, World!");
        when(messageService.getMessages()).thenReturn(new ArrayList<>());

        String viewName = chatController.postChatMessage(chatForm, model);

        verify(messageService, times(1)).addMessages(chatForm);
        verify(model, times(1)).addAttribute(eq("chats"), anyList());
        assertEquals("chat", viewName);
        assertEquals("", chatForm.getMessageText());
    }
}
