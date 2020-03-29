import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.2.6.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    val kotlinVersion = "1.3.71"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("kapt") version kotlinVersion
}

group = "kr.co.kcd"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_13

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-log4j2")
    // If this code is commented out, no error occurs
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    testImplementation("org.junit.platform:junit-platform-launcher")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation(group = "io.mockk", name = "mockk", version = "1.9.3")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor.tools:blockhound-junit-platform:1.0.3.RELEASE")
    val autoService = "com.google.auto.service:auto-service:1.0-rc6"
    compileOnly(autoService)
    testCompileOnly(autoService)
    kaptTest(autoService)
    kapt(autoService)
}

configurations {
    all {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        exclude(group = "org.mockito")
    }
}

dependencyManagement {
    imports {
        mavenBom("io.netty:netty-bom:4.1.48.Final")
    }
}


tasks.withType<Test> {
    useJUnitPlatform()
    jvmArgs("-XX:+AllowRedefinitionToAddDeleteMethods")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "12"
    }
}
