package com.everyAuction.everyAuction.Controller;

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

import static com.everyAuction.everyAuction.Controller.testpage.SESSION_ID;

@Controller
@RequiredArgsConstructor
public class BidrecodeController {
    private final BidRepository BR;

    @GetMapping("/bidRecode")
    public String SearchbidComplete(@SessionAttribute(name = SESSION_ID, required = false) Member member, Model model){
        List<Product> completebidrecord = BR.completebidrecord();
        List<String> imgs = completebidrecord.stream().map(img -> new String(Base64.encodeBase64((byte[]) img.getProductPhoto()))).collect(Collectors.toList());
        model.addAttribute("productList", completebidrecord);
        model.addAttribute("photo", imgs);
        return "bidrecode";
    }

}


