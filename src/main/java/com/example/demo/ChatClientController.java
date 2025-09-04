package com.example.demo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ChatClientController {

    @Autowired
    private ChatClient chatClient;

    @GetMapping("/ai/generateChatClient")
    public Map ChatClient(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        System.out.println("message: " + message);

        ChatResponse chatResponse = chatClient.prompt()
                .user(message)
                .call()
                .chatResponse();

        return Map.of("generation",chatResponse.getResult().getOutput().getText());
    }


}
