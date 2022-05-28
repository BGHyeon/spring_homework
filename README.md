# spring_homework
### com.sparta.entitys
* Notice : 게시글
* Member : 회원
* Comment : 댓글
* Timestamp : 생성, 수정시간 Column 추가용 abstract class

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
* MemberDetail : 로그인 토큰

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

