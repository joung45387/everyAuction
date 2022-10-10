package com.everyAuction.everyAuction.Domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaleItemDTO {
    int id;
    String title;
    String content;
    int startPrice;
    String endTime;
}
