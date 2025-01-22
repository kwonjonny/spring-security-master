# Spring Security 기반 인증 및 인가 시스템

![Spring Security](https://img.shields.io/badge/Spring%20Security-%234CAF50.svg?style=for-the-badge&logo=spring&logoColor=white)
![Java](https://img.shields.io/badge/Java-17-%23f89820?style=for-the-badge&logo=openjdk&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-%23DC382D.svg?style=for-the-badge&logo=redis&logoColor=white)
![OAuth2](https://img.shields.io/badge/OAuth2-%237E57C2.svg?style=for-the-badge&logo=oauth&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-%2300A4FF.svg?style=for-the-badge&logo=jsonwebtokens&logoColor=white)

## ✨ 프로젝트 소개 (진행중~~)
이 프로젝트는 **Spring Security**를 기반으로 다양한 인증 및 인가 방식을 구현한 백엔드 서버입니다. 사용자 인증, 권한 부여, 세션 관리 및 토큰 기반 인증을 효율적으로 관리할 수 있습니다.

---

## 🛠️ 사용 기술 스택
- **Spring Boot 3.3.5**: 프로젝트의 주요 프레임워크
- **Spring Security**: 인증 및 인가 관리
- **OAuth2 Client**: Google, Kakao, Naver 등 OAuth2 인증 연동
- **JWT (Json Web Token)**: 토큰 기반 인증 및 인가 구현
- **Redis**: 사용자 세션 캐싱 및 블랙리스트 관리
- **PostgreSQL**: 사용자 데이터 저장

---

## ⚙️ 주요 기능

### 1. **Form Login**
- 사용자 이름과 비밀번호를 이용한 전통적인 로그인 방식
- 암호화된 비밀번호 저장 (BCrypt)
- 로그인 성공 및 실패 이벤트 처리

### 2. **OAuth2 로그인**
- Google, Kakao, Naver OAuth2 Provider를 통한 소셜 로그인
- 사용자 정보 추가 등록 및 기존 계정과 연동
- 다양한 Provider 확장 가능

### 3. **JWT 인증**
- JWT 생성 및 검증을 통한 무상태 인증
- Access Token과 Refresh Token 구조 구현
- 만료된 JWT를 Redis에 저장하여 블랙리스트 관리

### 4. **Redis를 활용한 세션 관리**
- 인증 상태를 Redis에 저장하여 빠른 접근 가능
- JWT 블랙리스트 관리로 보안 강화
- 만료된 토큰 정리 스케줄링

---

## 📋 API 주요 엔드포인트

| Method | Endpoint            | Description                     |
|--------|---------------------|---------------------------------|
| POST   | /login              | Form Login                     |
| POST   | /oauth2/authorize   | OAuth2 로그인 처리               |
| GET    | /refresh-token      | Access Token 재발급            |
| GET    | /logout             | 로그아웃 및 Redis 세션 삭제     |

---

## 🚀 설치 및 실행

### 1. **환경 구성**
- Java: 17
- Redis: 6.x 이상
- PostgreSQL: 14.x 이상
- Spring Boot: 3.3.5

### 2. **프로젝트 실행**
```bash
./gradlew bootRun
