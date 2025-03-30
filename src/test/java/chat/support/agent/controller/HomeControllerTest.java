package chat.support.agent.controller;

import chat.support.agent.model.MessageForm;
import chat.support.agent.service.FileStoreService;
import chat.support.agent.service.MessageListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class HomeControllerTest {

    @Mock
    private MessageListService messageListService;

    @Mock
    private FileStoreService fileStoreService;

    @Mock
    private Model model;

    @Mock
    private MultipartFile fileUpload;

    @InjectMocks
    private HomeController homeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetHomePage() {
        MessageForm messageForm = new MessageForm();
        when(messageListService.getMessages()).thenReturn(new ArrayList<>());

        String viewName = homeController.getHomePage(messageForm, model);

        verify(model, times(1)).addAttribute(eq("greetings"), anyList());
        assertEquals("home", viewName);
    }

    @Test
    void testAddMessage() {
        MessageForm messageForm = new MessageForm();
        messageForm.setText("Hello, World!");
        when(messageListService.getMessages()).thenReturn(new ArrayList<>());

        String viewName = homeController.addMessage(messageForm, model);

        verify(messageListService, times(1)).addMessages("Hello, World!");
        verify(model, times(1)).addAttribute(eq("greetings"), anyList());
        assertEquals("home", viewName);
        assertEquals("", messageForm.getText());
    }

    @Test
    void testGetSimpleHomePage() {
        String viewName = homeController.getSimpleHomePage(model);

        verify(model, times(1)).addAttribute("firstVisit", "TRUE");
        assertEquals("simple-home", viewName);
    }

    @Test
    void testHandleFileUpload() {
        String viewName = homeController.handleFileUpload(fileUpload, model);

        verify(fileStoreService, times(1)).store(fileUpload);
        verify(model, times(1)).addAttribute("firstVisit", "TRUE");
        assertEquals("simple-home", viewName);
    }
}
