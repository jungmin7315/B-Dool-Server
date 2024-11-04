# 베이스 이미지 설정
FROM openjdk:17-jdk-alpine

# tzdata 패키지를 설치하고 시간대를 설정
RUN apk add --no-cache tzdata \
    && cp /usr/share/zoneinfo/Asia/Seoul /etc/localtime \
    && echo "Asia/Seoul" > /etc/timezone

# 워킹 디렉토리 설정
WORKDIR /app

COPY ChatService/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Duser.timezone=Asia/Seoul", "-jar", "/app/app.jar"]