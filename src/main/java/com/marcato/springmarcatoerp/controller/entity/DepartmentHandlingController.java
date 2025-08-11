package com.marcato.springmarcatoerp.controller.entity;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class DepartmentHandlingController {

    @MessageMapping("/ws/hello")
    @SendTo("/topic/departments")
    public String send(@Payload String text){
        return "Hello, " + text;
    }
}
