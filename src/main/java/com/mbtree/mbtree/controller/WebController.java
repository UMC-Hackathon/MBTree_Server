package com.mbtree.mbtree.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class WebController {

    @RequestMapping("/main")
    public String mainPage(){
        return "main.html";
    }

    @RequestMapping("/chat")
    public String chatPage(){
        return "chat.html";
    }

}
