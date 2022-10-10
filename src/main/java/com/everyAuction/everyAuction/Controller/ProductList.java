package com.everyAuction.everyAuction.Controller;

import com.everyAuction.everyAuction.Domain.Member;
import com.everyAuction.everyAuction.Domain.Product;
import com.everyAuction.everyAuction.Repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

import static com.everyAuction.everyAuction.Controller.testpage.SESSION_ID;

@Controller
@RequiredArgsConstructor
public class ProductList {
    private final ItemRepository IR;

    @GetMapping("/productlist")
    public String SaleItemUpload(@SessionAttribute(name = SESSION_ID, required = false) Member member, Model model){

        List<Product> all = IR.findAll();
        for(Product a : all){
            System.out.println(a.getId()+" "+a.getSaleUser()+" "+a.getStartPrice());
        }
        model.addAttribute("productList", all);
        return "productList";
    }

}
