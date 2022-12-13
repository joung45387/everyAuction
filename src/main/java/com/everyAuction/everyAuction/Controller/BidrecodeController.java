package com.everyAuction.everyAuction.Controller;

import com.everyAuction.everyAuction.Domain.BidRecord;
import com.everyAuction.everyAuction.Domain.Member;
import com.everyAuction.everyAuction.Domain.Product;
import com.everyAuction.everyAuction.Repository.BidRepository;
import com.everyAuction.everyAuction.Repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;
import java.util.stream.Collectors;

import static com.everyAuction.everyAuction.Controller.ProductList.SESSION_ID;

@Controller
@RequiredArgsConstructor
public class BidrecodeController {
    private final BidRepository BR;


    @GetMapping("/bidRecode")
    public String SearchbidComplete(@SessionAttribute(name = SESSION_ID, required = false) Member member, Model model){

        if(member==null){
            return "redirect:/login";
        }

        List<Product> completebidrecord = BR.completebidrecordNoBuyer(member.getId());
        List<String> imgs = completebidrecord.stream().map(img -> new String(Base64.encodeBase64((byte[]) img.getProductPhoto()))).collect(Collectors.toList());
        model.addAttribute("productList", completebidrecord);
        model.addAttribute("photo", imgs);
        model.addAttribute("existence", completebidrecord.isEmpty());
        model.addAttribute("status", "판매 기록");
        model.addAttribute("isLogin", member==null);
        return "myProduct_sales";
    }

    @GetMapping("/bidBuyRecode")
    public String SearchbidBuyComplete(@SessionAttribute(name = SESSION_ID, required = false) Member member, Model model){

        if(member==null){
            return "redirect:/login";
        }

        List<Product> completebidrecord = BR.findBuyRecordNoBuyer(member.getId());
        List<String> imgs = completebidrecord.stream().map(img -> new String(Base64.encodeBase64((byte[]) img.getProductPhoto()))).collect(Collectors.toList());
        model.addAttribute("productList", completebidrecord);
        model.addAttribute("photo", imgs);
        model.addAttribute("existence", completebidrecord.isEmpty());
        model.addAttribute("myid", member.getId());
        model.addAttribute("status", "구매 기록");
        model.addAttribute("isLogin", member==null);
        return "myProduct_purchase";
    }

    @GetMapping("/bidComplete/{productId}")
    public String SearchProductidandbidComplete(@SessionAttribute(name = SESSION_ID, required = false) Member member,
                                                Model model,
                                                @PathVariable("productId") int id){
        if(member==null){
            return "redirect:/login";
        }
        List<BidRecord> findbyproductid = BR.findbyproductid(id);
        model.addAttribute("bidRecord", findbyproductid);
        model.addAttribute("isLogin", member==null);
        return "bidComplete";
    }

}


