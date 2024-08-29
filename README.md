# PETMILY
### 반려동물 산책 기록 및 커뮤니티 플랫폼

![logo image ](https://github.com/user-attachments/assets/1e11f055-2858-4ab9-a42d-5c41c659e469)

---

## 📌 개요
- 프로젝트 명: PETMILY (펫밀리)
- 프로젝트 기간 : 2024.07.28 ~ 2024.08.29
- 깃허브 주소 : https://github.com/blubincod/petmily
- 노션 팀링크 : https://sudsy-flute-730.notion.site/770f740108a04881a975102eacdfafe8

## 📌 팀원

| 박준엽 | 정민균 | 이민용 | 이진형 |
| --- | --- | --- | --- |
| 팀장 | 팀원 | 팀원 (중도 탈퇴) | 팀원 (중도 탈퇴) |
| Back-End | Back-End | Back-End | Back-End |
| [@blubincod](https://github.com/blubincod) | [@totorotail](https://github.com/totorotail) | [@Minzion0](https://github.com/Minzion0) | [@jinhyoung2](https://github.com/jinhyoung2) |

## 📌 프로젝트 소개

Petmily는 반려동물 보호자들을 위한 종합 케어 서비스입니다. 산책 기록부터 건강 관리, 커뮤니티 활동까지 반려동물과 관련된 다양한 기능을 제공합니다.

## 📌 주요 기능

1. 🚶‍♂️ **산책 목표 설정 및 기록 관리**
  - 일일 산책 거리 기록
  - 산책 중 활동(배변, 수분 섭취) 기록
  - 산책 루트 저장 및 공유
2. 🏥 **반려동물 건강 관리**
  - 체중, 식사량 등 기본 건강 정보 기록
  - 예방접종, 병원 방문 일정 관리
3. 💬 **커뮤니티 기능**
  - 반려동물 관련 정보 공유
  - 사진 및 경험 공유 게시판
4. 🗨️ **오픈 채팅방**
  - 실시간 소통 기능
  - 주제별 채팅방 개설 가능
5. 🔔 **알림 서비스**
  - 중요 일정 알림
  - 커뮤니티 활동 알림
  - 예방접종, 병원 방문 일정 알림

## 📌 프로젝트 목표

- 다양한 기능 구현을 통한 Back-End 아키텍처 심화 학습
- 대규모 트래픽 처리를 위한 서버 최적화 및 안정성 확보
  - 캐싱 전략 구현 (예: Redis)
- 지속적인 코드 리팩토링을 통한 품질 개선
  - 클린 코드 원칙 적용
- 효율적인 협업 도구 활용 (Github, Slack, Notion, Discord)
- 문서화 강화
  - API 문서 자동화 (Swagger 등 활용)
  - 기술 문서 및 개발 가이드 작성

## 📌 프로젝트 환경

- Backend: Java 17, Spring Boot 3.1.8
- Database: PostgreSQL 16, H2 (개발용)
- ORM: Spring Data JPA
- Build Tool: Gradle

---

## 📌 Git-Flow 브랜치 전략
<img width="1000" alt="git flow" src="https://github.com/user-attachments/assets/01f357d7-8b86-4925-8c24-c5967f1f5d51">

- master : 제품으로 출시될 수 있는 브랜치를 의미
- develop : feature에서 리뷰완료한 브랜치를 Merge
- feature : 기능을 개발하는 브랜치
- release : 이번 출시 버전을 준비하는 브랜치
- hotfix : 출시 버전에서 발생한 버그를 수정하는 브랜치

---

## 📌 DB ERD
<img width="1000" alt="git flow" src="https://images-ext-1.discordapp.net/external/XST_nb_REA-OZ26pZ3I3xJ7JrAV2b93YGUdKg0YuIZg/https/github.com/user-attachments/assets/08cd7078-5aaf-4879-bdf5-0d3e97320fd3?format=webp&width=1193&height=920">

ERD 초안은 [여기](https://www.erdcloud.com/d/qACQLjHtkiabcsDWZ)에서 확인하실 수 있습니다.
