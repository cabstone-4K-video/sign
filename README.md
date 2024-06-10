# VDI 솔루션에서 webRTC 기반 원격 제어 기능 

## 목차
- [개요](#개요)
- [기능](#기능)
- [기술스택](#기술스택)
- [팀원소개](#팀원소개)


## 개요
- 기존에 사용하던 VDI(Virtual Desktop Infrastructure)솔루션은 사용자 상호 작용의 제약, 관리자의 제한적인 지원 능력 등의 문제점을 가지고 있다.
- 이러한 문제점을 해결하기 위해 웹RTC(Web Real-Time Communication) 기술을 활용해 원격 제어 기능을 통합하고, 이를 통해 사용자 편의성과 관리 효율성을 향상시킬 수 있다.
- 또 웹 브라우저 기반으로 실시간 음성, 영상, 데이터 통신을 가능하게 함으로써 사용자에게 개인화된 데스크톱 환경을 제공한다.

## 기능
이 프로젝트는 VDI 솔루션에서 webRTC 기반 원격 제어를 해결하기 위해 개발되었습니다. 주요 기능은 다음과 같습니다:
- 로그인 및 회원가입
- 방 선택 코드 생성 및 입력
- 화상 통화, 채팅, 화면 공유

## 기술스택
- 서버 : docker, openvidu
- 프론트엔드 : react
- 백엔드 : spring boot, spring security, JWT

## 팀원소개
- 김도현 : 프로젝트 매니저 및 예산관리자, 프로젝트의 전반적인 계획, 실행, 배포 및 모니터링을 담당
- 김웅희 : 백엔드 개발자, openvidu 서버 관리 및 배포 담당
- 김우식 : 백엔드 개발자, jwt 및 spring security를 활용한 로그인/회원가입 기능 구현 담당
- 고두범 : 프론트엔드 개발자, 사용자 인터페이스 생성, UX 및 사용성 최적화 담당
- 전영상 : 백엔드 개발자 및 보고서 작성, openvidu를 활용한 세션 기능 구현 및 프론트와의 연결 관리 담당
