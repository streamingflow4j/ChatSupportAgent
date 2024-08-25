package chat.support.agent.service;

import chat.support.agent.langchain4j.AiModelFactory;
import chat.support.agent.langchain4j.Lang4jTools;
import chat.support.agent.langchain4j.LangChain4jAssistant;
import chat.support.agent.model.ChatForm;
import chat.support.agent.model.ChatMessage;
import chat.support.agent.utils.Util;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@Service
public class MessageService {

	private String message;
	//@Autowired
	//private Lang4jTools lang4jTools;
	private List<ChatMessage> chatMsglist;

	public MessageService() {

	}

	@PostConstruct
	public void postConstruct() {
		this.chatMsglist = new ArrayList<>();
		System.out.println("Created messageService Bean");
	}

	public void addMessages(ChatForm chatForm) throws IOException, ExecutionException, InterruptedException {
		ChatMessage newMessage = new ChatMessage();
		//add chat MSG
		//newMessage.setMessage("Chat", userChat(chatForm.getMessageText()));
		newMessage.setMessage("Chat", streamingUserChat(chatForm.getMessageText()));
		chatMsglist.add(newMessage);
	}

	public List<ChatMessage> getMessages() {
		//return new ArrayList<ChatMessage>(this.chatMsglist);
		return chatMsglist;
	}


	public InMemoryEmbeddingStore<TextSegment>  getEmbedingStore() throws IOException {
		InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
		//2 - em memoria carrega o vetor embedando os documentos
		EmbeddingStoreIngestor.ingest(Util.getDocs(), embeddingStore);
		return embeddingStore;
	}

	public String streamingUserChat(String msg) throws IOException, ExecutionException, InterruptedException {

		StreamingChatLanguageModel model = AiModelFactory.createLocalOllamaStreamingChatModel();

		LangChain4jAssistant assistant = AiServices.builder(LangChain4jAssistant.class)
				// Alternative of .chatLanguageModel() which support streaming response
				.streamingChatLanguageModel(model)
				//.tools(new Lang4jTools()) //-->> ollama nao suporta
				.chatMemory(MessageWindowChatMemory.withMaxMessages(10))
				.contentRetriever(EmbeddingStoreContentRetriever.from(this.getEmbedingStore()))
				.build();

		//String token = model.generate(msg); old way
		CompletableFuture<String> token = ask(assistant, msg);
		System.out.printf("Response: %s%n", token);

		return token.get();
	}

	public static CompletableFuture<String> ask(LangChain4jAssistant assistant, String msg) {
		TokenStream tokenStream = assistant.chat(msg);
		CompletableFuture<String> future = new CompletableFuture<>();
		tokenStream.onNext(System.out::print)
				.onComplete((a) -> {
					//System.out.println(a);
					future.complete(a.content().text());
				})
				.onError(Throwable::printStackTrace)
				.start();
		return future;
	}

}
