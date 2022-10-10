package com.everyAuction.everyAuction.Controller;

import com.everyAuction.everyAuction.Domain.LoginForm;
import com.everyAuction.everyAuction.Domain.Member;
import com.everyAuction.everyAuction.Domain.Product;
import com.everyAuction.everyAuction.Domain.SaleItemDTO;
import com.everyAuction.everyAuction.Repository.ItemRepository;
import com.everyAuction.everyAuction.Service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.everyAuction.everyAuction.Controller.testpage.SESSION_ID;

@Controller
@RequiredArgsConstructor
public class SaleItemUpload {

    private final ItemRepository IR;

    @GetMapping("/saleitemupload")
    public String SaleItemUpload(@SessionAttribute(name = SESSION_ID, required = false) Member member){
        if(member==null){
           return "redirect:/login";
        }
        return "saleitemupload";
    }

    @PostMapping("/saleitemupload")
    public String PostSaleItemUpload(@SessionAttribute(name = SESSION_ID, required = false) Member login,
                                     SaleItemDTO saleItemDTO,
                                     @RequestParam MultipartFile file) throws IOException {
        if(login == null){
            return "redirect:/";
        }
        System.out.println(saleItemDTO.getTitle());
        System.out.println(saleItemDTO.getContent());
        System.out.println(saleItemDTO.getEndTime());
        System.out.println(file.getOriginalFilename());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime LDT = LocalDateTime.parse(saleItemDTO.getEndTime(), formatter);
        file.transferTo(new File("D:\\"+file.getOriginalFilename()));
        IR.saveItem(new Product(login.getId(), saleItemDTO.getStartPrice(), file.getOriginalFilename(), saleItemDTO.getContent(), saleItemDTO.getTitle(), LDT));


        return "redirect:/";
    }
}
