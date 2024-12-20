# 📚 spring_tenco24_v02 - 온라인 서점 API (v2)

### 프로젝트 소개
Napkins24는 간단한 책 관리, 장바구니 관리 및 **회원가입/로그인 기능**을 제공하는 REST API입니다.  
Spring Boot를 기반으로 개발되었으며, **JWT 인증 시스템**, **예외 처리 통합**, **공통 응답 DTO**, **Swagger API 문서**를 포함합니다.

---

## **🔄 버전 히스토리**
- **v1**: 책 리스트, 장바구니 CRUD 기능 구현
- **v2**: 회원가입, 로그인 및 JWT 인증 기능 추가  
  - **JWT 기반 인증**: 안전한 사용자 인증 및 API 보호
  - **글로벌 예외 처리**: 공통 예외 처리 클래스와 Custom Exception 도입
  - **공통 응답 DTO**: `ApiResponse<T>`로 일관된 데이터 응답 제공
  - **Swagger API 문서**: API 명세를 쉽게 확인하고 테스트할 수 있는 인터페이스 제공

---

## **📝 API 문서**

### **1. 책 리스트 조회**
- **URL**: `GET /books`
- **설명**: 모든 책 목록 조회
- **Swagger 태그**: `Book`
- **응답 예시**:
    ```json
    {
      "status": 200,
      "msg": "성공",
      "body": [
        { "id": 1, "title": "자바의 정석", "author": "남궁성", "price": 25000, "stockQuantity": 10 }
      ]
    }
    ```

---

### **2. 장바구니 항목 조회**
- **URL**: `GET /carts`
- **설명**: 장바구니에 담긴 항목 조회
- **Swagger 태그**: `Cart`
- **헤더**: `Authorization: Bearer <JWT 토큰>`
- **응답 예시**:
    ```json
    {
      "status": 200,
      "msg": "성공",
      "body": [
        { "id": 1, "bookId": 1, "bookTitle": "자바의 정석", "quantity": 2, "price": 50000 }
      ]
    }
    ```

---

### **3. 회원가입**
- **URL**: `POST /auth/signup`
- **설명**: 새 사용자를 등록합니다.
- **Swagger 태그**: `Auth`
- **요청 예시**:
    ```json
    {
      "username": "devnote1",
      "password": "password123",
      "email": "devnote1@kakao.com"
    }
    ```
- **응답 예시**:
    ```json
    { "status": 200, "msg": "회원가입이 완료되었습니다.", "body": null }
    ```

---

### **4. 로그인**
- **URL**: `POST /auth/login`
- **설명**: 사용자 인증 및 JWT 토큰 발급
- **Swagger 태그**: `Auth`
- **요청 예시**:
    ```json
    {
      "username": "devnote1",
      "password": "password123"
    }
    ```
- **응답 예시**:
    ```json
    {
      "status": 200,
      "msg": "성공",
      "body": {
        "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
        "tokenType": "Bearer"
      }
    }
    ```

---

### **5. JWT 토큰 인증 확인**
- **URL**: `GET /auth/me`
- **설명**: 현재 로그인된 사용자 정보 확인
- **Swagger 태그**: `Auth`
- **헤더**: `Authorization: Bearer <JWT 토큰>`
- **응답 예시**:
    ```json
    {
      "status": 200,
      "msg": "성공",
      "body": {
        "id": 1,
        "username": "devnote1",
        "email": "devnote1@kakao.com"
      }
    }
    ```

---

## **⚙️ 시스템 스펙**
- **프레임워크**: Spring Boot 3.x  
- **언어**: Java 17  
- **빌드 툴**: Gradle  
- **데이터베이스**: H2 (In-Memory)  
- **인증 방식**: JWT (Json Web Token)  
- **API 스타일**: RESTful  
- **응답 처리**: `ApiResponse<T>`로 통일된 데이터 구조 반환  
- **예외 처리**: 글로벌 예외 핸들러 및 Custom Exception 사용  
- **API 문서화**: Swagger를 통한 실시간 API 명세 제공

---

