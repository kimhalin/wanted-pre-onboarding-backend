# 원티드 프리온보딩 백엔드 인턴십 - 선발 과제

- [원티드 프리온보딩 백엔드 인턴십 - 선발 과제](#원티드-프리온보딩-백엔드-인턴십---선발-과제)
  - [1. 지원자의 성명](#1-지원자의-성명)
  - [2. 애플리케이션의 실행 방법](#2-애플리케이션의-실행-방법)
  - [3. 데이터베이스 테이블 구조](#3-데이터베이스-테이블-구조)
  - [4. 구현한 API의 동작을 촬영한 데모 영상](#4-구현한-api의-동작을-촬영한-데모-영상)
  - [5. 구현 방법 및 이유에 대한 간략한 설명](#5-구현-방법-및-이유에-대한-간략한-설명)
  - [6. API 명세](#6-api-명세)

## 1. 지원자의 성명

안녕하세요. 지원자 김하린 입니다.

<br></br>

## 2. 애플리케이션의 실행 방법
#### 실행환경
```text
- Java 17
- Spring Boot 3.1.2
```
</br>

1. clone
```text
git clone https://github.com/kimhalin/wanted-pre-onboarding-backend.git
```

2. 빌드
```text
./gradlew bootJar
```

3. docker-compose 실행
```text
docker-compose up --build
```

`localhost:8080/api `에 접속해 swagger를 통해 API 실행 가능
<br></br>

## 3. 데이터베이스 테이블 구조
![image](https://github.com/kimhalin/wanted-pre-onboarding-backend/assets/75435113/9e97da38-2438-4aa1-aff6-90e3101218d1)
<br></br>

## 4. 구현한 API의 동작을 촬영한 데모 영상
[https://drive.google.com/file/d/1uqdxaIn93gPBH3nvIXfrQtnup96xlI6g/view?usp=sharing]
<br></br>

## 5. 구현 방법 및 이유에 대한 간략한 설명
테스트 코드 작성 완료, docker compose를 이용하여 애플리케이션 환경을 구성 완료
로그인이 필요한 엔드포인트는 모두 Access Token의 유효성 검사 진행
- HandlerMethodArgumentResolver를 이용한 어노테이션을 통해 AuthInfo 객체를 parameter로 받을 수 있도록 구현
- `NoAuth` Interceptor를 로그인이 필요하지 않은 엔드포인트마다 설정, Interceptor를 이용해 로그인이 필요한 엔드포인트에서 token이 없을 경우 401 에러 발생

GlobalExceptionHandler 클래스를 생성해, 예외 발생 시, 아래와 같은 통일된 response를 반환하도록 설정
```text
{
    "error": "ERROR_BOARD_NOT_FOUND",
    "errorMessage": "게시글을 찾을 수 없습니다."
}
```

#### 과제 1. 사용자 회원가입 엔드포인트
- 비밀번호의 최소 길이(8)와 이메일 '@' 포함 조건 검사 후, 유효하지 않다면 "ERROR_INVALID_EMAIL_OR_PASSWORD" 예외 발생
- 중복가입을 방지하기 위해, 이메일 중복 가입 여부 확인 후, 중복 가입이라면 "ERROR_DUPLICATED_EMAIL" 예외 발생
- 두 가지 경우 다 통과되었다면, 회원가입 진행

- @Embeddable과 @Embedded를 이용해 Password 클래스 분리
    - domain layer 내의 책임 분리 및 기능별 세분화를 확실하게 하기 위함
    - "SHA3-256" 알고리즘을 이용해 단방향 해시 암호화

#### 과제 2. 사용자 로그인 엔드포인트
- 회원가입 때와 같이 비밀번호와 이메일의 유효 검사 진행 후, email을 이용해 사용자 조회
- request에 있는 비밀번호를 해시 알고리즘을 이용해 암호화 한 값이 DB에 있는 password값과 같은 지 검사
- 로그인 성공 시 AuthInfo 반환
    - AuthInfo -> RefreshTokenId와 AccessToken 반환
    - RefreshTokenId를 이용해 DB에 저장된 RefreshToken 조회 후, 유효한 토큰이라면, 새로 AccessToken 재발급 받을 수 있도록 구현
    - 클라이언트 단에서 refresh token을 노출하지 않고, 탈취 당했을 경우를 대비하도록 refresh token을 DB에 저장하도록 구현
    - AccessToken 유효기간: 1시간, RefreshToken 유효기간: 14일
    
#### 과제 3. 새로운 게시글을 생성하는 엔드포인트
- 게시글의 내용과 제목을 받아, 게시글 생성
- 게시글 생성 요청 시, 헤더를 통해 Access Token을 받아, 해당 사용자의 정보를 조회할 수 있도록 구현

#### 과제 4. 게시글 목록을 조회하는 엔드포인트
- page, page size, orderBy(정렬기준), direction(정렬순 ex.오름차순)을 request로 받아, 이에 따른 게시글 목록 데이터를 조회하도록 구현
- 클라이언트가 요구하는 게시글의 개수, 정렬에 따라 유동적으로 조회할 수 있도록 request 구성 
- 조회하는 기능은 회원이 아니어도 가능하도록 구현

#### 과제 5. 특정 게시글을 조회하는 엔드포인트
- 게시글의 ID를 받아 조회할 수 있도록 구현
- 조회하는 기능은 회원이 아니어도 가능하도록 구현
  
#### 과제 6. 특정 게시글을 수정하는 엔드포인트
- 게시글의 ID와 게시글 수정할 내용과 제목을 받아 게시물 수정 진행
- 헤더에 있는 Access Token을 이용해 사용자를 조회한 후, 게시글의 작성자와 동일한 사용자인지 확인한다
#### 과제 7. 특정 게시글을 삭제하는 엔드포인트
- 게시글의 ID를 받아 게시물 삭제 진행
- 헤더에 있는 Access Token을 이용해 사용자를 조회한 후, 게시글의 작성자와 동일한 사용자인지 확인한다
<br></br>

## 6. API 명세
[https://documenter.getpostman.com/view/21653554/2s9Xy2QCj1]
<br></br>
