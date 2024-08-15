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
    private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext();
    public static Document getDoc() throws IOException {
       // Resource fileResource = applicationContext.getResource("classpath:StreamingFlow4JAPI.txt");
        String fileResource2 = applicationContext.getResource("classpath:files/StreamingFlow4JAPI.txt").getFile().getCanonicalPath();
        String path = fileResource2.replace("\\", "\\\\");
        Document payloadAPI = loadDocument(path.toString(), new TextDocumentParser());
        return payloadAPI;
    }

    public static List<Document> getDocs() throws IOException {

        String fileResource2 = applicationContext.getResource("classpath:files").getFile().getCanonicalPath();
        String path = fileResource2.replace("\\", "\\\\");
        List<Document> documents =
                FileSystemDocumentLoader.loadDocuments(path.toString());

        return documents;
    }
}
