plugins {
    id("java")
    id("application")
}

group = "com.moon.scripting"
version = "1.0.0"

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

application {
    mainClass.set("com.moon.scripting.markdown.MarkdownBootstrap")
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
    implementation("org.ow2.asm:asm:9.4")
    implementation("org.ow2.asm:asm-tree:9.4")
    implementation("org.ow2.asm:asm-commons:9.4")
    implementation("org.ow2.asm:asm-util:9.4")
    compileOnly("org.projectlombok:lombok:1.18.26")
    annotationProcessor("org.projectlombok:lombok:1.18.26")
    implementation("org.tinylog:tinylog-api:2.6.1")
    implementation("org.tinylog:tinylog-impl:2.6.1")
    implementation(files("lib/markdown.jar"))
    implementation("commons-io:commons-io:2.11.0")
    implementation(project(":annotations"))
    implementation("com.moon:client-scripting-annotations:1.1.0")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
