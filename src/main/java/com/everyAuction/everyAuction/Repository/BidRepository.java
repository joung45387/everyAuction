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

    public List<BidRecord> findbyproductid(int id){
        List<BidRecord> findbyproductid = jdbcTemplate.query("select id, bidUserId, productId, bidTime, bidPrice from bidRecord where productId = ? order by id desc",
                (rs, rowNum) -> {
                    BidRecord br = new BidRecord(
                            rs.getString("bidUserId"),
                            rs.getInt("productId"),
                            rs.getObject("bidTime", LocalDateTime.class),
                            rs.getInt("bidPrice")
                    );
                    br.setBidRecordId(rs.getInt("id"));
                    return br;
                },
                id
        );
        return findbyproductid;
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


    public List<Product> completebidrecord(String id){
        List<Product> findcompleteproduct = jdbcTemplate.query("select id, title, productPhoto,endTime from product where endTime<? and salesUser = ?",
                (rs, rowNum) -> {
                    Product sp = new Product();
                    sp.setId(rs.getInt("id"));
                    sp.setTitle(rs.getString("title"));
                    sp.setProductPhoto(rs.getBytes("productPhoto"));
                    sp.setEndTime(rs.getObject("endTime", LocalDateTime.class));
                    return sp;
                },
                LocalDateTime.now(ZoneId.of("Asia/Seoul")),
                id
        );
        return findcompleteproduct;
    }

    public List<Product> completebuybidrecord(String id){
        List<Product> findcompleteproduct = jdbcTemplate.query("select id, title, productPhoto,endTime from product where endTime<? and buyer = ?",
                (rs, rowNum) -> {
                    Product sp = new Product();
                    sp.setId(rs.getInt("id"));
                    sp.setTitle(rs.getString("title"));
                    sp.setProductPhoto(rs.getBytes("productPhoto"));
                    sp.setEndTime(rs.getObject("endTime", LocalDateTime.class));
                    return sp;
                },
                LocalDateTime.now(ZoneId.of("Asia/Seoul")),
                id
        );
        return findcompleteproduct;
    }

    /*public List<Product> 함수 (상품id)
    where문 상품id=id
    거래기록에서 보여줄것들
     */


    public String findBidder (int productId){
        List<String> buyer = jdbcTemplate.query("select bidUserId from bidRecord where productId=? and bidPrice = (select max(bidPrice) from bidRecord where productId = ?)",
                (rs, rowNum) -> rs.getString("bidUserId"),
                productId,
                productId
        );
        return buyer.isEmpty()?null:buyer.get(0);
    }
}
