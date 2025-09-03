package chat.support.agent.utils;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentParser;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.data.document.parser.apache.poi.ApachePoiDocumentParser;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.testcontainers.shaded.com.google.common.io.MoreFiles.getFileExtension;

public class FileUtil {

    public static Document getDoc() throws IOException {

        String path = "";
        Document payloadAPI = null;
        try {
            path = System.getProperty("user.dir") + "/target/classes/files/StreamingFlow4JAPI.txt";
            payloadAPI = parse(Path.of(path));
        }catch (Exception e) {
            //docker server path
            path = "/files/StreamingFlow4JAPI.pdf";
            payloadAPI = parse(Path.of(path));
        }
        return payloadAPI;
    }

    public static List<Document> getDocs() throws IOException {
        List<Document> documents = null;
        String path = "";
        try {
            path = System.getProperty("user.dir") + "/target/classes/files";
            documents = FileSystemDocumentLoader.loadDocuments(path);
        } catch (Exception e) {
            //docker server path
            path = "/files";
            documents = FileSystemDocumentLoader.loadDocuments(path);
        }

        return documents;
    }

    public static Document parse(Path documentPath) {
        String extension = getFileExtension(documentPath);
        DocumentParser documentParser;
        switch (extension) {
            case "pdf":
                documentParser = new ApachePdfBoxDocumentParser();
                break;
            case "doc", "xlsx", "docx", "xls", "ppt", "pptx":
                documentParser = new ApachePoiDocumentParser();
                break;
            default:
                documentParser = new TextDocumentParser();
        }
        return FileSystemDocumentLoader.loadDocument(documentPath, documentParser);
    }
}
