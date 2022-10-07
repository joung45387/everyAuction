package com.everyAuction.everyAuction.Repository;

import com.everyAuction.everyAuction.Domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final JdbcTemplate jdbcTemplate;

    public void saveMember(Member member){
        jdbcTemplate.update(
                "insert into users values(?,?,?,?,?)",
                member.getId(),
                member.getPassword(),
                member.getName(),
                member.getPhoneNumber(),
                member.getAddress()
        );
    }
    public boolean idExistence(String id){
        List<String> query = jdbcTemplate.query(
                "select id from users where id=?",
                (rs, rowNum) -> {
                    String s = new String(
                            rs.getString("id")
                    );
                    return s;
                },
                id
        );
        return query.isEmpty()?false:true;
    }
}
