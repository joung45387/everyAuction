package com.everyAuction.everyAuction.Controller;



import com.everyAuction.everyAuction.Domain.Member;
import com.everyAuction.everyAuction.Domain.Message;
import com.everyAuction.everyAuction.Repository.chatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.SessionAttribute;

import static com.everyAuction.everyAuction.Controller.testpage.SESSION_ID;

@Controller
@RequiredArgsConstructor
public class MessageController {
    private final SimpMessageSendingOperations simpMessageSendingOperations;
    private final chatRepository CR;

    @MessageMapping("/test")
    public void message(Message message){
        simpMessageSendingOperations.convertAndSend("/sub/test", message);
    }
    @MessageMapping("/sendchat/{id}")
    public void price(Message message, @DestinationVariable("id") int id){
        CR.saveChatRecord(message, id);
        simpMessageSendingOperations.convertAndSend("/sub/sendchat/"+id, message);
    }

}
