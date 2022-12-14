package com.everyAuction.everyAuction.Controller;

import com.everyAuction.everyAuction.Domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttribute;

import static com.everyAuction.everyAuction.Controller.ProductList.SESSION_ID;

@Controller
@RequiredArgsConstructor
public class myinfo {

    @GetMapping("/myinfo")
    public String ProductController(@SessionAttribute(name = SESSION_ID, required = false) Member member, Model model){
        if(member==null){
            return "redirect:/login";
        }
        model.addAttribute("info", member);
        model.addAttribute("isLogin", member==null);
        return "profile";
    }
}
