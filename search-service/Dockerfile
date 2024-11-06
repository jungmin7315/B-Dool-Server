# Step 1: 베이스 이미지로 OpenJDK를 사용
FROM openjdk:17-jdk-alpine

# Step 2: JAR 파일을 컨테이너 안으로 복사
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

# Step 3: 애플리케이션 실행 명령어
ENTRYPOINT ["java","-jar","/app.jar"]
