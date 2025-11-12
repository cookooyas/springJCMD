# 💻 [프로젝트 이름]

## 1. 프로젝트 개요 (Overview)

이 프로젝트는 **Spring Framework 기반의 웹 애플리케이션**으로, 전통적인 `web.xml` 파일을 사용하지 않고 **100% Java Config 방식**으로 모든 설정을 구성합니다. 전자정부 프레임워크의 구조를 차용하여 Root Context와 Servlet Context를 명확하게 분리하고, WAS(Web Application Server) 구동 시점에 모든 환경 설정을 동적으로 처리합니다.

### 주요 특징
* **Java Config 기반:** 모든 설정은 `WebInitializer`와 `@Configuration` 클래스로 관리됩니다.
* **Context 분리:** Service/Repository 계층은 **Root Context**에서, Controller 계층은 **MVC Servlet Context**에서 분리하여 관리합니다.
* **보안 필터:** 악성 스크립트(XSS) 방지를 위한 커스텀 **HTML Tag Filter**를 WAS 구동 시점에 동적으로 등록합니다.
* **URL 매핑:** 모든 동적 웹 요청은 전통적인 **`*.do` 패턴**을 따릅니다.
* **프로파일 기본값:** `spring.profiles.active`가 설정되지 않을 경우, **`LOCAL_SERVER`** 프로파일을 기본값으로 강제 활성화합니다.

---

## 2. 기술 스택 (Tech Stack)

| 구분 | 주요 기술 | 비고 |
| :--- | :--- | :--- |
| **프레임워크** | Spring Framework (5.x+) | Core, Context, Web MVC |
| **빌드 도구** | Maven | |
| **웹 컨테이너** | Tomcat (WAR 배포) | Servlet 3.1+ 환경 |
| **개발 환경** | Eclipse, STS (추천) | |

---

## 3. 개발 환경 설정 및 빌드

### 3.1. WAS 환경 변수 (Active Profile 설정)

프로젝트는 프로파일(Profile)별로 다른 환경 설정 파일(Properties)을 로드합니다. WAS 구동 시점에 VM Arguments를 통해 활성화할 프로파일을 지정해야 합니다.

**WAS VM Arguments 예시:**
```bash
-Dspring.profiles.active=[적용할 프로파일 명]