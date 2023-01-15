plugins {
    id("java")
    id("application")
    `maven-publish`
}

group = "com.moon.scripting"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
    implementation("org.ow2.asm:asm:9.4")
    implementation("org.ow2.asm:asm-tree:9.4")
    implementation("org.ow2.asm:asm-commons:9.4")
    implementation("org.ow2.asm:asm-util:9.4")
    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")
    implementation("org.tinylog:tinylog-api:2.5.0")
    implementation("org.tinylog:tinylog-impl:2.5.0")
    implementation(files("lib/markdown.jar"))
}

application {
    mainClass.set("com.moon.scripting.documentation.markdown.MarkdownBootstrap")
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