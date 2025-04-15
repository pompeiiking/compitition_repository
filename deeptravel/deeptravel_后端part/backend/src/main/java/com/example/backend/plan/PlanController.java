package com.example.backend.plan;

import com.example.backend.utils.Parameter;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import static com.example.backend.utils.Prompt.PROMPT2;
import static com.example.backend.utils.SystemConstants.*;

@RestController
@RequestMapping("/plan")
public class PlanController {

    private final OllamaChatModel ollamaChatModel;

    @Autowired
    public PlanController(OllamaChatModel ollamaChatModel) {
        this.ollamaChatModel = ollamaChatModel;
    }
    @GetMapping(value = "/p/{msg}" ,produces="text/html;charset=utf-8")
    public Flux<String> planStream(@PathVariable("msg") String msg){
        String prompt2 = PROMPT2;
        String gender = GENDER[1];
        String income = INCOME_LEVEL;
        String travelMode = TRAVEL_MODE[1];
        String activityType = ACTIVITY_TYPE[1];
        String rests = RESTS;
        String prompt = "prompt"+prompt2+"性别："+gender+"\n"
                +"收入："+income+"\n"
                +"出行方式："+travelMode+"\n"
                +"活动类型："+activityType+"\n"
                +"其他："+rests+"\n"+"目的地"+msg;
        Prompt thePrompt = new Prompt(new UserMessage(prompt));
        System.out.println("生成计划");
        System.out.println(prompt);
        return ollamaChatModel.stream(thePrompt.getContents());
    }
}