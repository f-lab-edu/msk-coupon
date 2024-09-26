package com.example.couponcore.repository.mysql;

import com.example.couponcore.model.Coupon;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CouponJpaRepository extends JpaRepository<Coupon, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE) //flab: PESSIMISTIC_READ 을 사용하면 select ~ lock in share mode 쿼리가 발생함
    @Query("SELECT c FROM Coupon c WHERE c.id = :id") //flab: select ~ for update 쿼리가 발생함
    Optional<Coupon> findCouponWithLock(long id);

}
