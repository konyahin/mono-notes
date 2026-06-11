
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(ktorLibs.plugins.ktor)
    alias(libs.plugins.node.gradle)
}

group = "xyz.konyahin"
version = "0.0.1"

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    implementation(ktorLibs.server.config.yaml)
    implementation(ktorLibs.server.core)
    implementation(ktorLibs.server.netty)
    implementation(ktorLibs.server.contentNegotiation)
    implementation(ktorLibs.serialization.kotlinx.json)
    implementation(libs.logback.classic)

    implementation(libs.bundles.exposed)
    runtimeOnly(libs.sqlite.jdbc)

    testImplementation(kotlin("test"))
    testImplementation(ktorLibs.server.testHost)
}

node {
    nodeProjectDir.set(file("${projectDir}/frontend"))
}

val frontendBuildDir = layout.buildDirectory.dir("generated/frontend")

val buildFrontend by tasks.registering(com.github.gradle.node.npm.task.NpmTask::class) {
    dependsOn(tasks.npmInstall)
    args.set(listOf("run", "build"))

    inputs.files(fileTree("${projectDir}/frontend") {
        exclude("node_modules", "dist", ".svelte-kit")
    })
    outputs.dir(frontendBuildDir)
}

sourceSets.main {
    resources.srcDir(frontendBuildDir)
}

tasks.named("processResources") {
    dependsOn(buildFrontend)
}