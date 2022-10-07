package com.everyAuction.everyAuction.Controller;

import com.everyAuction.everyAuction.Domain.Member;
import com.everyAuction.everyAuction.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequiredArgsConstructor
public class MemberRegistration {

    private final MemberRepository MR;

    @GetMapping("/memberRegistration")
    public String memberRegistration(Model model, Member memberForm){
        model.addAttribute("member", memberForm);
        return "memberRegistration";
    }
    @PostMapping("/regist")
    public String save(@ModelAttribute Member memberForm, Model model){
        ConcurrentHashMap<String, String> errors = new ConcurrentHashMap<>();

        System.out.println("저장"+memberForm.getId());
        if(MR.idExistence(memberForm.getId())){
            errors.put("idDuplication", "중복된 아이디입니다.");
        }
        if(!errors.isEmpty()){
            model.addAttribute("errors", errors);
            return "memberRegistration";
        }
        MR.saveMember(memberForm);
         return "redirect:/";
    }
}
