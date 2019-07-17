# 게시글 관련 기능 구현



## 기능목록



### 게시글 생성

- 게시글 작성 페이지 이동
  - 메인페이지(index.html)에서 게시글 생성 버튼을 누르기
  - `GET /writing` 으로 요청
  - 작성 페이지(article-edit.html)로 이동

- 게시글 작성
  - `POST /articles` 으로 요청
  - 게시글 생성 시 게시글은 ArticleRepository의 `List<Article> articles`에 저장한다.
  - 게시글 페이지(article.html)로 이동



### 게시글 목록 조회

- 메인 페이지 이동
  - `GET /` 으로 요청으로 이동 시 메인 페이지에 게시글 목록이 노출



### 게시글 조회

- 게시글 페이지 이동
  - 메인페이지(index.html)에서 게시글을 클릭 시 게시글 페이지(article.html)으로 이동
  - `GET /articles/{articleId}` 으로 요청



### 게시글 수정

- 게시글 수정 페이지 이동
  - 게시글 페이지(article.html)에서 수정 버튼 누르기
  - `GET /articles/{articleId}/edit` 으로 요청
  - 게시글 수정 페이지(article-edit.html)로 이동
- 게시글 수정
  - `PUT /articles/{articleId}` 으로 요청
  - 게시글 페이지(article.html)로 이동



### 게시글 삭제

- 게시글 페이지(article.html)에서 삭제 버튼 누르기
  - `DELETE /articles/{articleId}` 으로 요청
  - 게시글 목록 조회 페이지(index.html)로 이동

### 회원 등록
- signup.html에서 `POST /users`로 회원가입 요청
- POST 요청 처리 후 redirect로  login.html로 이동
- 회원가입 규칙
    - 동일한 email로 중복가입을 할 수 없다.
    - 이름은 2~10자로 제한하며 숫자나 특수문자가 포함될 수 없다.
    - 비밀번호는 8자 이상의 소문자, 대문자, 숫자, 특수문자의 조합이다.
    - 비밀번호 확인 기능이 동작해야 한다.
    
- 회원 등록 실패 예외 처리
    - 회원등록 실패 시 errorMessage를 Model에 담아서 페이지에 노출
    - 프론트엔드에서 errorMessage 노출 시 부트스트랩의 Alerts을 이용
    - 프론트엔드에서도 유효성 체크 (HTML5의 form validation 이용)
    
### 회원 조회
- `GET /users`로 회원조회 요청
- user-list.html로 이동
   
 