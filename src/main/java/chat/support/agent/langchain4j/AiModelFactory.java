package chat.support.agent.langchain4j;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;

import java.time.Duration;

public class AiModelFactory {

    private AiModelFactory() {
        throw new IllegalStateException("Factory class shouldn't be instantiated");
    }

    public static ChatLanguageModel createLocalChatModel() {
        return OpenAiChatModel.builder()
                .baseUrl("http://localhost:1234/v1")
                .apiKey("ignore")
           //     .temperature(0.7)
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

    public static ChatLanguageModel createOpenAIChatModel() {
        return OpenAiChatModel.builder()
                .modelName("gpt-3.5-turbo")
                .apiKey("demo")
              //  .temperature(0.7)
                .logRequests(true)
                .build();
    }
    public static StreamingChatLanguageModel createStremingOpenAIChatModel() {
        return OpenAiStreamingChatModel
                .builder()
                .modelName("gpt-3.5-turbo")
                .apiKey("Demo")
                .build();
    }

}
