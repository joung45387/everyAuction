package com.everyAuction.everyAuction.Service;

import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.PriorityQueue;

@Component
public class scheduleService extends Thread{
    PriorityQueue<LocalDateTime> pq = new PriorityQueue<>();
    boolean isStop = false;
    public void run(){
        while (true){
            if(!pq.isEmpty()){
                Duration between = Duration.between(LocalDateTime.now(ZoneId.of("Asia/Seoul")), pq.peek());
                System.out.println(pq.size());
                System.out.println(between.toMillis()+"밀스 동안 멈춤후 동작");
                try {
                    synchronized (this){
                        if(between.toMillis()>=0){
                            wait(between.toMillis());
                        }
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                try {
                    synchronized (this){
                        wait();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            if(!isStop){
                System.out.println("동작 완료! 남은 작업:"+pq.size());
                pq.poll();
            }
            isStop = false;
        }
    }
    public void noti(LocalDateTime ldt){
        isStop = true;
        pq.add(ldt);
        synchronized (this){
            notifyAll();
        }
    }
}