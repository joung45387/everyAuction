package com.everyAuction.everyAuction.Repository;

import com.everyAuction.everyAuction.Domain.BidRecord;
import com.everyAuction.everyAuction.Domain.Member;
import com.everyAuction.everyAuction.Domain.Message;
import com.everyAuction.everyAuction.Domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class chatRepository {
    private final JdbcTemplate jdbcTemplate;

    public void saveChatRecord(Message message, int productId){
        jdbcTemplate.update(
                "insert into chats(sender, product, content, chatTime) values(?,?,?,?)",
                message.getSender(),
                productId,
                message.getText(),
                LocalDateTime.now(ZoneId.of("Asia/Seoul"))
        );
    }

    public List<Message> findAllChat(int productId){
        List<Message> chatList = jdbcTemplate.query(
                "select * from chats where product = ?",
                (rs, rowNum) -> {
                    Message message = new Message();
                    message.setSender(rs.getString("sender"));
                    message.setText(rs.getString("content"));
                    return message;
                },
                productId
        );
        return chatList;
    }
}
