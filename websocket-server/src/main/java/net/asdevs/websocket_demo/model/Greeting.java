package net.asdevs.websocket_demo.model;

import lombok.Data;

@Data
public class Greeting {
    public String content;

    public Greeting(){

    }

    public Greeting(String content){
        this.content = content;
    }
}
