package com.example.couponcore.component;

import com.example.couponcore.model.event.CouponIssueCompleteEvent;
import com.example.couponcore.service.CouponCacheService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class CouponEventListener { // 쿠폰 캐시 매니저의 역할을 하고 있습니다.

    private final CouponCacheService couponCacheService;
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async
    void issueComplete1(CouponIssueCompleteEvent event) {
        log.info("issue complete. cache refresh start couponId: %s".formatted(event.couponId()));
        couponCacheService.putCouponCache(event.couponId());
        couponCacheService.putCouponLocalCache(event.couponId());
        log.info("issue complete cache refresh end couponId: %s".formatted(event.couponId()));
    }

//    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
//    void issueComplete2(CouponIssueCompleteEvent event) {
//        log.info("issue complete. cache refresh start couponId: %s".formatted(event.couponId()));
//        couponCacheService.putCouponCache(event.couponId());
//        couponCacheService.putCouponLocalCache(event.couponId());
//        log.info("issue complete cache refresh end couponId: %s".formatted(event.couponId()));
//    }

    /*
    Try {
    begin
    ~
    commit
    } catch {
    rollback
    }
     */
}
