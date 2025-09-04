package com.example.demo;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartUpCommandLineRunner implements CommandLineRunner {
    @Resource
    private ChatClient chatClient;

    @Autowired
    private ToolCallbackProvider toolCallbackProvider;


    @Override
    public void run(String... args) throws Exception {
        chatClient=chatClient.mutate()
                .defaultToolCallbacks(toolCallbackProvider)
                .build();
        System.out.println("xxxxxx");
        ChatResponse chatResponse = chatClient.prompt()
                .system("你是一个天气助手。")
                .user("what is the weather in Shanghai?")
                .call()
                .chatResponse();
        System.out.println(chatResponse.getResult().getOutput().getText());

    }
}
