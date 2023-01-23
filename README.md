# 배달의 민족 서버 클론코딩
![image](https://user-images.githubusercontent.com/59828706/214031918-a6a41524-6f32-4e71-9445-991cac8c8427.png)

자바(IntelliJ), 스프링(IntelliJ), MySQL를 이용하여 배달의 민족 서버를 클론코딩 하였습니다.  
배달의 민족의 모든 기능을 구현한 것은 아니지만 핵심 기능을 위주로 구현하였습니다.  
과정에서 DB 설계, MVC 패턴과 JWT 및 소셜로그인에 대해 공부할 수 있었습니다.

# Spring Boot Template
본 템플릿은 소프트스퀘어드 서버 교육용 Spring Boot 템플릿 입니다. (2022 ver.)

## 목차
### [1. ERD 모델 설계](#ERD-모델-설계)
### [2. API 명세서 작성](#API-명세서-작성)
### [3. JWT 인증](#JWT-인증)
### [4. 소셜로그인](#소셜로그인)

  
## ERD 모델 설계
배달의 민족에서 배달, 주소 설정, 쿠폰함, 찜, 리뷰, 주문내역, 주문하기, 카테고리, 가게, 선물하기 테이블을 만들었습니다.
<img width="871" alt="image" src="https://user-images.githubusercontent.com/59828706/213859808-84ae74d8-550a-4da2-bd4b-2df1e3de2556.png">  
전체 ERD
<details>
<summary>Entity</summary>
<div markdown="1">
<img width="383" alt="image" src="https://user-images.githubusercontent.com/59828706/214009997-18e25a23-4973-4465-a1df-38db2ab3e6be.png"><br>
User <회원><br>
<img width="381" alt="image" src="https://user-images.githubusercontent.com/59828706/214010760-07c80d21-4bae-4d5c-8a91-9e0c58fa459b.png"><br>
Address <주소><br>
<img width="380" alt="image" src="https://user-images.githubusercontent.com/59828706/214010879-c1857f43-94c0-4d0e-abe9-dcadf5058708.png"><br>
Notice <알림센터><br>
<img width="380" alt="image" src="https://user-images.githubusercontent.com/59828706/214011227-f06ea253-7a7a-4b55-966e-4115c6e3b8a3.png"><br>
Coupon <쿠폰><br>
<img width="380" alt="image" src="https://user-images.githubusercontent.com/59828706/214011335-4dcbfb7a-1931-49eb-a0ed-88084600b6ca.png"><br>
User_Coupon <중간테이블><br>
<img width="380" alt="image" src="https://user-images.githubusercontent.com/59828706/214011489-1a280f52-e0c0-44bb-985a-85936cef348e.png"><br>
Store <가게><br>
<img width="381" alt="image" src="https://user-images.githubusercontent.com/59828706/214011652-0287d57e-1998-4f7d-96b4-83690dc5b571.png"><br>
StoreMenu <가게 메뉴><br>
<img width="381" alt="image" src="https://user-images.githubusercontent.com/59828706/214011777-fd1c3c58-d717-43b2-bc9f-e24052941910.png"><br>
Heart <찜><br>
<img width="379" alt="image" src="https://user-images.githubusercontent.com/59828706/214011868-cc085bbb-827d-47a2-8bc2-f89fecd386b7.png"><br>
Review <리뷰><br>
<img width="379" alt="image" src="https://user-images.githubusercontent.com/59828706/214011949-be71f527-8e8c-448e-b289-d8f7ef6b665c.png"><br>
StoreCategory <가게 카테고리><br>
<img width="379" alt="image" src="https://user-images.githubusercontent.com/59828706/214012043-75c0a925-6b50-462d-bc5a-bf6043fc8be5.png"><br>
StoreCategory_Store <중간테이블><br>
<img width="380" alt="image" src="https://user-images.githubusercontent.com/59828706/214012233-d9de7b97-71f7-4a90-ac2c-557460e548ec.png"><br>
Orders <주문><br>
<img width="379" alt="image" src="https://user-images.githubusercontent.com/59828706/214012330-1085d966-de0c-4307-8692-4ec26dff4284.png"><br>
OrderedMenu <주문 메뉴><br>
<img width="380" alt="image" src="https://user-images.githubusercontent.com/59828706/214012733-3eae3003-e858-4b73-91f0-5eb8ce2b7d2a.png"><br>
Delivery <배달><br>
<img width="380" alt="image" src="https://user-images.githubusercontent.com/59828706/214013471-605acf7a-a955-4c00-9199-608e6b4691c7.png"><br>
Gift <선물하기><br>
<img width="380" alt="image" src="https://user-images.githubusercontent.com/59828706/214012870-855c031c-7f1f-4c6c-ac34-d9a1a873a081.png"><br>
GiftCards <상품카드><br>
<img width="380" alt="image" src="https://user-images.githubusercontent.com/59828706/214013008-68d971b2-525c-4df9-a3a2-41a45daf3095.png"><br>
GiftCertificates <상품권><br>
<img width="380" alt="image" src="https://user-images.githubusercontent.com/59828706/214013215-27706876-6132-4c89-badb-c584ba5dcb15.png"><br>
Discount <할인><br>
<img width="380" alt="image" src="https://user-images.githubusercontent.com/59828706/214013335-4bb214ed-fa78-40c4-929e-dddd9d56f691.png"><br>
CategoryPrice <가격별 카테고리><br>
<img width="380" alt="image" src="https://user-images.githubusercontent.com/59828706/214013635-02a78d1c-9ec0-4a39-a77c-847f9d00d581.png"><br>
CategoryFood <음식별 카테고리><<br>
<img width="379" alt="image" src="https://user-images.githubusercontent.com/59828706/214013728-97cd51a0-7161-4de4-837f-02ab5a32d575.png"><br>
GiftCertificaate_CategoryFood <중간테이블><br>
<img width="379" alt="image" src="https://user-images.githubusercontent.com/59828706/214014079-3f202377-9642-42aa-8cee-edd104c5af8d.png"><br>
Receiver <수신자><br>
<img width="380" alt="image" src="https://user-images.githubusercontent.com/59828706/214014154-6384129a-7796-4331-b860-9bc8fb041f7d.png"><br>
Gift_Receiver <중간테이블><br>
</div>
</details>

## API 명세서 작성
인텔리제이, aws RDS, aws EC2, Postman을 사용하였습니다.  
API 명세서의 경우 선물하기 기능은 제외하였습니다.
![image](https://user-images.githubusercontent.com/59828706/213859361-14eaf58e-f99e-4efc-9457-84ffb4f89478.png)  
1) Client가 Request를 보낸다.  
2) Controller가 Request URL을 수신한다.  
3) Controller가 Provider/Service를 호출한다.  
4) 비즈니스 로직을 수행하며, 데이터베이스에 접근하는 DAO를 이용해 결과값을 받아온다.  
- Provider: Read와 관련된 것을 처리
- Service: Insert, Update, Delete와 관련된 것을 처리
5) DAO: db에 접속하여 비즈니스 로직 실행에 필요한 쿼리를 호출한다.  
  
