package com.everyAuction.everyAuction.Controller;

import com.everyAuction.everyAuction.Domain.BidRecord;
import com.everyAuction.everyAuction.Domain.Member;
import com.everyAuction.everyAuction.Domain.Product;
import com.everyAuction.everyAuction.Domain.productReply;
import com.everyAuction.everyAuction.Repository.BidRepository;
import com.everyAuction.everyAuction.Repository.ItemRepository;
import com.everyAuction.everyAuction.Repository.replyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.everyAuction.everyAuction.Controller.testpage.SESSION_ID;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ItemRepository IR;
    private final BidRepository BR;
    private final replyRepository RR;
    private final SimpMessageSendingOperations simpMessageSendingOperations;

    @GetMapping("/product/{id}")
    public String ProductController(@SessionAttribute(name = SESSION_ID, required = false) Member member,
                          Model model,
                          @PathVariable("id") int id){

        model = productInit(model, IR.findById(id), id, member);
        return "product_single";
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
        model = productInit(model, product, id, member);
        if(product.getCurrentPrice()>=Integer.parseInt(cost)){
            model.addAttribute("lowerprice", "현재가 보다 낮은 입찰은 할수 없습니다.");
            return "/product/"+id;
        }
        simpMessageSendingOperations.convertAndSend("/sub/productId/"+id, cost);
        IR.updateCurrentPrice(id, Integer.parseInt(cost));
        BR.saveBidRecord(new BidRecord(member.getId(), id, LocalDateTime.now(ZoneId.of("Asia/Seoul")), Integer.parseInt(cost)));
        return "redirect:/product/"+id;
    }

    @PostMapping("/product/{id}/productreply")
    public String saveReply(@SessionAttribute(name = SESSION_ID, required = false) Member member,
                             Model model,
                             String content,
                             @PathVariable("id") int id){
        if(member==null){
            return "redirect:/login";
        }
        RR.saveReply(new productReply(id, member.getId(), content, LocalDateTime.now(ZoneId.of("Asia/Seoul"))));
        return "redirect:/product/"+id;
    }

    @PostMapping("/product/deletereply/{id}/{productid}")
    public String deleteReply(@SessionAttribute(name = SESSION_ID, required = false) Member member,
                            Model model,
                            @PathVariable("id") int id,
                              @PathVariable("productid") int productid){
        if(member==null){
            return "redirect:/login";
        }
        RR.deleteReply(id, member.getId());
        return "redirect:/product/"+productid;
    }

    @PostMapping("/product/editreply/{id}/{productid}")
    public String editReply(@SessionAttribute(name = SESSION_ID, required = false) Member member,
                              Model model,
                              String content,
                              @PathVariable("id") int id,
                              @PathVariable("productid") int productid){
        if(member==null){
            return "redirect:/login";
        }
        RR.editReply(id, member.getId(), content);
        return "redirect:/product/"+productid;
    }

    public Model productInit(Model model, Product product, int id, Member member){
        List<productReply> allReply = RR.findAllReply(id);
        List<Boolean> collect = new ArrayList<>();
        collect = allReply.stream().map(o -> member != null && o.getUserId().equals(member.getId())).collect(Collectors.toList());

        byte[] encoded=org.apache.commons.codec.binary.Base64.encodeBase64((byte[]) product.getProductPhoto());
        String encodedString = new String(encoded);
        model.addAttribute("product", product);
        model.addAttribute("photo", encodedString);
        model.addAttribute("possible", product.getEndTime().compareTo(LocalDateTime.now(ZoneId.of("Asia/Seoul"))) > 0);
        model.addAttribute("replies", allReply);
        model.addAttribute("mine", collect);
        return model;
    }
}
