k-festival
====

2016년 2월 29일 ~ 3월 7일까지 진행하는 KWARE 개발자 행사을 위한 웹사이트

## 기술 스택

- Spring Boot
- jQuery + Bootstrap
- Docker

## 실행 환경

- Java 8
- Docker 1.9.1

### docker

```bash
$ sudo docker run -d \
  --name=k-festival \
  --publish=18080:8080 \
  --volume=/home/kware/k-festival:/usr/src \
  kware/k-festival
```
