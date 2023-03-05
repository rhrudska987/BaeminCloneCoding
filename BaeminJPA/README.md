<div align=center>
	<img src="https://capsule-render.vercel.app/api?type=waving&color=auto&height=200&section=header&text=Kyko%20Github!&fontSize=90" />	
</div>
<h1>배달의 민족 서버 클론코딩</h1>
JDBC로 먼저 구현한 뒤, JPA로 변경하는 과정을 담았습니다.
선물하기와 관련된 기능들은 제외하였습니다.
<br>
<div align=center>
	<h3>📚 Tech Stack 📚</h3>
	<p>✨ Platforms & Languages ✨</p>
</div>
<div align="center">
<img src="https://img.shields.io/badge/JAVA-007396?style=for-the-badge&logo=java&logoColor=white">
<img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=Spring&logoColor=white">
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
<img src="https://img.shields.io/badge/aws-232F3E?style=for-the-badge&logo=aws&logoColor=white">
</div>
<br>
<div align=center>
  <p>🛠 Tools 🛠</p>
</div>
<div align=center>
  <img src="https://img.shields.io/badge/AWS-232F3E?style=flat&logo=AmazonAWS&logoColor=white" />
  <img src="https://img.shields.io/badge/IntelliJ-000000?style=flat&logo=IntelliJ IDEA&logoColor=white" />
  <img src="https://img.shields.io/badge/DataGrip-000000?style=flat&logo=DataGrip&logoColor=white" />
  <img src="https://img.shields.io/badge/Postman-FF6C37?style=flat&logo=Postman&logoColor=white" />
</div>  

## 목차
### [1. ERD 모델 설계](#ERD-모델-설계)
### [2. API 명세서 작성](#API-명세서-작성)
### [3. JWT 인증](#JWT-인증)
### [4. 소셜로그인](#소셜로그인)

  
## ERD 모델 설계
배달의 민족에서 배달, 주소 설정, 쿠폰함, 찜, 리뷰, 주문내역, 주문하기, 카테고리, 가게 테이블을 만들었습니다.
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
</div>
</details>

## API 명세서 작성
스웨거를 사용하여 구현하였습니다.

  
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
