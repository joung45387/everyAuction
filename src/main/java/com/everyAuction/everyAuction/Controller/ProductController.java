package com.everyAuction.everyAuction.Controller;

import com.everyAuction.everyAuction.Domain.Member;
import com.everyAuction.everyAuction.Domain.Product;
import com.everyAuction.everyAuction.Repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.net.MalformedURLException;
import java.util.List;

import static com.everyAuction.everyAuction.Controller.testpage.SESSION_ID;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ItemRepository IR;

    @GetMapping("/product/{id}")
    public String ProductController(@SessionAttribute(name = SESSION_ID, required = false) Member member,
                          Model model,
                          @PathVariable("id") int id){

        Product product = IR.findById(id);
        model.addAttribute("product", product);
        return "product";
    }
    @GetMapping("/img/{filename}")
    @ResponseBody
    public Resource SaleItemUpload(@SessionAttribute(name = SESSION_ID, required = false) Member member,
                                   Model model,
                                   @PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:D:\\"+filename);
    }
}
