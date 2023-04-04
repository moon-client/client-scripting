plugins {
    id("java")
    `maven-publish`
}

group = "com.moon.scripting"
version = "1.1.0"

repositories {
    mavenCentral()
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
            groupId = "com.moon"
            artifactId = "client-scripting-annotations"
            version = version
            from(components["java"])
        }
    }
    repositories {
        maven {
            name = "MoonNexus"
            url = uri("https://nexus.moonclient.dev/repository/moon-maven/")
            credentials {
                username = project.property("com.moon.nexus.username").toString()
                password = project.property("com.moon.nexus.password").toString()
            }
        }
    }
}
