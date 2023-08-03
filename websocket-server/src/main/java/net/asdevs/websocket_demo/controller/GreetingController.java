package net.asdevs.websocket_demo.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import net.asdevs.websocket_demo.model.Greeting;
import net.asdevs.websocket_demo.model.HelloRequest;

@Controller
public class GreetingController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloRequest hellowrequest) throws Exception {
        Thread.sleep(1000);
        return new Greeting(
                HtmlUtils.htmlEscape(hellowrequest.getName()) + ":" + HtmlUtils.htmlEscape(hellowrequest.getMessage()));

    }
}