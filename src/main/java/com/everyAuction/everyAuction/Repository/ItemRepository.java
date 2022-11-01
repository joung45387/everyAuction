package com.everyAuction.everyAuction.Repository;

import com.everyAuction.everyAuction.Domain.Member;
import com.everyAuction.everyAuction.Domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final JdbcTemplate jdbcTemplate;

    public int saveItem(Product product){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement("insert into product(salesUser, startPrice, productPhoto, ProductInformation, title, endTime, currentPrice) values(?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, product.getSaleUser());
            ps.setInt(2, product.getStartPrice());
            ps.setBytes(3, product.getProductPhoto());
            ps.setString(4, product.getProductInformation());
            ps.setString(5, product.getTitle());
            ps.setObject(6, product.getEndTime());
            ps.setInt(7, product.getCurrentPrice());

            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();



        /*jdbcTemplate.update(
                "insert into product(salesUser, startPrice, productPhoto, ProductInformation, title, endTime, currentPrice) values(?,?,?,?,?,?,?)",
                product.getSaleUser(),
                product.getStartPrice(),
                product.getProductPhoto(),
                product.getProductInformation(),
                product.getTitle(),
                product.getEndTime(),
                product.getCurrentPrice(),
                keyHolder
        );
        return keyHolder.getKey().intValue();*/
    }

    public void updateCurrentPrice(int id, int price){
        jdbcTemplate.update(
                "update product set currentPrice = ? where id= ?", price, id
        );
    }

    public List<Product> findAll(){
        List<Product> productList = jdbcTemplate.query(
                "select * from product",
                (rs, rowNum) -> {
                    Product product = new Product();
                    product.setId(rs.getInt("id"));
                    product.setSaleUser(rs.getString("salesUser"));
                    product.setStartPrice(rs.getInt("startPrice"));
                    product.setProductPhoto(rs.getBytes("productPhoto"));
                    product.setProductInformation(rs.getString("ProductInformation"));
                    product.setTitle(rs.getString("title"));
                    product.setEndTime(rs.getObject("endTime", LocalDateTime.class));
                    product.setCurrentPrice(rs.getInt("currentPrice"));
                    return product;
                }
        );
        return productList;
    }

    public List<Product> saleAll(){
        List<Product> productList = jdbcTemplate.query(
                "select * from product where endTime>?",
                (rs, rowNum) -> {
                    Product product = new Product();
                    product.setId(rs.getInt("id"));
                    product.setSaleUser(rs.getString("salesUser"));
                    product.setStartPrice(rs.getInt("startPrice"));
                    product.setProductPhoto(rs.getBytes("productPhoto"));
                    product.setProductInformation(rs.getString("ProductInformation"));
                    product.setTitle(rs.getString("title"));
                    product.setEndTime(rs.getObject("endTime", LocalDateTime.class));
                    product.setCurrentPrice(rs.getInt("currentPrice"));
                    product.setBuyer(rs.getString("buyer"));
                    return product;
                },
                LocalDateTime.now(ZoneId.of("Asia/Seoul"))
        );
        return productList;
    }

    public Product findById(int id){
        List<Product> productList = jdbcTemplate.query(
                "select * from product where id=?",
                (rs, rowNum) -> {
                    Product product = new Product();
                    product.setId(rs.getInt("id"));
                    product.setSaleUser(rs.getString("salesUser"));
                    product.setStartPrice(rs.getInt("startPrice"));
                    product.setProductPhoto(rs.getBytes("productPhoto"));
                    product.setProductInformation(rs.getString("ProductInformation"));
                    product.setTitle(rs.getString("title"));
                    product.setEndTime(rs.getObject("endTime", LocalDateTime.class));
                    product.setCurrentPrice(rs.getInt("currentPrice"));
                    product.setBuyer(rs.getString("buyer"));
                    return product;
                },
                id
        );
        return productList.get(0);
    }

    public void updateBidder(String bidder, int productId) {
        jdbcTemplate.update(
                "update product set buyer = ? where id=?",
                bidder==null?"유찰":bidder,
                productId
        );
    }
}
