package com.everyAuction.everyAuction.Domain;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class Member {

    private String id;
    private String password;
    private String name;
    private String phoneNumber;
    private String address;

    public Member(String id, String password, String name, String phoneNumber, String address) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public Member() {

    }
}
