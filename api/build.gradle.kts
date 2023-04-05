plugins {
    id("java")
    `maven-publish`
}

group = "com.moon.scripting"
version = "1.0.21"

repositories {
    mavenCentral()
    maven {
        name = "MoonNexus"
        url = uri("https://nexus.moonclient.dev/repository/moon-maven/")
        credentials {
            username = project.property("com.moon.nexus.username").toString()
            password = project.property("com.moon.nexus.password").toString()
        }
    }
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
    implementation(project(":annotations"))
    implementation("com.moon:client-scripting-annotations:1.1.0")
    implementation("org.luaj:luaj-jse:3.0.1")
    compileOnly("org.projectlombok:lombok:1.18.26")
    annotationProcessor("org.projectlombok:lombok:1.18.26")
}

java { toolchain { languageVersion.set(JavaLanguageVersion.of(17)) } }

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.moon"
            artifactId = "client-scripting-api"
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
