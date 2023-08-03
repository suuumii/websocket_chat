package net.asdevs.websocket_demo.model;

import lombok.Data;

@Data
public class HelloRequest {
    private String name;
    private String message;

    public HelloRequest() {

    }

    public HelloRequest(String name, String message){
        this.name = name;
        this.message = message;
    }
}
