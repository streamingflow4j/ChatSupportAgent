package chat.support.agent.langchain4j;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;

import java.time.Duration;

public class AiModelFactory {

    private static StandardEnvironment environment;

    private AiModelFactory() {

    }
    public static ChatLanguageModel createLocalChatOllamaModel() {
        environment = new StandardEnvironment();
        return  OllamaChatModel.builder()
                .baseUrl(environment.getRequiredProperty("ollama.host"))
                .modelName(environment.getRequiredProperty("model.name"))
                .temperature(0.7)
                .logRequests(true)
                .logResponses(true)
                .build();
    }

    //-------------------  StreamingChat Models ----------------
    public static StreamingChatLanguageModel createLocalOllamaStreamingChatModel() {
        environment = new StandardEnvironment();
        return OllamaStreamingChatModel.builder()
                .baseUrl(environment.getRequiredProperty("ollama.host"))
                .modelName(environment.getRequiredProperty("model.name"))
                .timeout(Duration.ofHours(1))
                .logRequests(true)
                .logResponses(true)
                .build();
    }
}
