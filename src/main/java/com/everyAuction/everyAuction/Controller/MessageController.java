package com.everyAuction.everyAuction.Controller;



import com.everyAuction.everyAuction.Domain.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MessageController {
    private final SimpMessageSendingOperations simpMessageSendingOperations;

    @MessageMapping("/test")
    public void message(Message message){
        simpMessageSendingOperations.convertAndSend("/sub/test", message);
    }
    /*@MessageMapping("/productId/{id}")
    public void price(Message message, @DestinationVariable("id") int id){
        System.out.println("/sub/productId/"+id);
        simpMessageSendingOperations.convertAndSend("/sub/productId/"+id, message.getText());
    }*/

}
