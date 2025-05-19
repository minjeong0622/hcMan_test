FROM openjdk:21-jdk-slim-buster AS builder

WORKDIR /app

# 필요한 파일 복사
COPY gradlew build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle
COPY src ./src

# Gradle 실행 권한 부여
RUN chmod +x ./gradlew

# 의존성 캐싱 (빌드 속도 개선)
RUN ./gradlew dependencies --no-daemon

# 애플리케이션 빌드
RUN ./gradlew bootJar --no-daemon

# 실행용 JDK 이미지
FROM openjdk:21-slim-buster

WORKDIR /app

# 빌드된 JAR 파일 복사
COPY --from=builder /app/build/libs/*.jar app.jar

# EXPOSE 8080 (포트 열기)
EXPOSE 8080

# 실행 명령어
ENTRYPOINT ["java", "-jar", "app.jar"]
