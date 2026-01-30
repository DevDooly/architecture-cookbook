# Architecture Cookbook 🍳

**Specific solutions for specific engineering problems.**

이 프로젝트는 백엔드 개발 시 마주치는 다양한 트래픽 상황, 데이터 처리 문제, 시스템 아키텍처 이슈들을 해결하기 위한 **'구조적 해법(Architecture Patterns)'**과 **'구현 전략'**을 모아둔 레시피 저장소입니다.

단순한 코드 스니펫 저장이 아닌, **"어떤 상황(Context)에서, 어떤 구조(Architecture)를, 왜(Why) 사용해야 하는가?"**에 집중합니다.

## 🎯 Project Goal
- 특정 상황(대용량 트래픽, 동시성 이슈 등)에 최적화된 아키텍처 설계 능력 배양
- 문제 해결을 위한 적절한 도구(Redis, Kafka, gRPC 등)와 알고리즘 선정 기준 확립
- 실제 프로덕션 환경을 고려한 안정적인 시스템 설계 문서화

## 📚 Scenarios (Table of Contents)

### High Traffic & Concurrency
- **[Scenario 01]** 대규모 트래픽 발생 시 순서 보장 및 유량 제어 (Ticket Booking, Flash Sale) `Wait-Queue` `Redis` `Throttling`
- **[Scenario 02]** 재고 동시성 이슈 해결 (Inventory Race Condition) `Pessimistic-Lock` `Distributed-Lock` `Atomic-Operation`

### Data Consistency & Distributed System
- *(작성 예정)* 분산 트랜잭션 처리 전략 (Saga Pattern vs 2PC)
- *(작성 예정)* CQRS 패턴을 활용한 읽기/쓰기 성능 분리

### Spring Framework Internals
- **[Scenario 03]** Spring Boot Request Lifecycle (Deep Dive) `DispatcherServlet` `HandlerMapping` `Filter vs Interceptor`

## 🛠 Tech Stack & Keywords
- **Core:** Java/Spring Boot, Python, Go etc.
- **Data & Cache:** Redis (In-memory), MySQL, PostgreSQL
- **Messaging:** Kafka, RabbitMQ
- **Protocol:** gRPC, REST, WebSocket
- **Concepts:** Rate Limiting, Backpressure, Circuit Breaker, Consistent Hashing

---
*Last updated: 2026-01-22*