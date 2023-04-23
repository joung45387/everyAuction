# EveryAuction 
데이터베이스 프로그래밍 수업의 프로젝트 결과물 입니다. <br/>

# 사용기술
## 백엔드
* SpringBoot
* MySQL
* WebSocket

## 프론트엔드
* Thymeleaf
* HTML/CSS/Javascript
* Bootstrap5

# 프로젝트 개요
데이터베이스를 제일 잘 활용할수 있는 웹 어플리케이션을 만들기 위해 주제를 고민하던중 단순한 게시판보다는 <br/>

여러가지 서비스를 제공할수 있는 웹 어플리케이션이 데이터베이스를 좀더 활용할 수 있을것 같다는 의견하에<br/>

개인간 경매 주선 웹 어플리케이션을 개발하기로 결정했습니다.<br/>

# 요구사항 분석(데이터 베이스)

1. 회원으로 가입하려면 회원아이디,비밀번호,이름,전화번호,주소를 입력해야한다.
2. 회원은 회원 아이디로 식별한다.
3. 상품은 상품번호,시작가,상품정보,제목,현재가,경매 종료 시간,사진 정보를 유지해야한다. 
4. 상품은 상품번호로 식별한다. 
5. 회원은 여러 상품을 등록할 수 있고, 하나의 상품을 여러 회원이 입찰 할 수 있다. 
6. 회원은 상품을 입찰할 수 있으며, 입찰 시 입찰 시간, 입찰 가격에 대한 정보를 유지해야 한다. 
7. 입찰이 확정되면, 판매자와 입찰자는 채팅이 가능하다.
8. 채팅은 내용, 채팅한 시간에 대한 정보를 유지해야한다.
9. 회원은 경매 출품중인 상품에 대해 댓글을 달 수 있으며, 댓글 작성시 내용, 댓글 작성시간에 대한 정보를 유지해야 한다.

# ERD
<img src="https://user-images.githubusercontent.com/45916379/213995770-dcbf7a36-f21a-4a4a-bdd5-7bf3cf37acda.png">

# 테이블 명세서
<h3>Product Table</h3>
<img src = "https://user-images.githubusercontent.com/45916379/213997844-356acaf2-90fe-46ac-aecd-c4cb5c2424fb.png">
<h3>User Table</h3>
<img src = "https://user-images.githubusercontent.com/45916379/213998144-28718e8c-1ecc-4cdd-b52f-e25dac1ce386.png">
<h3>Chat Table</h3>
<img src = "https://user-images.githubusercontent.com/45916379/213998214-fe48c805-5b1e-431c-af8a-ac26fca32de5.png">
<h3>bidRecord Table</h3>
<img src = "https://user-images.githubusercontent.com/45916379/213998449-ef9c224f-e530-4657-8379-3b7f0f612bfd.png">
<h3>productReply Table</h3>
<img src = "https://user-images.githubusercontent.com/45916379/213998546-639e4336-86e0-4f25-8dba-e5208425744f.png">


# 주요 기능
<h3>회원가입</h3>
<img src = "https://user-images.githubusercontent.com/45916379/213998749-af8f4922-f396-481a-ae6b-8bbbb4ecdcc1.png">
<h3>상품 등록</h3>
<img src = "https://user-images.githubusercontent.com/45916379/213999307-d96108c3-7b7a-4c18-85c7-8af8dd50bfb7.png">
<h3>상품 정보 확인</h3>
<img src = "https://user-images.githubusercontent.com/45916379/213999679-fb37dba1-3c4e-40d2-b275-8a0c706af2fd.png">
<h3>거래 기록 확인</h3>
<img src = "https://user-images.githubusercontent.com/45916379/214000879-3dca5fb8-094f-4714-90ad-e4a89c62641c.png">
<h3>입찰가 갱신</h3>
<img src = "https://user-images.githubusercontent.com/45916379/214000991-d623aa84-5c22-40ab-b825-fcba9fd952a7.png">
<h3>댓글 삭제/수정</h3>
<img src = "https://user-images.githubusercontent.com/45916379/214001120-49f99e48-f3fb-4f48-8cc9-cc2e7f36c967.png">


# 개선방향
현재 이 프로젝트는 추상화가 되어 있지 않아 DB 접근 방식을 변경하려면, 코드를 싹다 갈아 엎어야한다는 단점이 있음.
<br><br>
또한 별도의 보안처리가 되어 있지 않아 ID와 패스워드만 비교해 정확한지 비교하고 세션을 부여하는방식으로 되어있음.
<br><br>
DB에 비밀번호를 암호화 하지 않고 저장해서 DataBase가 해킹당한다면 중요 회원 정보를 유출하게될 가능성이 있음.
<br><br>
웹소켓에서 인증처리가 되어 있지 않아 웹 지식을 가지고 있다면 1대1 채팅에 난입할 가능성이 있음.
<br>

# 팀원
* 이종훈 : 백엔드 및 총괄
    * github : https://github.com/joung45387
* 조성윤 : 프론트 개발(Thymeleaf)
    * github : https://github.com/chalaly
* 정호균 : 백엔드 보조
    * github : https://github.com/joeyjhg
