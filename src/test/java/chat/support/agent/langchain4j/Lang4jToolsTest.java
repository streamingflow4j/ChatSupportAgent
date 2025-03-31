package chat.support.agent.langchain4j;

import chat.support.agent.exceptions.DenyAcessException;
import chat.support.agent.utils.RestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertThrows;

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
    void testDeleteRule() {
        String body = "test body";
        assertThrows(DenyAcessException.class, () -> {
            lang4jTools.deleteRule(body);
        });
    }

    @Test
    void testUpdateRule(){
        String body = "test body";
        assertThrows(DenyAcessException.class, () -> {
            lang4jTools.updateRule(body);
        });

    }

    @Test
    void testCreateRule(){
        String body = "test body";
        assertThrows(DenyAcessException.class, () -> {
            lang4jTools.createRule(body);
        });
    }

    @Test
    void testCreateEvent(){
        String body = "test body";
        assertThrows(DenyAcessException.class, () -> {
            lang4jTools.createEvent(body);
        });

    }

    @Test
    void testCreateData(){
        String body = "test body";
        assertThrows(DenyAcessException.class, () -> {
            lang4jTools.createData(body);
        });
    }
}