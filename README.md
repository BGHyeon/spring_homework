# spring_homework

### com.sparta.entities
* Notice : 게시글
* Member : 회원
* Comment : 댓글
* Timestamp : 생성, 수정시간 Column 추가용 abstract class
* MemberDetail : 로그인 토큰

### com.sparta.repositories
* NoticeRepo : 게시글 Repository
* MemberRepo : 회원 Repository
* CommentRepo : 댓글 Repository
* MemberMemoryRepo : 테스트용 회원 Memory Repository

### com.sparta.services
* CommentService : 댓글 Service
* MemberService : 회원 Service
* NoticeService : 게시글 Service
* MemberAuthService : 로그인 토큰 확인용 Service
* JwTAuthService : 로그인시 JWT Token을 발급해주는 Service
* CookieService : Cookie 생성, 조회를 담당하는 Service(JWT Token 조회용)

### com.sparta.controllers
* CommentController : 댓글 관련 REST API (코멘트 생성, 수정, 삭제)
* MemberController : 회원 관련 REST API (회원 생성, ID 중복체크)
* NoticeController : 게시글 관련 REST API (게시글 생성, 수정, 삭제, 좋아요 기능)
* PageController : html 페이지 URI Controller

### com.sparta.configurations
* AuthFailHandler : 로그인 실패 Handler 
* CustomAccessDenied : 로그인 없음 Handler
* SecurityConfig : Spring Security 설정 정보
* SwaggerConfig : Swagger 설정 정보

### com.sparta.jwtsecurity
* JwTAuthFilter : Client의 모든 요청에 대해 Cookie에 있는 JwtToken을 조회하여 인증정보를 생성하는 Filter
* JwtTokenProvicer : Jwt Token 발급, 해독, 유효성 검사등의 기능을 가진 객체

| 내용         | Method | api                 | request                       | response     |
|------------|--------|---------------------|-------------------------------|--------------|
| 댓글삭제       | DELETE | /comment/{id}       | commentId                     | resultmsg    |
| 댓글수정       | PATCH  | /comment/{id}       | commentId,comment             | resultmsg    |
| 댓글생성       | DELETE | /comment/{noticeid} | noticeId,comment              | resultmsg    |
| 회원가입       | POST   | /member             | loginId,loginPw,name,nickName | redirectView |
| ID중복확인     | POST   | /member/{id}        | loginId                       | resultmsg    |
| 로그인        | POST   | /member/login       | username,password             | redirectView |
| 좋아요 toggle | POST   | /like/{noticeId}    | noticeId                      | redirectView |
| 게시물 목록     | GET    | /notice             |                               | List<Notice> |
| 게시물 생성     | POST   | /notice             | title,contents                | redirectView |
| 게시물 수정     | POST   | /notice/{noticeId}  | noticeId,title,contents       | redirectView |
| 게시물 삭제     | DELETE | /notice/{noticeId}  | noticeId       | resultmsg    |





### 로그인 요청 -> 계정 정보 확인및 Jwt, Refresh 토큰 발급 -> Refresh Token Database 저장 -> Jwt, Refresh토큰 Cookie 저장
### 클라이언트 요청 -> Cookie에서 Jwt, Refresh 토큰 조회 -> Jwt 토큰 만료상태 확인 및 해독 -> Jwt 토큰 해독값과 Refresh 토큰값으로 계정정보 조회 -> Refresh 토큰 만료 확인 -> 만료되었으면 Jwt, Refresh토큰 재발급 및 DB, Cookie 저장

