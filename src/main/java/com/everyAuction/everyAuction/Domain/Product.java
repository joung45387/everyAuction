package com.everyAuction.everyAuction.Domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Product {
    private int id;
    private String saleUser;
    private int startPrice;
    private String productPhoto;
    private String productInformation;
    private String title;
    private LocalDateTime endTime;

    public Product(String saleUser, int startPrice, String productPhoto, String productInformation, String title, LocalDateTime endTime) {
        this.saleUser = saleUser;
        this.startPrice = startPrice;
        this.productPhoto = productPhoto;
        this.productInformation = productInformation;
        this.title = title;
        this.endTime = endTime;
    }
    public Product(){};
}
