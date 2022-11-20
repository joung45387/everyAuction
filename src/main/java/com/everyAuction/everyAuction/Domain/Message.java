package com.everyAuction.everyAuction.Domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Message {

    private String messageId;
    private String sender;
    private String channelId;
    private String text;
    private LocalDateTime chatTime;

    public void setSender(String sender){
        this.sender = sender;
    }
    public void setMessageId(String id){
        this.messageId = id;
    }
}
