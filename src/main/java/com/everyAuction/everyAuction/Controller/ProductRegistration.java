package com.everyAuction.everyAuction.Controller;

import com.everyAuction.everyAuction.Domain.Member;
import com.everyAuction.everyAuction.Service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import static com.everyAuction.everyAuction.Controller.testpage.SESSION_ID;

@Controller
@RequiredArgsConstructor
public class ProductRegistration {

    private final JdbcTemplate jdbcTemplate;
    private final ScheduleService t;

    @GetMapping("/productRegistration")
    public String aa(@SessionAttribute(name = SESSION_ID, required = false) Member member){
        if(member==null){
           return "redirect:/login";
        }
        return "productRegistration";
    }
}
