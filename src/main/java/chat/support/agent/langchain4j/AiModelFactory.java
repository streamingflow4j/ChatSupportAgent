package chat.support.agent.langchain4j;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import org.springframework.core.env.StandardEnvironment;

import java.time.Duration;

public class AiModelFactory {

    private static final String MODEL = "deepseek-r1:1.5b";

    private static final String BASE_URL = "http://localhost:11434";

    private static StandardEnvironment environment;

    private AiModelFactory() {
    }
    public static ChatLanguageModel createLocalChatOllamaModel() {
        environment = new StandardEnvironment();
        return  OllamaChatModel.builder()
                .baseUrl(environment.getRequiredProperty("ollama.host"))
                .modelName(environment.getRequiredProperty("ollama.model"))
                .temperature(0.7)
                .logRequests(true)
                .logResponses(true)
                .build();
    }

    //-------------------  StreamingChat Models ----------------
    public static StreamingChatLanguageModel createLocalOllamaStreamingChatModel() {
        environment = new StandardEnvironment();
        return OllamaStreamingChatModel.builder()
                .baseUrl(BASE_URL)
                .modelName(MODEL)
                .timeout(Duration.ofHours(1))
                .logRequests(true)
                .logResponses(true)
                .build();
    }
}
