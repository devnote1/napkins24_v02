# 📚 Napkins24 - 온라인 서점 API (v2)

### 프로젝트 소개

Napkins24는 간단한 책 관리, 장바구니 관리 및 **회원가입/로그인 기능**을 제공하는 REST API입니다.

Spring Boot를 기반으로 개발되었으며, JWT를 활용한 인증 시스템을 구현하였습니다.

---

## **버전 히스토리**

- **v1**: 책 리스트, 장바구니 CRUD 기능 구현
- **v2**: 회원가입, 로그인 및 JWT 인증 기능 추가

---

## **API 문서**

### **1. 책 리스트 조회**

- **URL**: `GET /books`
- **설명**: 모든 책 목록 조회
- **응답 예시**:
    
    ```json
    [
      { "id": 1, "title": "자바의 정석", "author": "남궁성", "price": 25000, "stockQuantity": 10 }
    ]
    
    ```
    

---

### **2. 장바구니 항목 조회**

- **URL**: `GET /carts`
- **설명**: 장바구니에 담긴 항목 조회
- **헤더**: `Authorization: Bearer <JWT 토큰>`
- **응답 예시**:
    
    ```json
    [
      { "id": 1, "bookId": 1, "bookTitle": "자바의 정석", "quantity": 2, "price": 50000 }
    ]
    
    ```
    

---

### **3. 장바구니에 책 추가**

- **URL**: `POST /carts/items`
- **설명**: 장바구니에 책 추가
- **헤더**: `Authorization: Bearer <JWT 토큰>`
- **요청 예시**:
    
    ```json
    { "bookId": 1, "quantity": 2 }
    
    ```
    
- **응답 예시**:
    
    ```json
    { "id": 1, "bookId": 1, "bookTitle": "자바의 정석", "quantity": 2, "price": 50000 }
    
    ```
    

---

### **4. 장바구니 항목 삭제**

- **URL**: `DELETE /carts/items/{id}`
- **설명**: 장바구니에서 항목 삭제
- **헤더**: `Authorization: Bearer <JWT 토큰>`

---

### **5. 회원가입**

- **URL**: `POST /auth/signup`
- **설명**: 새 사용자를 등록합니다.
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
    { "message": "회원가입이 완료되었습니다." }
    
    ```
    

---

### **6. 로그인**

- **URL**: `POST /auth/login`
- **설명**: 사용자 인증 및 JWT 토큰 발급
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
      "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
      "tokenType": "Bearer"
    }
    
    ```
    

---

### **7. JWT 토큰 인증 확인**

- **URL**: `GET /auth/me`
- **설명**: 현재 로그인된 사용자 정보 확인
- **헤더**: `Authorization: Bearer <JWT 토큰>`
- **응답 예시**:
    
    ```json
    {
      "id": 1,
      "username": "devnote1",
      "email": "devnote1@kakao.com"
    }
    
    ```
    

---

## **기본 스펙**

- **프레임워크**: Spring Boot 3.x
- **언어**: Java 17
- **빌드 툴**: Gradle
- **데이터베이스**: H2 (In-Memory)
- **인증**: JWT (Json Web Token)
- **API 형식**: RESTful

---

## **실행 방법**

1. **빌드 및 실행**
    
    ```bash
    ./gradlew clean build
    java -jar build/libs/napkins24-0.0.2-SNAPSHOT.jar
    
    ```
    
2. **H2 Console 접속**
    - URL: `http://localhost:8080/h2-console`
    - JDBC URL: `jdbc:h2:mem:testdb`
    - Username: `sa`
3. **JWT 테스트**:
    - `/auth/signup`으로 회원가입 후 `/auth/login`에서 토큰을 발급받으세요.
    - 이후 API 요청 시 `Authorization` 헤더에 토큰을 추가합니다:
        
        ```
        Authorization: Bearer <토큰>
        
        ```
        

---

## **버전 히스토리**

- **v1**: 책 리스트 및 장바구니 CRUD 구현
- **v2**: 회원가입, 로그인 및 JWT 인증 기능 추가

---

## **연락처**

- **이메일**: [devnote1@kakao.com](mailto:devnote1@kakao.com)
