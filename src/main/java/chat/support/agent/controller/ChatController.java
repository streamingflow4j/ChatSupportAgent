package chat.support.agent.controller;

import chat.support.agent.model.ChatForm;
import chat.support.agent.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;


@Controller
@RequestMapping("/chat")
public class ChatController {

	@Autowired
	private MessageService messageService;

//	public ChatController(MessageService messageService) {
//		this.messageService = messageService;
//	}

	@GetMapping
	public String getChatPage(ChatForm chatForm, Model model) {
		model.addAttribute("chats", this.messageService.getMessages());
		return "chat";
	}

	@PostMapping
	public String postChatMessage(ChatForm chatForm, Model model) throws IOException {
		chatForm.setUsername("User");
		this.messageService.addMessages(chatForm);
		model.addAttribute("chats", this.messageService.getMessages());
		chatForm.setMessageText("");
		return "chat";
	}


}
