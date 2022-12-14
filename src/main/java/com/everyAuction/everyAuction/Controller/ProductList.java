package com.everyAuction.everyAuction.Controller;

import com.everyAuction.everyAuction.Domain.Member;
import com.everyAuction.everyAuction.Domain.Product;
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


@Controller
@RequiredArgsConstructor
public class ProductList {
    private final ItemRepository IR;
    static final String SESSION_ID = "SESSION_ID";
    @GetMapping("/")
    public String a(){
        return "redirect:/productlist";
    }
    @GetMapping("/productlist")
    public String SaleItemUpload(@SessionAttribute(name = SESSION_ID, required = false) Member member, Model model){

        List<Product> all = IR.saleAll();
        List<String> imgs = all.stream().map(img -> new String(Base64.encodeBase64((byte[]) img.getProductPhoto()))).collect(Collectors.toList());
        model.addAttribute("productList", all);
        model.addAttribute("photo", imgs);
        model.addAttribute("isLogin", member==null);
        return "index";
    }



}
