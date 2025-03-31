package chat.support.agent.langchain4j;

import chat.support.agent.exceptions.DenyAcessException;
import chat.support.agent.utils.RestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

class Lang4jToolsTest {

    private Lang4jTools lang4jTools;

    @Mock
    private RestUtil restUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        lang4jTools = new Lang4jTools("http://example.com");
    }

    @Test
    void testDeleteRule() throws Exception {
        String body = "test body";
        assertThrows(DenyAcessException.class, () -> {
            lang4jTools.deleteRule(body);
        });
    }

    @Test
    void testUpdateRule() throws Exception {
        String body = "test body";
        assertThrows(DenyAcessException.class, () -> {
            lang4jTools.updateRule(body);
        });

    }

    @Test
    void testCreateRule() throws Exception {
        String body = "test body";
        assertThrows(DenyAcessException.class, () -> {
            lang4jTools.createRule(body);
        });
    }

    @Test
    void testCreateEvent() throws Exception {
        String body = "test body";
        assertThrows(DenyAcessException.class, () -> {
            lang4jTools.createEvent(body);
        });

    }

    @Test
    void testCreateData() throws Exception {
        String body = "test body";
        assertThrows(DenyAcessException.class, () -> {
            lang4jTools.createData(body);
        });
    }
}