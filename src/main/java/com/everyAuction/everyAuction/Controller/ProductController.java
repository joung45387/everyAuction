package com.everyAuction.everyAuction.Controller;

import com.everyAuction.everyAuction.Domain.BidRecord;
import com.everyAuction.everyAuction.Domain.Member;
import com.everyAuction.everyAuction.Domain.Product;
import com.everyAuction.everyAuction.Repository.BidRepository;
import com.everyAuction.everyAuction.Repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static com.everyAuction.everyAuction.Controller.testpage.SESSION_ID;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ItemRepository IR;
    private final BidRepository BR;

    @GetMapping("/product/{id}")
    public String ProductController(@SessionAttribute(name = SESSION_ID, required = false) Member member,
                          Model model,
                          @PathVariable("id") int id){

        Product product = IR.findById(id);
        byte[] encoded=org.apache.commons.codec.binary.Base64.encodeBase64((byte[]) product.getProductPhoto());
        String encodedString = new String(encoded);
        model.addAttribute("product", product);
        model.addAttribute("photo", encodedString);
        return "product";
    }


    @PostMapping("/product/{id}/bid")
    public String ProductBid(@SessionAttribute(name = SESSION_ID, required = false) Member member,
                                    Model model,
                                    String cost,
                                    @PathVariable("id") int id){
        if(member==null){
            return "redirect:/login";
        }
        Product product = IR.findById(id);
        System.out.println(product.getProductPhoto().length);
        model.addAttribute("product", product);
        if(product.getCurrentPrice()>=Integer.parseInt(cost)){
            model.addAttribute("lowerprice", "현재가 보다 낮은 입찰은 할수 없습니다.");
            return "product";
        }
        IR.updateCurrentPrice(id, Integer.parseInt(cost));
        BR.saveBidRecord(new BidRecord(member.getId(), id, LocalDateTime.now(ZoneId.of("Asia/Seoul")), Integer.parseInt(cost)));
        return "redirect:/product/"+id;
    }
}
