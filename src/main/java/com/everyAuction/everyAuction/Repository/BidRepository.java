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


    public List<Product> completebidrecordNoBuyer(String id){
        List<Product> findcompleteproduct = jdbcTemplate.query("select p.id, p.title, p.productPhoto,p.endTime, temp.bidUserId\n" +
                        "from product p left join\n" +
                        "(select * from bidRecord a where (productId, bidPrice) in (select productId, max(bidPrice) from bidRecord b  group by productId)) temp\n" +
                        "on p.id = temp.productId where p.endTime<? and p.salesUser = ?",
                (rs, rowNum) -> {
                    Product sp = new Product();
                    sp.setId(rs.getInt("id"));
                    sp.setTitle(rs.getString("title"));
                    sp.setProductPhoto(rs.getBytes("productPhoto"));
                    sp.setEndTime(rs.getObject("endTime", LocalDateTime.class));
                    sp.setBuyer(rs.getString("bidUserId"));
                    return sp;
                },
                LocalDateTime.now(ZoneId.of("Asia/Seoul")),
                id
        );
        return findcompleteproduct;
    }

    public List<Product> completebidrecord(String id){
        List<Product> findcompleteproduct = jdbcTemplate.query("select id, title, productPhoto,endTime, buyer from product where endTime<? and salesUser = ?",
                (rs, rowNum) -> {
                    Product sp = new Product();
                    sp.setId(rs.getInt("id"));
                    sp.setTitle(rs.getString("title"));
                    sp.setProductPhoto(rs.getBytes("productPhoto"));
                    sp.setEndTime(rs.getObject("endTime", LocalDateTime.class));
                    sp.setBuyer(rs.getString("buyer"));
                    return sp;
                },
                LocalDateTime.now(ZoneId.of("Asia/Seoul")),
                id
        );
        return findcompleteproduct;
    }

    public List<Product> completebuybidrecord(String id){
        List<Product> findcompleteproduct = jdbcTemplate.query("select id, title, productPhoto,endTime, buyer,salesUser from product where endTime<? and buyer = ?",
                (rs, rowNum) -> {
                    Product sp = new Product();
                    sp.setId(rs.getInt("id"));
                    sp.setTitle(rs.getString("title"));
                    sp.setProductPhoto(rs.getBytes("productPhoto"));
                    sp.setEndTime(rs.getObject("endTime", LocalDateTime.class));
                    sp.setBuyer(rs.getString("buyer"));
                    sp.setSaleUser(rs.getString("salesUser"));
                    return sp;
                },
                LocalDateTime.now(ZoneId.of("Asia/Seoul")),
                id
        );
        return findcompleteproduct;
    }

    public List<Product> findBuyRecord(String id){
        List<Product> findcompleteproduct = jdbcTemplate.query("select product.id,title,productPhoto, endTime, buyer, salesUser from bidRecord, product " +
                        "where bidRecord.productId = product.id " +
                        "and bidUserId = ?" +
                        "and endTime< ?" +
                        "group by productId",
                (rs, rowNum) -> {
                    Product sp = new Product();
                    sp.setId(rs.getInt("id"));
                    sp.setTitle(rs.getString("title"));
                    sp.setProductPhoto(rs.getBytes("productPhoto"));
                    sp.setEndTime(rs.getObject("endTime", LocalDateTime.class));
                    sp.setBuyer(rs.getString("buyer"));
                    sp.setSaleUser(rs.getString("salesUser"));
                    return sp;
                },
                id,
                LocalDateTime.now(ZoneId.of("Asia/Seoul"))
        );
        return findcompleteproduct;
    }

    public List<Product> findBuyRecordNoBuyer(String id){
        List<Product> findcompleteproduct = jdbcTemplate.query("select *\n" +
                        "from product \n" +
                        "inner join (select t2.productId, t2.bidUserId\n" +
                        "\t\t\tfrom (select productId \n" +
                        "\t\t\t\t  from bidRecord \n" +
                        "                  where bidUserId = ? \n" +
                        "                  group by productId) t1\n" +
                        "\t\t\tinner join (select productId,bidUserId \n" +
                        "\t\t\t\t\t   from bidRecord \n" +
                        "\t\t\t\t\t   where (productId,bidPrice) in (select productId, max(bidPrice) \n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t  from bidRecord \n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t  group by productId)) t2\n" +
                        "\t\t\ton t1.productId = t2.productId) t3\n" +
                        "on product.id = t3.productId\n" +
                        "and endTime<?\n",
                (rs, rowNum) -> {
                    Product sp = new Product();
                    sp.setId(rs.getInt("id"));
                    sp.setTitle(rs.getString("title"));
                    sp.setProductPhoto(rs.getBytes("productPhoto"));
                    sp.setEndTime(rs.getObject("endTime", LocalDateTime.class));
                    sp.setBuyer(rs.getString("bidUserId"));
                    sp.setSaleUser(rs.getString("salesUser"));
                    return sp;
                },
                id,
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
