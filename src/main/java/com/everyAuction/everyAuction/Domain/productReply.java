package com.everyAuction.everyAuction.Domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class productReply {
    int id;
    int productId;
    String userId;
    String content;
    LocalDateTime uploadTime;

    public productReply( int productId, String userId, String content, LocalDateTime uploadTime) {
        this.productId = productId;
        this.userId = userId;
        this.content = content;
        this.uploadTime = uploadTime;
    }

    public productReply() {
    }
}
