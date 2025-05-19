plugins {
	java
	id("org.springframework.boot") version "3.4.1"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.heechang"
version = "0.0.1-SNAPSHOT"
val queryDslVersion = "5.0.0"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	// Spring Boot 기본 스타터
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-web")

	// MyBatis 통합
	implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.4")

	// QueryDSL
	implementation("com.querydsl:querydsl-jpa:${queryDslVersion}:jakarta")
	annotationProcessor("com.querydsl:querydsl-apt:${queryDslVersion}:jakarta")
	annotationProcessor("jakarta.persistence:jakarta.persistence-api")

	// JPA 메타모델 생성 지원
	annotationProcessor("jakarta.annotation:jakarta.annotation-api")

	// Lombok
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	// Spring Security Starter (BCryptPasswordEncoder 포함)
	implementation("org.springframework.boot:spring-boot-starter-security")

	// 이메일 발송
	implementation("org.springframework.boot:spring-boot-starter-mail")

	// JWT 인증 관련 라이브러리
	implementation("io.jsonwebtoken:jjwt-api:0.11.2")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.2")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.2")

	// Jackson JSR310
	implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.18.2")

	// P6Spy
	implementation("p6spy:p6spy:3.9.1")

	// 데이터베이스 드라이버
	runtimeOnly("com.microsoft.sqlserver:mssql-jdbc")
	runtimeOnly("com.h2database:h2")

	// 테스트
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	// Validation API (Hibernate Validator 포함)
	implementation("org.springframework.boot:spring-boot-starter-validation")

}

tasks.withType<Test> {
	useJUnitPlatform()
}

val querydslDir = "src/main/generated"

tasks.withType<JavaCompile> {
	options.generatedSourceOutputDirectory = file(querydslDir)
}

tasks.named("clean") {
	doLast {
		file(querydslDir).deleteRecursively()
	}
}