# 네고왕 이벤트 선착순 쿠폰 시스템

## Background
네고왕 선착순 쿠폰 이벤트는 한정된 수량의 쿠폰을 먼저 신청한 사용자에게 제공하는 이벤트입니다. 

## Requirements
- 이벤트 기간내에(ex 2023-11-03일 오후 1시 ~ 2023-11-04일 오후 1시) 발급이 가능합니다.
- 선착순 이벤트는 유저당 1번의 쿠폰 발급만 가능합니다.
- 선착순 쿠폰의 최대 쿠폰 발급 수량을 설정할 수 있어야합니다.

## Architecture

### 비동기 쿠폰 발급 요청 처리 구조

### 캐시 데이터 기반 Validation

### Tech Stack

**Infra**  
Aws EC2, Aws RDS, Aws Elastic Cache,

**Server**  
Java 17, Spring Boot 3.1, Spring Mvc, JPA, QueryDsl

**Database**  
Mysql, Redis, H2

**Monitoring**   
Aws Cloud Watch, Spring Actuator, Promethous, Grafana

**Etc**  
Locust, Gradle, Docker

## Main Feature

### 쿠폰 발급 검증
- 발급 기한
- 발급 수량
- 중복 발급

### 쿠폰 발급 수량 관리
- Redis Set기반 재고 관리

### 비동기 쿠폰 발급
- Redis List (발급 Queue)
- Queue Polling Scheduler 

## Result

#### server spec
- coupon-api, coupon-consumer -> t2.medium (2core, 4G) 
- Mysql -> db.t3.micro (free-tier)
- Redis -> cache.t3.micro	(free-tier)

### Load Test
Runtime : 5m  
Number of Users : 5,000

#### Request Statics
Total Requests : 1,696,253
Failed Requests : 1,315
Failure rate : 0.077%   
RPS : 4958  
  
#### Response Statics
p50 : 900 ms  
p70 : 1200 ms  
p90 : 1500 ms  
p90 : 2000 ms  
p99 : 3600 ms  
max : 14000 ms  

<img width="1032" alt="image" src="https://github.com/prod-j/coupon-version-management/assets/148772205/9a1b0725-a8a9-4a86-9d49-85490351dcc8">

#### Chart
<img width="786" alt="image" src="https://github.com/prod-j/coupon-version-management/assets/148772205/9b37ab5f-7d77-41e0-9358-da0aea10a725">

#### Metric
<img width="1656" alt="image" src="https://github.com/prod-j/coupon-version-management/assets/148772205/9e3936c5-698d-40cb-b4f4-60e98993687e">


#### How to run
1) intellij -> edit configuration, profile 'local'
2) at each dir, docker compose up -d
3) create db using coupon-core/resources/sql/schema.sql, and then make user - id:abcd, pw:1234
4) INSERT INTO coupon_issues
VALUES (1, '네고왕', 'FIRST_COME_FIRST_SERVED', 500, 0, 100000, 110000, '2024-09-26 00:40:02.000000', '2024-09-30 01:40:20.000000', '2024-09-26 01:40:37.000000', '2024-09-26 01:48:47.356114');
5) run all services and do test