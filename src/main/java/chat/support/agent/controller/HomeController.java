package chat.support.agent.controller;

import chat.support.agent.model.MessageForm;
import chat.support.agent.service.FIleStoreService;
import chat.support.agent.service.MessageListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class HomeController {

	@Autowired
	private MessageListService messageListService;
	@Autowired
	FIleStoreService fileStoreService;

//	public HomeController(MessageListService messageListService) {
//		this.messageListService = messageListService;
//	}

	@GetMapping("/home")
	public String getHomePage(@ModelAttribute("newMessage") MessageForm messageForm, Model model) {
		model.addAttribute("greetings", this.messageListService.getMessages());
		return "home";
	}

	@PostMapping("/home")
	public String addMessage(@ModelAttribute("newMessage") MessageForm messageForm, Model model) {
		messageListService.addMessages(messageForm.getText());
		model.addAttribute("greetings", this.messageListService.getMessages());
		messageForm.setText("");
		return "home";
	}

	@RequestMapping("/simplehome")
	public String getSimpleHomePage(Model model) {
		model.addAttribute("firstVisit", "TRUE");
		return "simple-home";
	}
	
	@PostMapping("/file-upload")
	public String handleFileUpload(@RequestParam("fileUpload")  MultipartFile fileUpload	, Model model) {
		fileStoreService.store(fileUpload);
		model.addAttribute("firstVisit", "TRUE");
		return "simple-home";
	}

}
