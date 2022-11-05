package com.everyAuction.everyAuction.Repository;

import com.everyAuction.everyAuction.Domain.LoginForm;
import com.everyAuction.everyAuction.Domain.Member;
import com.everyAuction.everyAuction.Domain.productReply;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class replyRepository {
    private final JdbcTemplate jdbcTemplate;

    public void saveReply(productReply reply){
        jdbcTemplate.update(
                "insert into productReply(productId, userId, content, uploadTime) values(?,?,?,?)",
                reply.getProductId(),
                reply.getUserId(),
                reply.getContent(),
                reply.getUploadTime()
        );
    }

    public void deleteReply(int id, String userId){
        jdbcTemplate.update(
                "delete from productReply where id=? and userId=?",
                id,
                userId
        );
    }

    public void editReply(int id, String userId, String content){
        jdbcTemplate.update(
                "update productReply set content = ? where id=? and userId=?",
                content,
                id,
                userId
        );
    }

    public List<productReply> findAllReply(int id){
        List<productReply> query = jdbcTemplate.query(
                "select * from productReply where productId=?",
                (rs, rowNum) -> {
                    productReply reply = new productReply(
                            rs.getInt("productId"),
                            rs.getString("userId"),
                            rs.getString("content"),
                            rs.getObject("uploadTime", LocalDateTime.class)
                    );
                    reply.setId(rs.getInt("id"));
                    return reply;
                }
                ,id
        );
        return query;
    }
}
