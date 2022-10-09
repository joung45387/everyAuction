package com.everyAuction.everyAuction.Repository;

import com.everyAuction.everyAuction.Domain.LoginForm;
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
    public Member findMember(LoginForm lf){
        List<Member> query = jdbcTemplate.query(
                "select * from users where id=? and password=?",
                (rs, rowNum) -> {
                    Member member = new Member(
                            rs.getString("id"),
                            rs.getString("password"),
                            rs.getString("name"),
                            rs.getString("phoneNumber"),
                            rs.getString("address")
                    );
                    return member;
                },
                lf.getLoginId(), lf.getLoginPassword()
        );
        return query.isEmpty()?null:query.get(0);
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

    public boolean poslogin(LoginForm lf){
        List<String> query = jdbcTemplate.query(
                "select id from users where id=? and password=?",
                (rs, rowNum) -> {
                    String s = new String(
                            rs.getString("id")
                    );
                    return s;
                },
                lf.getLoginId(), lf.getLoginPassword()
        );
        return query.isEmpty()?false:true;
    }
}
