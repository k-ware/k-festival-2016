k-festival
====

2016년 2월 29일 ~ 3월 7일까지 진행하는 KWARE 개발자 이벤트를 위한 웹사이트

## 기술 스택

- Gradle
- Spring Boot
- jQuery + Bootstrap
- Docker

## 실행 환경

- Java 8
- Docker 1.9.1

### docker

1. 빌드
```bash
$ gradle clean deleteWar4Docker build copyWar4Docker
$ cd docker
$ sudo docker build --tag kware/k-festival .
```

2. 실행
```bash
$ sudo docker run -d \
  --name=k-festival \
  --publish=18080:8080 \
  --volume=/home/kware/k-festival:/usr/src \
  kware/k-festival
```
