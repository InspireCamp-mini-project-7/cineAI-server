# OpenJDK 17을 기반으로 하는 경량화 스프링 부트 이미지
FROM gradle:8.12.1-jdk17 AS builder

# 작업 디렉토리 설정
WORKDIR /app

# JAR 파일을 컨테이너에 복사(jar파일이 하나만 생기도록 설정해줘야 함.)
COPY . .

RUN gradle build --no-daemon

# OpenJDK 17을 기반으로 하는 경량화 스프링 부트 이미지
FROM amazoncorretto:17

# 작업 디렉토리 설정
WORKDIR /app

# JAR 파일을 컨테이너에 복사(jar파일이 하나만 생기도록 설정해줘야 함.)
COPY --from=builder /app/build/libs/*.jar app.jar
RUN ls -al /app

# 포트 설정
EXPOSE 8080

# 실행 명령어
ENTRYPOINT ["java", "-jar", "app.jar"]