package pl.abbl.reactchat.models;

import lombok.Data;

@Data
public class ChatMessage {
    private int id;
    private String author;
    private String content;
    private String time;

    public ChatMessage(){

    }
}
