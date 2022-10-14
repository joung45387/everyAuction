package com.everyAuction.everyAuction.Domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ScheduledProduct {
    int id;
    LocalDateTime endTime;

    public ScheduledProduct(int id, LocalDateTime endTime) {
        this.id = id;
        this.endTime = endTime;
    }

    public ScheduledProduct() {

    }
}
