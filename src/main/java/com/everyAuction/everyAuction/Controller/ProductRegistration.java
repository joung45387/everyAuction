package com.everyAuction.everyAuction.Controller;

import com.everyAuction.everyAuction.Service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ProductRegistration {

    private final JdbcTemplate jdbcTemplate;
    private final ScheduleService t;

    @GetMapping("/productRegistration")
    public String aa(){

        return "productRegistration";
    }
}
