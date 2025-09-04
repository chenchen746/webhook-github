package com.example.demo.service;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class WeatherMCPService {
    @Tool(description = "Get weather information by city name,the input is the name of the city")
    public String getWeather(String cityName) {
        // Implementation
        System.out.println("getWeather");
        return "这就是"+cityName+"的天气信息";
    }

    @Bean(name = "hha")
    public ToolCallbackProvider weatherTools(WeatherMCPService weatherMCPService) {
        return MethodToolCallbackProvider.builder().toolObjects(weatherMCPService).build();
    }
}
