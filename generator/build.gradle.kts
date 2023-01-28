plugins {
    id("java")
    id("application")
}

group = "com.moon"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application {
    mainClass.set("com.moon.scripting.markdown.MarkdownBootstrap")
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation("org.ow2.asm:asm:9.4")
    implementation("org.ow2.asm:asm-tree:9.4")
    implementation("org.ow2.asm:asm-commons:9.4")
    implementation("org.ow2.asm:asm-util:9.4")
    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")
    implementation("org.tinylog:tinylog-api:2.5.0")
    implementation("org.tinylog:tinylog-impl:2.5.0")
    implementation(files("lib/markdown.jar"))
    implementation("commons-io:commons-io:2.11.0")
    implementation(project(":documentation"))
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}