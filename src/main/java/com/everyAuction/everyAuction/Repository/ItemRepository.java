package com.everyAuction.everyAuction.Repository;

import com.everyAuction.everyAuction.Domain.Member;
import com.everyAuction.everyAuction.Domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final JdbcTemplate jdbcTemplate;

    public void saveItem(Product product){
        jdbcTemplate.update(
                "insert into product(salesUser, startPrice, productPhoto, ProductInformation, title, endTime, currentPrice) values(?,?,?,?,?,?,?)",
                product.getSaleUser(),
                product.getStartPrice(),
                product.getProductPhoto(),
                product.getProductInformation(),
                product.getTitle(),
                product.getEndTime(),
                product.getCurrentPrice()
        );
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
                    product.setProductPhoto(rs.getString("productPhoto"));
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
                    product.setProductPhoto(rs.getString("productPhoto"));
                    product.setProductInformation(rs.getString("ProductInformation"));
                    product.setTitle(rs.getString("title"));
                    product.setEndTime(rs.getObject("endTime", LocalDateTime.class));
                    product.setCurrentPrice(rs.getInt("currentPrice"));
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
                    product.setProductPhoto(rs.getString("productPhoto"));
                    product.setProductInformation(rs.getString("ProductInformation"));
                    product.setTitle(rs.getString("title"));
                    product.setEndTime(rs.getObject("endTime", LocalDateTime.class));
                    product.setCurrentPrice(rs.getInt("currentPrice"));
                    return product;
                },
                id
        );
        return productList.get(0);
    }
}
