package com.example.demo.controller;

import com.example.demo.CityVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class WeatherController
{
    @PostMapping(value = "/weather")
    public ResponseEntity addNotic(@RequestBody CityVO cityVO, HttpServletRequest request)
    {
        Map ret=new HashMap();
        ret.put("asdf",cityVO.getCityName()+"天气~");
        return new ResponseEntity(ret, HttpStatus.OK);
    }
}
