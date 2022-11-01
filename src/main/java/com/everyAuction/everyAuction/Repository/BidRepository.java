package com.everyAuction.everyAuction.Repository;

import com.everyAuction.everyAuction.Domain.BidRecord;
import com.everyAuction.everyAuction.Domain.Product;
import com.everyAuction.everyAuction.Domain.ScheduledProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BidRepository {
    private final JdbcTemplate jdbcTemplate;

    public void saveBidRecord(BidRecord bidRecord){
        jdbcTemplate.update(
                "insert into bidRecord(bidUserId, ProductId, bidTime, bidPrice) values(?,?,?,?)",
                bidRecord.getBidUserId(),
                bidRecord.getProductId(),
                bidRecord.getBidTime(),
                bidRecord.getBidPrice()
        );
    }

    public void saveCompleteBidRecord(BidRecord bidRecord){
        jdbcTemplate.update(
                "insert into bidRecord(bidUserId, ProductId, bidTime, bidPrice) values(?,?,?,?)",
                bidRecord.getBidUserId(),
                bidRecord.getProductId(),
                bidRecord.getBidTime(),
                bidRecord.getBidPrice()
        );
    }

    public List<ScheduledProduct> saleingBidRecord(){
        List<ScheduledProduct> scheduledProducts = jdbcTemplate.query("select id, endTime from product where endTime>?",
                (rs, rowNum) -> {
                    ScheduledProduct sp = new ScheduledProduct();
                    sp.setId(rs.getInt("id"));
                    sp.setEndTime(rs.getObject("endTime", LocalDateTime.class));
                    return sp;
                },
                LocalDateTime.now(ZoneId.of("Asia/Seoul"))
        );
        return scheduledProducts;
    }


    public List<Product> completebidrecord(){
        List<Product> findcompleteproduct = jdbcTemplate.query("select id, title, productPhoto,endTime from product where endTime<?",
                (rs, rowNum) -> {
                    Product sp = new Product();
                    sp.setId(rs.getInt("id"));
                    sp.setTitle(rs.getString("title"));
                    sp.setProductPhoto(rs.getBytes("productPhoto"));
                    sp.setEndTime(rs.getObject("endTime", LocalDateTime.class));
                    return sp;
                },
                LocalDateTime.now(ZoneId.of("Asia/Seoul"))
        );
        return findcompleteproduct;
    }

    public String findBidder (int productId){
        List<String> buyer = jdbcTemplate.query("select bidUserId from bidRecord where productId=? and bidPrice = (select max(bidPrice) from bidRecord where productId = ?)",
                (rs, rowNum) -> rs.getString("bidUserId"),
                productId,
                productId
        );
        return buyer.isEmpty()?null:buyer.get(0);
    }
}
