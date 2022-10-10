package com.everyAuction.everyAuction.Repository;

import com.everyAuction.everyAuction.Domain.Member;
import com.everyAuction.everyAuction.Domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final JdbcTemplate jdbcTemplate;

    public void saveItem(Product product){
        jdbcTemplate.update(
                "insert into product(salesUser, startPrice, productPhoto, ProductInformation, title, endTime) values(?,?,?,?,?,?)",
                product.getSaleUser(),
                product.getStartPrice(),
                product.getProductPhoto(),
                product.getProductInformation(),
                product.getTitle(),
                product.getEndTime()
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
                    return product;
                }
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
                    return product;
                },
                id
        );
        return productList.get(0);
    }
}
