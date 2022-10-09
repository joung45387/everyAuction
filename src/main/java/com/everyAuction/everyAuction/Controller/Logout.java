package com.everyAuction.everyAuction.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class Logout {
    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        if(request == null){
            return "redirect:/";
        }
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/";
    }
}
