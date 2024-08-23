package chat.support.agent.langchain4j;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.time.Duration;

public class AiModelFactory {
    @Autowired
    private static String getUserBucketPath;
    private AiModelFactory() {

    }
    public ChatLanguageModel createLocalChatOllamaModel() {
        return  OllamaChatModel.builder()
                .baseUrl("http://172.21.0.3:11434/")
                //.baseUrl("http://172.21.0.3:11434/")
                .modelName("gemma:2b")
                .temperature(0.8)
                .build();
    }

    //-------------------  StreamingChat Models ----------------
    public static StreamingChatLanguageModel createLocalOllamaStreamingChatModel() {
        return OllamaStreamingChatModel.builder()
                .baseUrl("http://172.21.0.3:11434/")
                //.baseUrl(getUserBucketPath+":11434/")
                .modelName("gemma:2b")
                .timeout(Duration.ofHours(1))
                .build();
    }


}
