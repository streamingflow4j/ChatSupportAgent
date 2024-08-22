package chat.support.agent.langchain4j;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import java.time.Duration;

public class AiModelFactory {

    private AiModelFactory() {
        throw new IllegalStateException("Factory class shouldn't be instantiated");
    }
    
    public static ChatLanguageModel createLocalChatOllamaModel() {
        return  OllamaChatModel.builder()
                .baseUrl("http://localhost:11434/")
                .modelName("gemma:2b")
                .temperature(0.8)
                .build();
    }

    //-------------------  StreamingChat Models ----------------
    public static StreamingChatLanguageModel createLocalOllamaStreamingChatModel() {
        return OllamaStreamingChatModel.builder()
                .baseUrl("http://localhost:11434/")
                .modelName("gemma:2b")
                .timeout(Duration.ofHours(1))
                .build();
    }

}
