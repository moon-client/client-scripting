plugins {
    id("java")
    `maven-publish`
}

group = "com.moon.scripting"
version = "1.0.19-SNAPSHOT"

repositories {
    mavenCentral()

    // Internal Moon libraries
    maven {
        url = uri("https://aids.moonclient.xyz")
        credentials {
            username = "aids"
            password = project.property("com.moon.aids.token").toString()
        }
        authentication { create<BasicAuthentication>("header") }
    }
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation(project(":documentation"))
    implementation("org.luaj:luaj-jse:3.0.1")
    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}


publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "${project.group}"
            artifactId = "api"
            version = version
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