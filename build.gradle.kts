import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.1.8.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
    kotlin("jvm") version "1.2.71"
    kotlin("plugin.spring") version "1.2.71"
    kotlin("plugin.jpa") version "1.2.71"
}

group = "com.example.demo"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

val developmentOnly by configurations.creating
configurations {
    runtimeClasspath {
        extendsFrom(developmentOnly)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")

    implementation("com.auth0:java-jwt:3.3.0")

    // jpa
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("mysql:mysql-connector-java")


    // kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.apache.commons:commons-lang3:3.8.1")

    // jackson
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.9.9")

    // aws s3
    implementation("com.amazonaws:aws-java-sdk-s3:1.11.606")

    // 자바 캐쉬 라이브러리
    implementation("com.github.ben-manes.caffeine:caffeine:2.8.0")

    // spring-boot-configuration-processor
    implementation("org.springframework.boot:spring-boot-configuration-processor:2.1.7.RELEASE")

    // pdf
    implementation("org.apache.pdfbox:pdfbox:2.0.16")
    implementation("com.openhtmltopdf:openhtmltopdf-pdfbox:1.0.0")
    implementation("com.openhtmltopdf:openhtmltopdf-svg-support:1.0.0")
    implementation("com.openhtmltopdf:openhtmltopdf-slf4j:1.0.0")

    // slack
    implementation("net.gpedro.integrations.slack:slack-webhook:1.4.0")

    // barcode
    implementation("net.sf.barcode4j:barcode4j:2.1")

    // FTP
    implementation("com.jcraft:jsch:0.1.55")

    // 국제 전화번호 유틸
    implementation("com.googlecode.libphonenumber:libphonenumber:8.10.17")


    // koreapost encrypt
    // https://mvnrepository.com/artifact/org.bouncycastle/bcprov-jdk15on
    implementation("org.bouncycastle:bcprov-jdk15on:1.62")

    // koreapost pdf
    // https://mvnrepository.com/artifact/net.sf.jasperreports/jasperreports
    implementation("net.sf.jasperreports:jasperreports:6.5.1")
    // https://mvnrepository.com/artifact/com.lowagie/itext
    implementation("com.lowagie:itext:2.1.7")
    // https://mvnrepository.com/artifact/org.olap4j/olap4j
    implementation("org.olap4j:olap4j:1.2.0")
    // https://mvnrepository.com/artifact/net.sourceforge.barbecue/barbecue
    implementation("net.sourceforge.barbecue:barbecue:1.5-beta1")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}
