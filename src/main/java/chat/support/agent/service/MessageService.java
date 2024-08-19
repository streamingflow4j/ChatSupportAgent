package chat.support.agent.service;

import chat.support.agent.langchain4j.AiModelFactory;
import chat.support.agent.langchain4j.Lang4jTools;
import chat.support.agent.langchain4j.LangChain4jAssistant;
import chat.support.agent.model.ChatForm;
import chat.support.agent.model.ChatMessage;
import chat.support.agent.utils.Util;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class MessageService {


	private List<ChatMessage> chatMsglist;

	
	@PostConstruct
	public void postConstruct() {
		this.chatMsglist = new ArrayList<>();
	}


	public void addMessages(ChatForm chatForm) throws IOException {
		ChatMessage newMessage = new ChatMessage();
		newMessage.setMessage("|CHAT| ====> ", userChat(chatForm.getMessageText()));
		chatMsglist.add(newMessage);
	}
	
	public List<ChatMessage> getMessages() {
		//return new ArrayList<ChatMessage>(this.chatMsglist);
		 return chatMsglist;
	}

	public String userChat(String msg) throws IOException {
		//ChatLanguageModel model = AiModelFactory.createOpenAIChatModel();
		ChatLanguageModel model = AiModelFactory.createLocalChatModel();
		InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
		//2 - em memoria carrega o vetor embedando os documentos
		EmbeddingStoreIngestor.ingest(Util.getDocs(), embeddingStore);

		LangChain4jAssistant langChain4jAssistant = AiServices.builder(LangChain4jAssistant.class)
				.chatLanguageModel(model)
				.chatMemory(MessageWindowChatMemory.withMaxMessages(10))
				.tools(new Lang4jTools())
				.contentRetriever(EmbeddingStoreContentRetriever.from(embeddingStore))
				.build();

		return langChain4jAssistant.chat(msg);
	}

}
