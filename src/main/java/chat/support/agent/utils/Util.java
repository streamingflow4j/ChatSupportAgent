package chat.support.agent.utils;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;

import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocument;

public class Util {

    public static Document getDoc() throws IOException {
        String path = System.getProperty("user.dir") + "files/StreamingFlow4JAPI.txt";
        Document payloadAPI = loadDocument(path, new TextDocumentParser());
        return payloadAPI;
    }

    public static List<Document> getDocs() throws IOException {
        String path = System.getProperty("user.dir") + "files";
        List<Document> documents =
                FileSystemDocumentLoader.loadDocuments(path);

        return documents;
    }
}