Validation의 경우 code 2000번대로 값, 형식, 길이 등의 형식적 Validation은 Controller에서,  
DB에서 검증해야 하는 의미적 Validation은 code 3000번대로 Provider 혹은 Service에서 처리하였습니다.  
[배달의 민족 API 명세서](https://docs.google.com/spreadsheets/d/1gPQ4RF7OVo3WuTnoHzmRS508-hT4D1TE/edit?usp=sharing&ouid=109274003342609518676&rtpof=true&sd=true)

## JWT 인증
단순히 ID, Password를 DB값과 비교하는 것 대신 JWT(Json Web Token) 토큰을 이용하여 유저 인덱스 값을 비교하도록하여 보안을 높였습니다.
<img width="563" alt="image" src="https://user-images.githubusercontent.com/59828706/214027008-1febb853-f384-493d-8efa-77bac09f8c64.png">  
<Controller에서 유저 인덱스 값 비교>  
<img width="402" alt="image" src="https://user-images.githubusercontent.com/59828706/214027297-cf639a15-c2df-434c-9316-45b59e442479.png">  
<JWT에서 유저 인덱스 추출>  
<img width="740" alt="image" src="https://user-images.githubusercontent.com/59828706/214027542-7bbc33e4-9ceb-46b4-b39d-426266a2ac4d.png">  
<Header에서 X-ACCESS-TOKEN으로 JWT 추출>  

<img width="440" alt="image" src="https://user-images.githubusercontent.com/59828706/214030502-4e1ae10d-bea9-406a-85ff-669229588131.png">  
<사용 전에 JWT_SECRET_KEY를 입력하여야 합니다.>  

## 소셜로그인
카카오 소셜로그인을 구현하였습니다.  
[Kakao Developer](https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api)  
[카카오 소셜로그인 참고자료](https://velog.io/@shwncho/Spring-Boot-%EC%B9%B4%EC%B9%B4%EC%98%A4-%EB%A1%9C%EA%B7%B8%EC%9D%B8-APIoAuth-2.0)

## ✨License
- 본 템플릿의 소유권은 소프트스퀘어드에 있습니다. 본 자료에 대한 상업적 이용 및 무단 복제, 배포 및 변경을 원칙적으로 금지하며 이를 위반할 때에는 형사처벌을 받을 수 있습니다.
