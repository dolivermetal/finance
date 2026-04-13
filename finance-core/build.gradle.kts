group "br.com.doliver"
version "0.0.1-SNAPSHOT"

plugins {
    id("java-library")
    id("org.springframework.boot").version("4.0.5").apply(false)
    id("pmd")
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.44")
    annotationProcessor("org.projectlombok:lombok:1.18.44")

    // Spring
    implementation("org.springframework.boot:spring-boot-starter:4.0.5")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:4.0.5")

    // Database
    implementation("org.postgresql:postgresql:42.7.10")
    implementation("com.oracle.database.jdbc:ojdbc11:23.26.1.0.0")

    // Tests
    testCompileOnly("org.projectlombok:lombok:1.18.44")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.44")
    testImplementation("org.junit.jupiter:junit-jupiter:6.0.3")
    testImplementation("org.mockito:mockito-junit-jupiter:5.23.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:6.0.3")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(26))
    }
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
        showStandardStreams = true
    }
}

pmd {
    toolVersion = "7.23.0"
    isConsoleOutput = true
    ruleSets = listOf("${rootProject.projectDir}/pmd.xml")
}
