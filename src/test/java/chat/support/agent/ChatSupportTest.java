package chat.support.agent;

import chat.support.agent.langchain4j.AiModelFactory;
import chat.support.agent.langchain4j.Lang4jTools;
import chat.support.agent.langchain4j.LangChain4jAssistant;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocument;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ChatSupportTest {
    // @Mock
    // StreamingChatLanguageModel model;

    @Test
    public void myTestOK() {

        ChatLanguageModel model = AiModelFactory.createOpenAIChatModel();
        // ChatLanguageModel model = AiModelFactory.createLocalChatModel();

        LangChain4jAssistant assistant = AiServices.builder(LangChain4jAssistant.class)
                .chatLanguageModel(model)
                .tools(new Lang4jTools())
                .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
                //  .contentRetriever(contentRetriever) //camada do rag de coumentos
                //.contentRetriever(EmbeddingStoreContentRetriever.from(embeddingStore))
                .build();
        String token = assistant.chat("Create an Event called Thermometer with attributes id and temperature");
        System.out.println(token);
        assertNotNull(token);
    }

    public Document getDoc() throws IOException, URISyntaxException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext();
        String fileResource2 = applicationContext.getResource("classpath:files").getFile().getCanonicalPath();
        String path = fileResource2.replace("\\", "\\\\");
         List<Document> payloadAPI = FileSystemDocumentLoader.loadDocuments(path.toString());
        return payloadAPI.get(0);
    }

    @Test
    public void customerServiceAgent() throws IOException, URISyntaxException {
      //  ChatLanguageModel model = AiModelFactory.createOpenAIChatModel();
        ChatLanguageModel model = AiModelFactory.createLocalChatModel();
        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
        //2 - em memoria carrega o vetor embedando os documentos
        EmbeddingStoreIngestor.ingest(getDoc(), embeddingStore);

        LangChain4jAssistant langChain4jAssistant = AiServices.builder(LangChain4jAssistant.class)
                .chatLanguageModel(model)//.streamingChatLanguageModel(streamingChatLanguageModel)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
                /*
                .chatMemoryProvider(
                        chatID -> TokenWindowChatMemory.builder()
                                .id(chatID)
                                .maxToken(500, tokenizer)
                                .build())*/
                .tools(new Lang4jTools())
                .contentRetriever(EmbeddingStoreContentRetriever.from(embeddingStore))
                .build();
        String token = langChain4jAssistant.chat("Create an Event called Thermometer with attributes id and temperature");
        System.out.println(token);
        assertNotNull(token);

    }
}
