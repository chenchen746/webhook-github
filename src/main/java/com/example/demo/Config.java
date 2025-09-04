package com.example.demo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Config {

    @Autowired
    private OpenAiChatModel openAiChatModel;
    @Bean(name = "chatClient")
    public ChatClient getChatClient() {
        ChatClient chatClient = ChatClient.create(openAiChatModel);
        return chatClient;
    }
}
