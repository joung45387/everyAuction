package com.everyAuction.everyAuction.Domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BidRecord {
    public BidRecord(String bidUserId, int productId, LocalDateTime bidTime, int bidPrice) {
        this.bidUserId = bidUserId;
        this.productId = productId;
        this.bidTime = bidTime;
        this.bidPrice = bidPrice;
    }

    private String bidRecordId;
    private String bidUserId;
    private int productId;
    private LocalDateTime bidTime;
    private int bidPrice;
}
