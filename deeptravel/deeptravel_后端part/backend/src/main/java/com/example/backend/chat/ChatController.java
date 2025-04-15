package com.example.backend.chat;

import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import static com.example.backend.utils.Prompt.PROMPT2;
import static com.example.backend.utils.Prompt.PROMPT3;
import static com.example.backend.utils.SystemConstants.*;

@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private OllamaChatModel ollamaChatModel;

    @GetMapping(value = "/c2/{msg}" ,produces="text/html;charset=utf-8")
    public Flux<String> chatStream(@PathVariable("msg") String msg){
        String prompt3 = PROMPT3;
        String prompt = "prompt"+prompt3+msg;
        Prompt thePrompt = new Prompt(new UserMessage(prompt));
        System.out.println("生成聊天");
        System.out.println(prompt);
        return ollamaChatModel.stream(thePrompt.getContents());
    }
}
