package com.everyAuction.everyAuction.Controller;

import com.everyAuction.everyAuction.Service.ScheduleService;

import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Controller
@RequiredArgsConstructor
public class testpage {

    private final JdbcTemplate jdbcTemplate;
    private final ScheduleService t;
    @GetMapping("/")
    public String a(){
        /*t.pq.add(1);
        String a = jdbcTemplate.queryForObject("select count(*) from MEMBER", String.class);
        System.out.println(a);*/
        return "mainpage";
    }

    @GetMapping("/a")
    public String aa(){
        System.out.println("121212");
        /*String a = jdbcTemplate.queryForObject("select count(*) from MEMBER", String.class);
        System.out.println(a);*/
        return "a";
    }

    @GetMapping("/date")
    public String date(){


        return "b";
    }
    @PostMapping("/date")
    public String datep(@RequestParam String starttime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime q = LocalDateTime.parse(starttime, formatter);
        t.noti(q);
        return "b";
    }
    @GetMapping("/select")
    public String ac(){
        String sql = "select count(*) from member";
        String s = jdbcTemplate.queryForObject(sql, String.class);
        System.out.println(s);
        return "a";
    }

    @GetMapping("/dd")
    public String acfsd(){
        String sql = "select count(*) from member";
        String s = jdbcTemplate.queryForObject(sql, String.class);
        System.out.println(s);
        return "test";
    }

    /*@GetMapping("/{name}")
    @ResponseBody
    public Resource aaa(@PathVariable String name){
        System.out.println(name);
        try {
            return new UrlResource("file:D:\\"+name);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }*/

    @PostMapping("/")
    public String save(@RequestParam MultipartFile file) throws IOException {
        System.out.println(file);
        file.transferTo(new File("D:\\"+file.getOriginalFilename()));
        return "a";
    }
}
