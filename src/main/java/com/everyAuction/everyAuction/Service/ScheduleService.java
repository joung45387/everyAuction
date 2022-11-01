package com.everyAuction.everyAuction.Service;

import com.everyAuction.everyAuction.Domain.ScheduledProduct;
import com.everyAuction.everyAuction.Repository.BidRepository;
import com.everyAuction.everyAuction.Repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

@Component
@RequiredArgsConstructor
public class ScheduleService extends Thread{
    PriorityQueue<ScheduledProduct> pq = new PriorityQueue<>(new Comparator<ScheduledProduct>() {
        @Override
        public int compare(ScheduledProduct o1, ScheduledProduct o2) {
            return o1.getEndTime().compareTo(o2.getEndTime());
        }
    });
    private final BidRepository BR;
    private final ItemRepository IR;
    boolean isStop = false;
    public void run(){
        while (true){
            if(!pq.isEmpty()){
                Duration between = Duration.between(LocalDateTime.now(ZoneId.of("Asia/Seoul")), pq.peek().getEndTime());
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
                ScheduledProduct productInfo = pq.poll();
                String bidder = BR.findBidder(productInfo.getId());
                System.out.println(bidder);
                IR.updateBidder(bidder, productInfo.getId());
                System.out.println("동작 완료! 남은 작업:"+pq.size());
            }
            isStop = false;
        }
    }
    public void noti(ScheduledProduct sp){
        isStop = true;
        pq.add(sp);
        synchronized (this){
            notifyAll();
        }
    }

    public void init(){
        List<ScheduledProduct> scheduledProducts = BR.saleingBidRecord();
        for(ScheduledProduct scheduledProduct : scheduledProducts){
            noti(scheduledProduct);
        }
    }
}