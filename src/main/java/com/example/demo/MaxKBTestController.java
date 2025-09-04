package com.example.demo;


import com.example.demo.model.Prompt;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MaxKBTestController {

    @Autowired
    private OpenAiChatModel openAiChatModel;
    @PostMapping("/testMaxKB")
    public Map testMaxKB(@RequestBody Prompt prompt, HttpServletRequest request) {
        System.out.println("prompt: " + prompt.getPromptString());
        return Map.of("generation", this.openAiChatModel.call(prompt.getPromptString()));
    }

}
