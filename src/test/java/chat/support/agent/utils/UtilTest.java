package chat.support.agent.utils;

import dev.langchain4j.data.document.Document;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UtilTest {

    @Test
    void testGetDoc() throws IOException {
        Document doc = Util.getDoc();
        assertNotNull(doc, "O documento não deve ser nulo");
    }

    @Test
    void testGetDocs() throws IOException {
        List<Document> docs = Util.getDocs();
        assertNotNull(docs, "A lista de documentos não deve ser nula");
        assertTrue(docs.size() > 0, "A lista de documentos deve conter pelo menos um documento");
    }
}