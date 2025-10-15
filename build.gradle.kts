
plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.18"
}

group = "me.gamerduck.acm"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://libraries.minecraft.net")
}

dependencies {
    paperweight.paperDevBundle("1.21.8-R0.1-SNAPSHOT")

    implementation("net.kyori:adventure-platform-bukkit:4.4.1")
    compileOnly("com.microsoft.onnxruntime:onnxruntime:1.19.0")
    compileOnly("ai.djl.huggingface:tokenizers:0.25.0")

    // If you ever need to shade libraries (example)
    // implementation("com.example:yourlib:1.0.0")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks {
    assemble {
        dependsOn(reobfJar)
    }
}
