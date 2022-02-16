# 스프링 핵심 원리 - 기본편
## 비즈니스 요구사항과 설계
- 회원
    - 회원을 가입하고 조회할 수 있다.
    - 회원은 일반과 VIP 두가지 등급이 있다
    - 회원 데이터는 자체 DB를 구축할 수 있고, 외부 시스템과 연동할 수 있다. (미확정)
- 주문과 할인 정책
    - 회원은 상품을 주문할 수 있다.
    - 회원 등급에 따라 할인 정책을 적용할 수 있다.
    - 할인 정책은 모든 VIP는 1000원을 할인해주는 고정 금액 할인을 적용해달라. (나중에 변경 될 수 있다.)
    - 할인 정책은 변경 가능성이 높다. 회사의 기본 할인 정책을 아직 정하지 못했고, 오픈 직전까지 고민을 미루고 싶다. 최악의 경우 할인을 적용하지 않을 수도 있다. (미확정)

## 회원 도메인 설계
- 회원 도메인 요구사항
    - 회원을 가입하고 조회할 수 있다.
    - 회원은 일반과 VIP 두가지 등급이 있다.
    - 회원 데이터는 자체 DB를 구축할 수 있고, 외부 시스템과 연동할 수 있다. (미확정)

## 회원 도메인 개발
### 회원 엔티티
- 회원 등급 : enum Grade
- 회원 엔티티 : class Member
### 회원 저장소
- 회원 저장소 인터페이스 : interface MemberRepository
- 메모리 회원 저장소 구현체 : class MemoryMemberRepository implements MemberRepository
### 회원 서비스
- 회원 서비스 인터페이스 : interface MemberService
- 회원 서비스 구현체 : class MemberServiceImpl implements MemberService

## 주문과 할인 도메인 설계
- 주문과 할인 정책
    - 회원은 상품을 주문할 수 있다.
    - 회원 등급에 따라 할인 정책을 적용할 수 있다.
    - 할인 정책은 모든 VIP는 1000원을 할인해주는 고정 금액 할인을 적용해달라. (나중에 변경될 수 있다.)
    - 할인 정책은 변경 가능성이 높다. 회사의 기본 할인 정책을 아직 정하지 못했고, 오픈 직전까지 고민을 미루고 싶다. 최악의 경우 할인을 적용하지 않을 수도 있다. (미확정)

### 주문 도메인 협력, 역할 책임
1. 주문 생성 : 클라이언트는 주문 서비스에 주문 생성을 요청한다.
2. 회원 조회 : 할인을 위해서는 회원 등급이 필요하다. 그래서 주문 서비스는 회원 저장소에서 회원을 조회한다.
3. 할인 적용 : 주문 서비스는 회원 등급에 따른 할인 여부를 할인 정책에 위임한다.
4. 주문 결과 반환 : 주문 서비스는 할인 결과를 포함한 주문 결과를 반환한다.

## 주문과 할인 도메인 개발
- 할인 정책 인터페이스 : interface DiscountPolicy
- 정액 할인 정책 구현체 : class FixDiscountPolicy implements DiscountPolicy
- 주문 엔티티 : class Order
- 주문 서비스 인터페이스 : interface OrderService
- 주문 서비스 구현체 : class OrderServiceImpl implements OrderService
### 새로운 할인 정책 개발
- 정률 할인 정책 구현체 : class RateDiscountPolicy implements DiscountPolicy

## 관심사의 분리
- AppConfig 설정 클래스를 통해 객체의 생성과 연결을 하게 된다.(구성 영역)(DI컨테이너)
- 사용 영역은 할인 정책 등을 변경해도 코드의 변경이 필요없다.
- SRP, DIP, OCP를 준수하게 된다.

## 스프링으로 전환하기
### AppConfig 스프링 기반으로 변경
- AppConfig에 설정을 구성한다는 뜻의 @Configuration을 붙여준다.
- 각 메서드에 @Bean을 붙여줌으로서 스프링 컨테이너에 스프링 빈으로 등록한다.

### MemberApp에 스프링 컨테이너 적용
- ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
- MemberService memberService = applicationContext.getBean("memberService",MemberService.class);

### OrderApp에 스프링 컨테이너 적용
- OrderService orderService = applicationContext.getBean("orderService",OrderService.class);
