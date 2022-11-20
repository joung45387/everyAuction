package com.everyAuction.everyAuction.Controller;


import com.everyAuction.everyAuction.Domain.Member;
import com.everyAuction.everyAuction.Domain.Message;
import com.everyAuction.everyAuction.Domain.Product;
import com.everyAuction.everyAuction.Repository.chatRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;
import java.util.stream.Collectors;

import static com.everyAuction.everyAuction.Controller.testpage.SESSION_ID;

@Controller
@RequiredArgsConstructor
public class chat {
    private final chatRepository CR;

    @GetMapping("/chat/{productId}")
    public String SearchbidComplete(@SessionAttribute(name = SESSION_ID, required = false) Member member,
                                    Model model,
                                    @PathVariable("productId") int productId){
        //채팅 접근 권한 추가할 예정
        if(member==null){
            return "redirect:/login";
        }
        List<Message> allChat = CR.findAllChat(productId);
        model.addAttribute("allChat", allChat);
        model.addAttribute("productId", productId);
        model.addAttribute("userName", member.getId());
        return "chat";
    }
}
