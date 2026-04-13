group "br.com.doliver"
version "0.0.1-SNAPSHOT"

plugins {
  id("application")
  id("org.springframework.boot").version("4.0.5")
}

repositories {
  mavenCentral()
}

dependencies {
  implementation(project(":finance-core"))

  compileOnly("org.projectlombok:lombok:1.18.44")
  annotationProcessor("org.projectlombok:lombok:1.18.44")

  // Spring
  implementation("org.springframework.boot:spring-boot-starter:4.0.5")
  implementation("org.springframework.boot:spring-boot-starter-web:4.0.5")
  compileOnly("org.springframework.boot:spring-boot-starter-data-jpa:4.0.5")

  // Tests
  testCompileOnly("org.projectlombok:lombok:1.18.44")
  testAnnotationProcessor("org.projectlombok:lombok:1.18.44")
  testImplementation("org.springframework.boot:spring-boot-starter-test:4.0.5")
  testImplementation("org.junit.jupiter:junit-jupiter:6.0.3")
  testImplementation("org.mockito:mockito-junit-jupiter:5.23.0")
  testRuntimeOnly("org.junit.platform:junit-platform-launcher:6.0.3")

  // Tests
  testImplementation("io.rest-assured:rest-assured:6.0.0")
  testImplementation("org.wiremock:wiremock-standalone:3.13.2")
  testImplementation("org.springframework.cloud:spring-cloud-contract-wiremock:5.0.2")
  testImplementation("br.com.leonardoferreira:JBacon:2.1.1")
  testImplementation("com.h2database:h2:2.4.240")
  testCompileOnly("org.springframework.boot:spring-boot-starter-data-jpa:4.0.5")
}

java {
  toolchain {
    languageVersion.set(JavaLanguageVersion.of(26))
  }
}

application {
  mainClass = "br.com.doliver.APIApplication"
}

tasks.test {
  useJUnitPlatform()
  testLogging {
    events("passed", "skipped", "failed")
    showStandardStreams = true
  }
}
