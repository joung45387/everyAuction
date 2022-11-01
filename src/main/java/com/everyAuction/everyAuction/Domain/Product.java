package com.everyAuction.everyAuction.Domain;

import lombok.Getter;
import lombok.Setter;

import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
public class Product {
    private int id;
    private String saleUser;
    private int startPrice;
    private byte[] productPhoto;
    private String productInformation;
    private String title;
    private LocalDateTime endTime;
    private int currentPrice;
    private String buyer;

    public Product(String saleUser, int startPrice, byte[] productPhoto, String productInformation, String title, LocalDateTime endTime, int currentPrice) {
        this.saleUser = saleUser;
        this.startPrice = startPrice;
        this.productPhoto = productPhoto;
        this.productInformation = productInformation;
        this.title = title;
        this.endTime = endTime;
        this.currentPrice = currentPrice;
    }
    public Product(){};
}
