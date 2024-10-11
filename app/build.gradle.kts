plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("mysql:mysql-connector-java:8.0.33")
    implementation("com.google.guava:guava:32.0.1-jre")
    implementation("org.slf4j:slf4j-simple:1.7.32")
    implementation("com.google.code.gson:gson:2.11.0")
    implementation("org.mongodb:mongodb-driver-sync:4.4.0")

    implementation("io.reactivex.rxjava3:rxjava:3.1.9")

    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
    testImplementation("com.tngtech.archunit:archunit-junit5:1.3.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

configurations.all {
    resolutionStrategy {
        force("org.slf4j:slf4j-api:1.7.32")
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    mainClass = "org.example.App"
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}