package com.everyAuction.everyAuction.Controller;

import com.everyAuction.everyAuction.Domain.*;
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

import javax.sql.rowset.serial.SerialException;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.everyAuction.everyAuction.Controller.testpage.SESSION_ID;

@Controller
@RequiredArgsConstructor
public class SaleItemUpload {

    private final ItemRepository IR;
    private final ScheduleService SS;

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime LDT = LocalDateTime.parse(saleItemDTO.getEndTime(), formatter);


        Map<String, Object> blob = new HashMap<String, Object>();
        String fileName = file.getOriginalFilename();
        byte[] bytes;
        /*try{
            bytes = file.getBytes();
            Blob blobdata = new javax.sql.rowset.serial.SerialBlob(bytes);
            blob.put("file", blobdata);
            blob.put("fileName", fileName);
            blob.put("fileSize", blobdata.length());
        } catch (SerialException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/

        /*if(!file.isEmpty()){
            file.transferTo(new File("D:\\"+file.getOriginalFilename()));
        }*/
        int id = IR.saveItem(new Product(login.getId(), saleItemDTO.getStartPrice(), file.getBytes(), saleItemDTO.getContent(), saleItemDTO.getTitle(), LDT, saleItemDTO.getStartPrice()));
        System.out.println(id);
        SS.noti(new ScheduledProduct((int)id, LDT));

        return "redirect:/";
    }
}
