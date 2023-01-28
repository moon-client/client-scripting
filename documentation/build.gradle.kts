plugins {
    id("java")
    `maven-publish`
}

group = "com.moon.scripting"
version = "1.1-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "${project.group}"
            artifactId = "documentation"
            version = "${project.version}"
            from(components["java"])
        }
    }
    repositories {
        // The aids dependency repository
        maven {
            name = "aids"
            url = uri("https://aids.moonclient.xyz")
            credentials {
                username = "aids"
                password = project.property("com.moon.aids.token").toString()
            }
            authentication { create<BasicAuthentication>("header") }
        }
    }
}