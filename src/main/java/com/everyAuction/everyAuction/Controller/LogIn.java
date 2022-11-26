package com.everyAuction.everyAuction.Controller;

import com.everyAuction.everyAuction.Domain.LoginForm;
import com.everyAuction.everyAuction.Domain.Member;
import com.everyAuction.everyAuction.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.everyAuction.everyAuction.Controller.ProductList.SESSION_ID;

@Controller
@RequiredArgsConstructor
public class LogIn {
    private final MemberRepository MR;

    @GetMapping("/login")
    public String logIn(@SessionAttribute(name = SESSION_ID, required = false) Member login, Model model){
        if(login != null){
            return "redirect:/";
        }
        model.addAttribute("loginForm", new LoginForm());
        return "auctionLogin";
    }

    @PostMapping("/login")
    public String postLogin(@ModelAttribute LoginForm loginForm, HttpServletRequest request, Model model){
        Member member = MR.findMember(loginForm);
        if(member!=null){
            HttpSession session = request.getSession();
            session.setAttribute(SESSION_ID, member);
            return "redirect:/";
        }
        model.addAttribute("error", "아이디 혹은 비밀번호가 잘못되었습니다.");
        return "auctionLogin";
    }
}
