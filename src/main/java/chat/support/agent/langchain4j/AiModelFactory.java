package chat.support.agent.langchain4j;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import org.springframework.core.env.StandardEnvironment;

import java.time.Duration;

public class AiModelFactory {

    private AiModelFactory() {
        throw new IllegalStateException("Factory class shouldn't be instantiated");
    }

    public static ChatLanguageModel createLocalChatModel() {
        return OpenAiChatModel.builder()
                .baseUrl("http://localhost:1234/v1")
                .apiKey("ignore")
                .temperature(0.7)
                .logRequests(true)
                .timeout(Duration.ofSeconds(300))
                .build();
    }

    public static StreamingChatLanguageModel createStremingLocalChatModel() {
        return OpenAiStreamingChatModel
                .builder()
                .baseUrl("http://localhost:1234/v1")
                .apiKey("ignore")
                .logRequests(true)
                .timeout(Duration.ofSeconds(300))
                .build();
    }

    //-------------------  StreamingChat Models ----------------
    public static StreamingChatLanguageModel createStremingOpenAIChatModel() {
        return OpenAiStreamingChatModel
                .builder()
                .modelName("gemini-2.0-flash")
                .apiKey(System.getenv("GEMINI_API_KEY"))
                .build();

    }
    public static ChatLanguageModel createOpenAIChatModel() {
        return OpenAiChatModel.builder()
                .modelName("gpt-4o-mini")
                .apiKey("demo")
                .temperature(0.7)
                .logRequests(true)
                .build();
    }
    //-------------------  StreamingChat Ollama Models ----------------
    public static ChatLanguageModel createLocalChatOllamaModel() {

        return  OllamaChatModel.builder()
                .baseUrl("http://localhost:11434/")
                .modelName("qwen3:1.7b")
                .temperature(0.7)
                .logRequests(true)
                .logResponses(true)
                .build();
    }

    public static StreamingChatLanguageModel createLocalOllamaStreamingChatModel() {
        return OllamaStreamingChatModel.builder()
                .baseUrl("http://localhost:11434/")
                .modelName("qwen3:1.7b")
                .timeout(Duration.ofHours(1))
                .logRequests(true)
                .logResponses(true)
                .build();
    }
}
